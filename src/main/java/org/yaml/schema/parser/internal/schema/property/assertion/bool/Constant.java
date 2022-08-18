package org.yaml.schema.parser.internal.schema.property.assertion.bool;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.api.validator.problem.AbstractMessage;
import org.yaml.schema.parser.internal.schema.property.assertion.AbstractBooleanAssertion;

import java.io.IOException;
import java.util.Map;

@SchemaPropertyContext(SchemaPropertyContext.Type.BOOLEAN)
@SchemaPropertyName("const")
@SchemaVersion(SpecVersion.DRAFT_01)
public class Constant extends AbstractBooleanAssertion {

    public Constant(Boolean value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public Constant(SpecVersion specVersion, Boolean value) throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<Boolean> mapper() {
        return (specVersion, value, propertyFactory) -> new Constant(specVersion, value);
    }

    @Override
    protected void serializeValue(Serializer serializer) throws IOException {
        serializer.writePropertyValue(value());
    }

    @Override
    public boolean testValue(Object value) {
        if (value instanceof Boolean) {
            return value().equals(value);
        }
        return false;
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
