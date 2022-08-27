package org.yaml.schema.parser.internal.schema.type;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.type.annotation.SchemaTypeName;
import org.yaml.schema.parser.api.schema.type.mapper.SchemaTypeMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.internal.schema.property.core.Properties;
import org.yaml.schema.parser.internal.utils.SchemaPropertyNameDesignator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SchemaTypeName("object")
@SchemaVersion(SpecVersion.DRAFT_01)
public class ObjectType extends AbstractType {

    public ObjectType(String name, Map<String, SchemaProperty> properties) {
        this(SpecVersion.current(), name, properties);
    }

    public ObjectType(SpecVersion specVersion, String name, Map<String, SchemaProperty> properties) {
        super(specVersion, name, properties);
    }

    public static SchemaTypeMapper<Map<String, Object>> mapper() {
        return (specVersion, name, value, propertyFactory) -> {
            Map<String, SchemaProperty> properties = new HashMap<>();
            for (Map.Entry<String, Object> object : value.entrySet()) {
                properties.put(object.getKey(),
                        propertyFactory.createProperty(SchemaPropertyContext.Type.OBJECT, object.getKey(),
                                object.getValue()));
            }
            String propertiesPropertyName = getPropertiesPropertyName(specVersion);
            if (!properties.containsKey(propertiesPropertyName)) {
                properties.put(propertiesPropertyName,
                        propertyFactory.createProperty(SchemaPropertyContext.Type.OBJECT, propertiesPropertyName,
                                new HashMap<String, Object>()));
            }
            return new ObjectType(specVersion, name, properties);
        };
    }

    private static String getPropertiesPropertyName(SpecVersion specVersion) {
        try {
            return SchemaPropertyNameDesignator.designatePropertyName(Properties.class, specVersion);
        } catch (SchemaPropertyNotExistsInSpecificationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void format(Serializer serializer, Object rawValue) throws IOException {
        if (rawValue instanceof Map<?, ?> values) {
            if (values.isEmpty()) {
                serializer.startSimpleElement(name());
                serializer.writeEmptyObject();
                serializer.endSimpleElement();
            } else {
                try {
                    String propertyName = SchemaPropertyNameDesignator.designatePropertyName(Properties.class,
                            getUsedSpecVersion());
                    SchemaProperty property = getProperty(propertyName);

                    serializer.startComplexElement(name());
                    property.format(serializer, values);
                    serializer.endComplexElement();
                } catch (SchemaPropertyNotExistsInSpecificationException e) {
                    throw new RuntimeException(e);
                }
            }
        } else if (rawValue == null && isRequired()) {
            serializer.startComplexElement(name());
            serializer.endComplexElement();
        }
    }

}
