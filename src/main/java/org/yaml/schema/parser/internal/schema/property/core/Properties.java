package org.yaml.schema.parser.internal.schema.property.core;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.internal.schema.property.AbstractSchemaComplexProperty;

import java.util.HashMap;
import java.util.Map;

@SchemaPropertyContext
@SchemaPropertyName("properties")
@SchemaVersion(SpecVersion.DRAFT_01)
public class Properties extends AbstractSchemaComplexProperty {

    public Properties(Map<String, SchemaProperty> properties) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), properties);
    }

    public Properties(SpecVersion specVersion, Map<String, SchemaProperty> properties)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, properties);
    }

    public static SchemaPropertyMapper<Map<String, Object>> mapper() {
        return (specVersion, value, propertyFactory) -> {
            Map<String, SchemaProperty> properties = new HashMap<>();
            for (Map.Entry<String, Object> object : value.entrySet()) {
                properties.put(object.getKey(), propertyFactory.createType(object.getKey(), object.getValue()));
            }
            return new Properties(specVersion, properties);
        };
    }

}
