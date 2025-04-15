package de.kaleidox.hhh19356;

import javax.persistence.AttributeConverter;
import java.util.UUID;

public class UuidVarchar36Converter implements AttributeConverter<UUID, String> {
    @Override
    public String convertToDatabaseColumn(UUID attribute) {
        return attribute.toString();
    }

    @Override
    public UUID convertToEntityAttribute(String dbData) {
        return dbData == null ? null : UUID.fromString(dbData);
    }
}
