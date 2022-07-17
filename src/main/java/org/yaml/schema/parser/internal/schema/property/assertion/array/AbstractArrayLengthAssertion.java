package org.yaml.schema.parser.internal.schema.property.assertion.array;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.version.SpecVersion;

public abstract class AbstractArrayLengthAssertion extends AbstractArrayAssertion<Integer> {

    public AbstractArrayLengthAssertion(SpecVersion specVersion, Integer value)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

}
