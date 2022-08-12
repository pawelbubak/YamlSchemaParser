package org.yaml.schema.parser.internal.schema.property.assertion.string;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.SerializationContext;
import org.yaml.schema.parser.api.serializer.Serializer;

import java.io.IOException;

@SchemaPropertyContext(SchemaPropertyContext.Type.STRING)
@SchemaPropertyName("pattern")
@SchemaVersion(SpecVersion.DRAFT_01)
public class Pattern extends AbstractStringAssertion<java.util.regex.Pattern> {

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
    protected void serializeValue(Serializer serializer, SerializationContext serializationContext) throws IOException {
        serializer.writePropertyValue(value().pattern());
    }

}
