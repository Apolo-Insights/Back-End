package apolov2.insightsApolo.v2.infrastructure.adapter.out.azure;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class AzureBlobService {
    private final String connectionString = "DefaultEndpointsProtocol=https;AccountName=blobgrupo4;AccountKey=vFlz7Wlm++uFOa12X915kX4+2SNxCVcwXtNNKffNF4fE1JdcIwGCMSWeY8KoF1zMxa5hORpJLo61+ASt4ksyVA==;EndpointSuffix=core.windows.net";

    public String upload(byte[] imagemBytes, String containerName) {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();

        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        if (!containerClient.exists()) containerClient.create();

        String fileName = UUID.randomUUID() + ".png";
        BlobClient blobClient = containerClient.getBlobClient(fileName);

        try (InputStream is = new ByteArrayInputStream(imagemBytes)) {
            blobClient.upload(is, imagemBytes.length, true);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao enviar imagem", e);
        }

        return blobClient.getBlobUrl();
    }
}
