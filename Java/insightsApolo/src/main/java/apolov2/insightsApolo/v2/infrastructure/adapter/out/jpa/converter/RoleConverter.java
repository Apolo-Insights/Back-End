package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.converter;

import apolov2.insightsApolo.v2.core.domain.util.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        if (role == null) {
            return "CLIENTE";
        }
        // Salvar como STRING para manter compatibilidade
        return role.name();
    }

    @Override
    public Role convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return Role.CLIENTE; // Valor padrão
        }

        // Tentar primeiro como string
        try {
            return Role.valueOf(dbData.trim().toUpperCase());
        } catch (IllegalArgumentException e1) {
            // Se falhar, tentar como número (dados legados)
            try {
                Integer numeroRole = Integer.parseInt(dbData.trim());
                return switch (numeroRole) {
                    case 0 -> Role.CLIENTE;
                    case 1 -> Role.ADMIN;
                    case 2 -> Role.ESTETICISTA;
                    case 3 -> Role.CABELEIREIRO;
                    case 4 -> Role.MANICURE_PEDICURE;
                    default -> Role.CLIENTE;
                };
            } catch (NumberFormatException e2) {
                // Se nem string nem número funcionam, usar padrão
                System.err.println("Valor role inválido no banco: " + dbData + ". Usando CLIENTE como padrão.");
                return Role.CLIENTE;
            }
        }
    }
}
