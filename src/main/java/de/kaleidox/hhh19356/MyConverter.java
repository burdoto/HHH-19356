package de.kaleidox.hhh19356;

import javax.persistence.AttributeConverter;
import java.time.Instant;

public class MyConverter implements AttributeConverter<Instant, String> {
    @Override
    public String convertToDatabaseColumn(Instant attribute) {
        return String.valueOf(attribute.toEpochMilli());
    }

    @Override
    public Instant convertToEntityAttribute(String dbData) {
        return Instant.ofEpochMilli(Long.parseLong(dbData));
    }
}
