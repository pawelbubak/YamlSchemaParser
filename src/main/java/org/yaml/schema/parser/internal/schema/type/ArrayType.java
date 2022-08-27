package org.yaml.schema.parser.internal.schema.type;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.type.annotation.SchemaTypeName;
import org.yaml.schema.parser.api.schema.type.mapper.SchemaTypeMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.internal.schema.property.assertion.array.ItemsType;
import org.yaml.schema.parser.internal.utils.SchemaPropertyNameDesignator;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SchemaTypeName("array")
@SchemaVersion(SpecVersion.DRAFT_01)
public class ArrayType extends AbstractType {

    public ArrayType(String name, Map<String, SchemaProperty> properties) {
        this(SpecVersion.current(), name, properties);
    }

    public ArrayType(SpecVersion specVersion, String name, Map<String, SchemaProperty> properties) {
        super(specVersion, name, properties);
    }

    public static SchemaTypeMapper<Map<String, Object>> mapper() {
        return (specVersion, name, value, propertyFactory) -> {
            Map<String, SchemaProperty> properties = new HashMap<>();
            for (Map.Entry<String, Object> object : value.entrySet()) {
                properties.put(object.getKey(),
                        propertyFactory.createProperty(SchemaPropertyContext.Type.ARRAY, object.getKey(),
                                object.getValue()));
            }
            return new ArrayType(specVersion, name, properties);
        };
    }

    @Override
    public void format(Serializer serializer, Object rawValue) throws IOException {
        if (rawValue instanceof List<?> values) {
            serializer.startComplexElement(name());
            try {
                String propertyName = SchemaPropertyNameDesignator.designatePropertyName(ItemsType.class,
                        getUsedSpecVersion());
                SchemaProperty property = getProperty(propertyName);

                for (Object value : values) {
                    property.format(serializer, value);
                }
            } catch (SchemaPropertyNotExistsInSpecificationException e) {
                throw new RuntimeException(e);
            }
            serializer.endComplexElement();
        }
    }

}
