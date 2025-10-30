package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.converter;

import apolov2.insightsApolo.v2.core.domain.util.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Role role) {
        if (role == null) {
            return null;
        }
        // Mapear Role para o formato legado numérico
        // 0=CLIENTE, 1=ADMIN, 2=ESTETICISTA, 3=CABELEIREIRO, 4=MANICURE_PEDICURE
        return switch (role) {
            case CLIENTE -> 0;
            case ADMIN -> 1;
            case ESTETICISTA -> 2;
            case CABELEIREIRO -> 3;
            case MANICURE_PEDICURE -> 4;
        };
    }

    @Override
    public Role convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return Role.CLIENTE; // Valor padrão
        }

        // Converter valores numéricos do banco para o enum Role
        return switch (dbData) {
            case 0 -> Role.CLIENTE;
            case 1 -> Role.ADMIN;
            case 2 -> Role.ESTETICISTA;
            case 3 -> Role.CABELEIREIRO;
            case 4 -> Role.MANICURE_PEDICURE;
            default -> Role.CLIENTE; // Fallback para valor desconhecido
        };
    }
}
