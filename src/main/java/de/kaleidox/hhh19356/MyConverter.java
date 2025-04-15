package de.kaleidox.hhh19356;

import javax.persistence.AttributeConverter;

public class MyConverter implements AttributeConverter<MyOption, String> {
    @Override
    public String convertToDatabaseColumn(MyOption attribute) {
        return attribute.getName();
    }

    @Override
    public MyOption convertToEntityAttribute(String dbData) {
        return MyOption.of(dbData);
    }
}
