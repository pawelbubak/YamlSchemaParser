package org.yaml.schema.parser.internal.schema.property.assertion;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.property.SchemaAssertionProperty;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.internal.schema.property.AbstractSchemaSimpleProperty;

import java.io.IOException;

public abstract class AbstractBooleanAssertion extends AbstractSchemaSimpleProperty<Boolean>
        implements SchemaAssertionProperty<Boolean> {

    public AbstractBooleanAssertion(SpecVersion specVersion, Boolean value)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    @Override
    protected void serializeValue(Serializer serializer) throws IOException {
        serializer.writePropertyValue(value());
    }

}
