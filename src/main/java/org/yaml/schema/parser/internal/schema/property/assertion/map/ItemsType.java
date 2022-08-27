package org.yaml.schema.parser.internal.schema.property.assertion.map;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.SchemaComplexProperty;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.property.SchemaSimpleProperty;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.api.validator.YamlValidator;
import org.yaml.schema.parser.api.validator.problem.AbstractMessage;
import org.yaml.schema.parser.internal.schema.property.assertion.AbstractSchemaPropertyAssertion;
import org.yaml.schema.parser.internal.schema.property.core.Type;
import org.yaml.schema.parser.internal.schema.type.ArrayType;
import org.yaml.schema.parser.internal.schema.type.MapType;
import org.yaml.schema.parser.internal.schema.type.ObjectType;
import org.yaml.schema.parser.internal.utils.SchemaPropertyNameDesignator;
import org.yaml.schema.parser.internal.utils.SchemaTypeNameDesignator;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SchemaPropertyContext(SchemaPropertyContext.Type.MAP)
@SchemaPropertyName("itemsType")
@SchemaVersion(SpecVersion.DRAFT_01)
public class ItemsType extends AbstractSchemaPropertyAssertion {

    public ItemsType(SchemaProperty value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public ItemsType(SpecVersion specVersion, SchemaProperty value)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<Map<String, Object>> mapper() {
        return (specVersion, value, propertyFactory) -> {
            String propertyName = SchemaPropertyNameDesignator.designatePropertyName(ItemsType.class, specVersion);
            SchemaProperty typeProperty = propertyFactory.createType(propertyName, value);
            return new ItemsType(specVersion, typeProperty);
        };
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        serializeValue(serializer);
    }

    @Override
    public void test(YamlValidator validator, Object rawValue) {
        if (rawValue instanceof Map<?, ?> values) {
            for (Map.Entry<?, ?> entry : values.entrySet()) {
                validator.startElement(entry.getKey());
                Object value = entry.getValue();
                value().test(validator, value);
                validator.endElement();
            }
        }
    }

    @Override
    public void format(Serializer serializer, Object rawValue) throws IOException {
        if (rawValue instanceof Map.Entry<?, ?> valueEntry) {
            if (SchemaComplexProperty.class.isAssignableFrom(value().getClass())) {
                SchemaComplexProperty property = (SchemaComplexProperty) value();
                String type = getPropertyType(property);
                if (valueEntry.getValue() == null) {
                    serializer.startSimpleMapElement(valueEntry.getKey());
                    serializer.startSimpleElement(valueEntry.getKey());
                    serializer.endSimpleElement();
                    serializer.endSimpleMapElement();
                } else if (isComplexMapElement(type)) {
                    if (valueEntry.getValue() instanceof List<?> value && value.isEmpty()) {
                        serializer.startSimpleMapElement(valueEntry.getKey());
                        serializer.startSimpleElement(valueEntry.getKey());
                        serializer.writeEmptyObject();
                        serializer.endSimpleElement();
                        serializer.endSimpleMapElement();
                    } else if (valueEntry.getValue() instanceof Map<?, ?> value && value.isEmpty()) {
                        serializer.startSimpleMapElement(valueEntry.getKey());
                        serializer.startSimpleElement(valueEntry.getKey());
                        serializer.writeEmptyObject();
                        serializer.endSimpleElement();
                        serializer.endSimpleMapElement();
                    } else {
                        serializer.startComplexMapElement(valueEntry.getKey());
                        value().format(serializer, valueEntry.getValue());
                        serializer.endComplexMapElement();
                    }
                } else {
                    serializer.startSimpleMapElement(valueEntry.getKey());
                    value().format(serializer, valueEntry.getValue());
                    serializer.endSimpleMapElement();
                }
            }
        }
    }

    @Override
    public boolean testValue(Object value) {
        return true;
    }

    @Override
    protected AbstractMessage.Key getProblemMessageCode() {
        return null;
    }

    @Override
    protected Map<String, Object> getProblemMessageArguments() {
        return null;
    }

    private String getPropertyType(SchemaComplexProperty property) {
        try {
            String typePropertyName = SchemaPropertyNameDesignator.designatePropertyName(Type.class,
                    getUsedSpecVersion());
            SchemaSimpleProperty<?> typeProperty = (SchemaSimpleProperty<?>) property.getProperty(typePropertyName);
            return (String) typeProperty.value();
        } catch (SchemaPropertyNotExistsInSpecificationException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isComplexMapElement(String type) {
        return type.equals(getTypeName(ArrayType.class)) || type.equals(getTypeName(MapType.class)) ||
                type.equals(getTypeName(ObjectType.class));
    }

    private String getTypeName(Class<? extends SchemaProperty> typeClass) {
        try {
            return SchemaTypeNameDesignator.designateTypeName(typeClass, getUsedSpecVersion());
        } catch (SchemaPropertyNotExistsInSpecificationException e) {
            throw new RuntimeException(e);
        }
    }

}
