package org.yaml.schema.parser.internal.schema.property.assertion;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.property.SchemaAssertionProperty;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.internal.schema.property.AbstractSchemaSimpleProperty;

import java.io.IOException;

public abstract class AbstractSchemaPropertyAssertion extends AbstractSchemaSimpleProperty<SchemaProperty>
        implements SchemaAssertionProperty<SchemaProperty> {

    public AbstractSchemaPropertyAssertion(SpecVersion specVersion, SchemaProperty value)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        serializer.startComplexProperty(name());
        serializeValue(serializer);
        serializer.endComplexElement();
    }

    @Override
    protected void serializeValue(Serializer serializer) throws IOException {
        value().serialize(serializer);
    }

}
