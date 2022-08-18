package org.yaml.schema.parser.internal.schema.property.assertion.array;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.api.validator.YamlValidator;
import org.yaml.schema.parser.api.validator.problem.AbstractMessage;
import org.yaml.schema.parser.internal.schema.property.assertion.AbstractSchemaPropertyAssertion;
import org.yaml.schema.parser.internal.utils.SchemaPropertyNameDesignator;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SchemaPropertyContext(SchemaPropertyContext.Type.ARRAY)
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

    public static SchemaPropertyMapper<Object> mapper() {
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
        if (rawValue instanceof List<?> values) {
            for (int i = 0; i < values.size(); i++) {
                validator.getContext().push(i);
                Object value = values.get(i);
                value().test(validator, value);
                validator.getContext().pop();
            }
        }
    }

    @Override
    public boolean testValue(Object rawValue) {
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

}
