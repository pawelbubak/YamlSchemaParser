package org.yaml.schema.parser.internal.schema.reader;

import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.reader.SchemaSpecVersionDesignator;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.internal.schema.property.declaration.Declaration;
import org.yaml.schema.parser.internal.schema.property.declaration.Version;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DefaultSchemaSpecVersionDesignator implements SchemaSpecVersionDesignator {

    public SpecVersion getSpecVersion(Map<String, Object> rawSchema) {
        Set<String> propertyNames = getDeclarationPropertyNames();
        Object property = findPropertyByAllPossibleItsNames(rawSchema, propertyNames);

        Object version = null;
        propertyNames = getVersionPropertyNames();
        if (property == null) {
            version = findPropertyByAllPossibleItsNames(rawSchema, propertyNames);
        } else if (property instanceof Map) {
            Map<String, Object> declarationProperty = (Map<String, Object>) property;
            version = findPropertyByAllPossibleItsNames(declarationProperty, propertyNames);
        }

        if (version instanceof String) {
            return SpecVersion.of((String) version);
        }

        return SpecVersion.current();
    }

    private Set<String> getDeclarationPropertyNames() {
        return getPropertyNames(Declaration.class);
    }

    private Set<String> getVersionPropertyNames() {
        return getPropertyNames(Version.class);
    }

    private Object findPropertyByAllPossibleItsNames(Map<String, Object> rawSchema, Set<String> propertyNames) {
        for (Map.Entry<String, Object> entry : rawSchema.entrySet()) {
            if (propertyNames.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    private Set<String> getPropertyNames(Class<?> propertyClass) {
        Set<String> propertyNames = new HashSet<>();

        String propertyName = getDefaultPropertyName(propertyClass);
        if (!propertyName.isBlank()) {
            propertyNames.add(propertyName);
        }

        propertyNames.addAll(getAnotherPropertyNames(propertyClass));

        return propertyNames;
    }

    private String getDefaultPropertyName(Class<?> propertyClass) {
        return propertyClass.getAnnotation(SchemaPropertyName.class).value();
    }

    private Set<String> getAnotherPropertyNames(Class<?> propertyClass) {
        Set<String> propertyNames = new HashSet<>();

        for (SchemaVersion schemaVersion : propertyClass.getAnnotationsByType(SchemaVersion.class)) {
            if (!schemaVersion.name().isBlank()) {
                propertyNames.add(schemaVersion.name());
            }
        }

        return propertyNames;
    }

}
