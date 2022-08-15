package org.yaml.schema.parser.internal.schema.property.assertion.number;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.SerializationContext;
import org.yaml.schema.parser.api.serializer.Serializer;

@SchemaPropertyContext(SchemaPropertyContext.Type.NUMBER)
@SchemaPropertyName("integer")
@SchemaVersion(SpecVersion.DRAFT_01)
public class IntegerAssertion extends AbstractNumberAssertion<Boolean> {

    public IntegerAssertion(Boolean value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public IntegerAssertion(SpecVersion specVersion, Boolean value)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<Boolean> mapper() {
        return (specVersion, value, propertyFactory) -> new IntegerAssertion(specVersion, value);
    }

    @Override
    public void serialize(Serializer serializer, SerializationContext serializationContext) {
        // DO NOTHING
    }

    @Override
    protected void serializeValue(Serializer serializer, SerializationContext serializationContext) {
        // DO NOTHING
    }

}
