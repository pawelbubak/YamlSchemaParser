package org.yaml.schema.parser.internal.schema.property.core.declaration;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.declaration.SchemaDeclaration;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.internal.schema.property.AbstractSchemaComplexProperty;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@SchemaPropertyContext
@SchemaPropertyName("$schema")
@SchemaVersion(SpecVersion.DRAFT_01)
public class Declaration extends AbstractSchemaComplexProperty implements SchemaDeclaration {

    public Declaration(Map<String, SchemaProperty> properties) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), properties);
    }

    public Declaration(SpecVersion specVersion, Map<String, SchemaProperty> properties)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, properties);
    }

    public static SchemaPropertyMapper<Map<String, Object>> mapper() {
        return (specVersion, value, propertyFactory) -> {
            Map<String, SchemaProperty> properties = new HashMap<>();
            for (Map.Entry<String, Object> object : value.entrySet()) {
                properties.put(object.getKey(), propertyFactory.createProperty(object.getKey(), object.getValue()));
            }
            return new Declaration(specVersion, properties);
        };
    }

    @Override
    public int sequenceNumber() {
        return 1;
    }

    @Override
    public URI id() {
        String idPropertyName = getPropertyName(Id.class);
        if (!containsProperty(idPropertyName)) {
            return null;
        }
        Id id = (Id) getProperty(idPropertyName);
        return id.value();
    }

    @Override
    public SpecVersion version() {
        String versionPropertyName = getPropertyName(Version.class);
        if (!containsProperty(versionPropertyName)) {
            return null;
        }
        Version version = (Version) getProperty(versionPropertyName);
        return version.value();
    }

    private String getPropertyName(Class<?> clazz) {
        for (SchemaVersion schemaVersion : clazz.getAnnotationsByType(SchemaVersion.class)) {
            if (getUsedSpecVersion().equals(schemaVersion.value()) && !schemaVersion.name().isBlank()) {
                return schemaVersion.name();
            }
        }
        return clazz.getAnnotation(SchemaPropertyName.class).value();
    }

}
