package org.yaml.schema.parser.internal.schema.property.assertion.bool;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.SerializationContext;
import org.yaml.schema.parser.api.serializer.Serializer;

import java.io.IOException;

@SchemaPropertyContext(SchemaPropertyContext.Type.BOOLEAN)
@SchemaPropertyName("boolean")
@SchemaVersion(SpecVersion.DRAFT_01)
public class BooleanAssertion extends AbstractBooleanAssertion<Boolean> {

    public BooleanAssertion(Boolean value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public BooleanAssertion(SpecVersion specVersion, Boolean value)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<Boolean> mapper() {
        return (specVersion, value, propertyFactory) -> new BooleanAssertion(specVersion, value);
    }

    @Override
    public void serialize(Serializer serializer, SerializationContext serializationContext) throws IOException {
        // DO NOTHING
    }

    @Override
    protected void serializeValue(Serializer serializer, SerializationContext serializationContext) throws IOException {
        // DO NOTHING
    }

}
