package org.yaml.schema.parser.internal.schema.property.assertion.string;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.api.validator.problem.AbstractMessage;
import org.yaml.schema.parser.internal.schema.property.AbstractSchemaSimpleProperty;
import org.yaml.schema.parser.internal.validator.problem.ValidationMessage;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SchemaPropertyContext(SchemaPropertyContext.Type.STRING)
@SchemaPropertyName("enum")
@SchemaVersion(SpecVersion.DRAFT_01)
public class Enum extends AbstractSchemaSimpleProperty<List<String>> {

    public Enum(List<String> value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public Enum(SpecVersion specVersion, List<String> value) throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<List<String>> mapper() {
        return (specVersion, value, propertyFactory) -> new Enum(specVersion, value);
    }

    @Override
    protected void serializeValue(Serializer serializer) throws IOException {
        serializer.writePropertyValue(value());
    }

    @Override
    public boolean testValue(Object value) {
        if (value instanceof String) {
            return value().contains(value);
        }
        return false;
    }

    @Override
    protected AbstractMessage.Key getProblemMessageCode() {
        return ValidationMessage.Key.ENUM_VALIDATION_PROBLEM;
    }

    @Override
    protected Map<String, Object> getProblemMessageArguments() {
        String expectedValues = String.join(",", value());
        return Collections.singletonMap("expected", expectedValues);
    }

}
