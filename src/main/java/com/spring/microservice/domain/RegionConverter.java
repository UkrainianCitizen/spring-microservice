package com.spring.microservice.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Region Converter.
 */
@Converter
public class RegionConverter implements AttributeConverter<Region, String> {

    @Override
    public String convertToDatabaseColumn(Region region) {
        return region.getLabel();
    }

    @Override
    public Region convertToEntityAttribute(String columnValue) {
        return Region.findByLabel(columnValue);
    }
}
