package org.yaml.schema.parser.internal.schema.property.assertion.number;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.property.SchemaAssertionProperty;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.internal.schema.property.AbstractSchemaSimpleProperty;

public abstract class AbstractNumberAssertion<T> extends AbstractSchemaSimpleProperty<T>
        implements SchemaAssertionProperty<T> {

    public AbstractNumberAssertion(SpecVersion specVersion, T value) throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

}
