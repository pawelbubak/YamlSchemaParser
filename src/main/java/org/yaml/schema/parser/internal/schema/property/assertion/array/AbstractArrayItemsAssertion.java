package org.yaml.schema.parser.internal.schema.property.assertion.array;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.SerializationContext;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.internal.schema.property.AbstractSchemaSimpleProperty;

import java.io.IOException;

public abstract class AbstractArrayItemsAssertion extends AbstractSchemaSimpleProperty<SchemaProperty> {

    public AbstractArrayItemsAssertion(SpecVersion specVersion, SchemaProperty value)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    @Override
    public void serialize(Serializer serializer, SerializationContext serializationContext) throws IOException {
//        System.out.print(serializationContext.getIndentation());
//        System.out.printf("%s:\n", name());
//        value().serialize(serializer, serializationContext);
    }

    @Override
    protected void serializeValue(Serializer serializer, SerializationContext serializationContext) throws IOException {
        value().serialize(serializer, serializationContext);
    }

}
