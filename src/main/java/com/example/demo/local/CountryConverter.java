package com.example.demo.local;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CountryConverter
        implements AttributeConverter<Country, String> {

    public String convertToDatabaseColumn(Country value) {
        if (value == null) {
            return null;
        }

        return value.name();
    }

    public Country convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }

        return Country.valueOf(value);
    }
}