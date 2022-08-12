package org.yaml.schema.parser.internal.schema.property.assertion.map;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.SerializationContext;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.internal.schema.property.AbstractSchemaSimpleProperty;

import java.io.IOException;

public abstract class AbstractMapItemsAssertion extends AbstractSchemaSimpleProperty<SchemaProperty> {

    public AbstractMapItemsAssertion(SpecVersion specVersion, SchemaProperty value)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    @Override
    public void serialize(Serializer serializer, SerializationContext serializationContext) throws IOException {
        serializeValue(serializer, serializationContext);
    }

    @Override
    protected void serializeValue(Serializer serializer, SerializationContext serializationContext) throws IOException {
        value().serialize(serializer, serializationContext);
    }

}
