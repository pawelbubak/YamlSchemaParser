package org.yaml.schema.parser.internal.utils;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.version.SpecVersion;

public class SchemaPropertyNameDesignator {

    public static String designatePropertyName(Class<? extends SchemaProperty> clazz, SpecVersion specVersion)
            throws SchemaPropertyNotExistsInSpecificationException {
        for (SchemaVersion schemaVersion : clazz.getAnnotationsByType(SchemaVersion.class)) {
            if (specVersion.equals(schemaVersion.value())) {
                if (schemaVersion.name().isBlank()) {
                    return getDefaultPropertyName(clazz);
                } else {
                    return schemaVersion.name();
                }
            }
        }
        throw new SchemaPropertyNotExistsInSpecificationException(getDefaultPropertyName(clazz), specVersion);
    }

    private static String getDefaultPropertyName(Class<? extends SchemaProperty> clazz) {
        SchemaPropertyName schemaPropertyName = clazz.getAnnotation(SchemaPropertyName.class);
        return schemaPropertyName.value();
    }

}
