package org.yaml.schema.parser.internal.schema.type;

import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.type.annotation.SchemaTypeName;
import org.yaml.schema.parser.api.schema.type.mapper.SchemaTypeMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.Serializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SchemaTypeName("number")
@SchemaVersion(SpecVersion.DRAFT_01)
public class NumberType extends AbstractType {

    public NumberType(String name, Map<String, SchemaProperty> properties) {
        this(SpecVersion.current(), name, properties);
    }

    public NumberType(SpecVersion specVersion, String name, Map<String, SchemaProperty> properties) {
        super(specVersion, name, properties);
    }

    public static SchemaTypeMapper<Map<String, Object>> mapper() {
        return (specVersion, name, value, propertyFactory) -> {
            Map<String, SchemaProperty> properties = new HashMap<>();
            for (Map.Entry<String, Object> object : value.entrySet()) {
                properties.put(object.getKey(),
                        propertyFactory.createProperty(SchemaPropertyContext.Type.NUMBER, object.getKey(),
                                object.getValue()));
            }
            return new NumberType(specVersion, name, properties);
        };
    }

    @Override
    public void format(Serializer serializer, Object rawValue) throws IOException {
        if (rawValue == null && isRequired()) {
            serializer.startSimpleElement(name());
            serializer.endSimpleElement();
        } else if (rawValue instanceof Number value) {
            serializer.startSimpleElement(name());
            serializer.writePropertyValue(value);
            serializer.endSimpleElement();
        }
    }

}
