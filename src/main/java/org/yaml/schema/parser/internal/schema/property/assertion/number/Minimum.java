package org.yaml.schema.parser.internal.schema.property.assertion.number;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.SerializationContext;
import org.yaml.schema.parser.api.serializer.Serializer;

import java.io.IOException;

@SchemaPropertyContext(SchemaPropertyContext.Type.NUMBER)
@SchemaPropertyName("min")
@SchemaVersion(SpecVersion.DRAFT_01)
public class Minimum extends AbstractNumberAssertion<Integer> {

    public Minimum(Integer value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public Minimum(SpecVersion specVersion, Integer value) throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<Integer> mapper() {
        return (specVersion, value, propertyFactory) -> new Minimum(specVersion, value);
    }

    @Override
    protected void serializeValue(Serializer serializer, SerializationContext serializationContext) throws IOException {
        serializer.writePropertyValue(value());
    }

}
