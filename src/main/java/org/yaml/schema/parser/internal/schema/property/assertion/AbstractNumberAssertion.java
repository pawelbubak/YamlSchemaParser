package org.yaml.schema.parser.internal.schema.property.assertion;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.property.SchemaAssertionProperty;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.internal.schema.property.AbstractSchemaSimpleProperty;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

public abstract class AbstractNumberAssertion extends AbstractSchemaSimpleProperty<BigDecimal>
        implements SchemaAssertionProperty<BigDecimal> {

    public AbstractNumberAssertion(SpecVersion specVersion, BigDecimal value)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    @Override
    protected void serializeValue(Serializer serializer) throws IOException {
        serializer.writePropertyValue(value());
    }

    @Override
    protected Map<String, Object> getProblemMessageArguments() {
        return Collections.singletonMap("limit", value());
    }

}
