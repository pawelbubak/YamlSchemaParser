package org.yaml.schema.parser.internal.utils;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.type.annotation.SchemaTypeName;
import org.yaml.schema.parser.api.schema.version.SpecVersion;

public class SchemaTypeNameDesignator {

    public static String designateTypeName(Class<? extends SchemaProperty> clazz, SpecVersion specVersion)
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
        SchemaTypeName schemaPropertyName = clazz.getAnnotation(SchemaTypeName.class);
        return schemaPropertyName.value();
    }

}
