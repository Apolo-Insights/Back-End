package com.sptech.produtos.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sptech.produtos.dto.HairAnalysisResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class OpenAiHairService {

    private static final List<String> HAIR_TYPES = List.of("liso", "ondulado", "cacheado", "crespo");
    private static final List<String> HAIR_PROBLEMS = List.of("ressecamento", "oleosidade", "frizz", "queda", "caspa", "danificado", "saudavel");
    private static final List<String> HAIR_TREATMENTS = List.of("hidratacao", "nutricao", "reconstrucao", "controle de frizz", "fortalecimento", "sem tratamento necessario");

    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final String openAiApiKey;
    private final String openAiModel;

    public OpenAiHairService(ObjectMapper objectMapper,
                             @Value("${openai.api-key:}") String openAiApiKey,
                             @Value("${openai.model:gpt-4.1-mini}") String openAiModel) {
        this.objectMapper = objectMapper;
        this.httpClient = HttpClient.newHttpClient();
        this.openAiApiKey = openAiApiKey;
        this.openAiModel = openAiModel;
    }

    public HairAnalysisResponse analisarImagem(MultipartFile photo) throws IOException, InterruptedException {
        if (photo == null || photo.isEmpty()) {
            throw new IllegalArgumentException("Envie uma foto.");
        }

        if (isBlank(openAiApiKey)) {
            throw new IllegalStateException("OPENAI_API_KEY nao configurada.");
        }

        String mimeType = photo.getContentType();
        if (isBlank(mimeType)) {
            mimeType = "image/jpeg";
        }

        String imageDataUrl = "data:%s;base64,%s".formatted(mimeType, Base64.getEncoder().encodeToString(photo.getBytes()));
        String responseText = chamarOpenAi(gerarPayloadAnalise(imageDataUrl));

        if (isBlank(responseText)) {
            throw new IllegalStateException("A IA nao retornou analise.");
        }

        JsonNode parsed = parseModelJson(responseText);
        if (parsed == null || !parsed.isObject()) {
            throw new IllegalStateException("Resposta da IA em formato invalido.");
        }

        String rawTipoCabelo = firstDefined(parsed, List.of("tipoCabelo", "tipo_cabelo", "hairType", "hair_type", "tipo"));
        String rawProblema = firstDefined(parsed, List.of("problema", "main_problem", "problem", "problemaPrincipal"));
        String rawTratamento = firstDefined(parsed, List.of("tratamento", "treatment", "objetivo"));
        String rawConfianca = firstDefined(parsed, List.of("confianca", "confidence", "score"));
        firstDefined(parsed, List.of("imagemValida", "imagem_valida", "imageValid", "isValid"));
        firstDefined(parsed, List.of("motivo", "reason", "justificativa", "rationale"));

        String tipoCabelo = normalizeHairType(rawTipoCabelo);
        String problema = normalizeHairProblem(rawProblema);
        String tratamento = normalizeHairTreatment(rawTratamento);
        Integer confianca = parseConfidence(rawConfianca);
        if (confianca == null) {
            throw new IllegalStateException("A IA nao retornou o campo de confianca da imagem.");
        }

        HairAnalysisResponse response = new HairAnalysisResponse();

        if (isBlank(tipoCabelo) || isBlank(problema) || isBlank(tratamento)) {
            response.setTipoCabelo(null);
            response.setProblema(null);
            response.setTratamento(null);
            response.setConfianca(Math.min(confianca, 40));
            return response;
        }

        response.setTipoCabelo(normalizeOutputValue(tipoCabelo));
        response.setProblema(normalizeOutputValue(problema));
        response.setTratamento(normalizeOutputValue(tratamento));
        response.setConfianca(confianca);
        return response;
    }

    private String chamarOpenAi(Map<String, Object> payload) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/responses"))
                .header("Authorization", "Bearer " + openAiApiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(payload), StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IllegalStateException("Falha ao chamar OpenAI: HTTP " + response.statusCode());
        }

        JsonNode root = objectMapper.readTree(response.body());
        return extractOutputText(root);
    }

    private Map<String, Object> gerarPayloadAnalise(String imageDataUrl) {
        Map<String, Object> systemContent = Map.of(
                "type", "input_text",
                "text", "Responda somente com JSON valido. Sem markdown e sem texto extra."
        );

        Map<String, Object> userText = Map.of(
                "type", "input_text",
                "text", "Analise somente o cabelo da pessoa na imagem. Responda em JSON com estas chaves EXATAS em portugues: imagemValida (boolean), motivo, tipoCabelo, problema, tratamento, confianca. Valores permitidos: tipoCabelo = liso|ondulado|cacheado|crespo|null; problema = ressecamento|oleosidade|frizz|queda|caspa|danificado|null; tratamento = hidratação|nutrição|reconstrução|controle de frizz|fortalecimento|null; confianca inteiro de 0 a 100. Regras: se nao houver pessoa ou cabelo visivel => imagemValida=false, confianca de 0 a 20 e campos tipo/problema/tratamento null. Se houver cabelo visivel => imagemValida=true e confianca de 60 a 100."
        );

        Map<String, Object> userImage = Map.of(
                "type", "input_image",
                "image_url", imageDataUrl
        );

        return new LinkedHashMap<>(Map.of(
                "model", openAiModel,
                "temperature", 0,
                "input", List.of(
                        Map.of(
                                "role", "system",
                                "content", List.of(systemContent)
                        ),
                        Map.of(
                                "role", "user",
                                "content", List.of(userText, userImage)
                        )
                )
        ));
    }

    private String extractOutputText(JsonNode root) {
        if (root == null) {
            return null;
        }

        JsonNode outputText = root.get("output_text");
        if (outputText != null && !outputText.isNull()) {
            return outputText.asText();
        }

        JsonNode output = root.path("output");
        if (output.isArray()) {
            for (JsonNode item : output) {
                JsonNode content = item.path("content");
                if (content.isArray()) {
                    for (JsonNode contentItem : content) {
                        JsonNode textNode = contentItem.get("text");
                        if (textNode != null && !textNode.isNull()) {
                            return textNode.asText();
                        }
                    }
                }
            }
        }

        return null;
    }

    private JsonNode parseModelJson(String rawText) throws IOException {
        if (isBlank(rawText)) {
            return null;
        }

        String trimmed = rawText.trim();

        try {
            return objectMapper.readTree(trimmed);
        } catch (IOException ignored) {
        }

        int firstBrace = trimmed.indexOf('{');
        int lastBrace = trimmed.lastIndexOf('}');
        if (firstBrace >= 0 && lastBrace > firstBrace) {
            return objectMapper.readTree(trimmed.substring(firstBrace, lastBrace + 1));
        }

        return null;
    }

    private String firstDefined(JsonNode node, List<String> keys) {
        for (String key : keys) {
            JsonNode candidate = node.get(key);
            if (candidate != null && !candidate.isNull()) {
                String value = candidate.asText();
                if (!isBlank(value)) {
                    return value;
                }
            }
        }
        return null;
    }

    private String normalizeHairType(String value) {
        String normalized = normalizeValue(value);
        if (HAIR_TYPES.contains(normalized)) {
            return normalized;
        }

        return switch (normalized) {
            case "straight" -> "liso";
            case "wavy" -> "ondulado";
            case "curly" -> "cacheado";
            case "coily", "kinky" -> "crespo";
            default -> null;
        };
    }

    private String normalizeHairProblem(String value) {
        String normalized = normalizeValue(value);
        if (HAIR_PROBLEMS.contains(normalized)) {
            return normalized;
        }

        return switch (normalized) {
            case "dry", "dryness", "seco" -> "ressecamento";
            case "oily", "oiliness" -> "oleosidade";
            case "frizzy" -> "frizz";
            case "hairloss", "hair loss" -> "queda";
            case "dandruff" -> "caspa";
            case "damaged" -> "danificado";
            case "healthy" -> "saudavel";
            default -> null;
        };
    }

    private String normalizeHairTreatment(String value) {
        String normalized = normalizeValue(value);
        if (HAIR_TREATMENTS.contains(normalized)) {
            return normalized;
        }

        return switch (normalized) {
            case "hydration", "moisturizing", "moisture" -> "hidratacao";
            case "nutrition", "nourishing" -> "nutricao";
            case "reconstruction", "repair" -> "reconstrucao";
            case "anti frizz", "antifrizz" -> "controle de frizz";
            case "strengthening" -> "fortalecimento";
            case "no treatment" -> "sem tratamento necessario";
            default -> null;
        };
    }

    private Integer parseConfidence(String value) {
        try {
            int parsed = Math.round(Float.parseFloat(value));
            if (parsed < 0 || parsed > 100) {
                return null;
            }
            return parsed;
        } catch (Exception ignored) {
            return null;
        }
    }

    private String normalizeValue(String value) {
        if (value == null) {
            return "";
        }

        return Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .trim()
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", " ");
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private String normalizeOutputValue(String value) {
        return normalizeValue(value);
    }
}