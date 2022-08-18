package org.yaml.schema.parser.internal.schema.property.assertion.string;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.SchemaAssertionProperty;
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
import java.util.Map;

@SchemaPropertyContext(SchemaPropertyContext.Type.STRING)
@SchemaPropertyName("pattern")
@SchemaVersion(SpecVersion.DRAFT_01)
public class Pattern extends AbstractSchemaSimpleProperty<java.util.regex.Pattern>
        implements SchemaAssertionProperty<java.util.regex.Pattern> {

    public Pattern(String value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public Pattern(java.util.regex.Pattern value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public Pattern(SpecVersion specVersion, String value) throws SchemaPropertyNotExistsInSpecificationException {
        this(specVersion, java.util.regex.Pattern.compile(value));
    }

    public Pattern(SpecVersion specVersion, java.util.regex.Pattern value)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<String> mapper() {
        return (specVersion, value, propertyFactory) -> new Pattern(specVersion, value);
    }

    @Override
    protected void serializeValue(Serializer serializer) throws IOException {
        serializer.writePropertyValue(value().pattern());
    }

    @Override
    public boolean testValue(Object value) {
        if (value instanceof String) {
            return value().matcher((String) value).matches();
        }
        return false;
    }

    @Override
    protected AbstractMessage.Key getProblemMessageCode() {
        return ValidationMessage.Key.STRING_PATTERN_VALIDATION_PROBLEM;
    }

    @Override
    protected Map<String, Object> getProblemMessageArguments() {
        return Collections.singletonMap("pattern", value().pattern());
    }

}
