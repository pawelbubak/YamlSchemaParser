package org.yaml.schema.parser.internal.schema.property.assertion.string;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.version.SpecVersion;

public abstract class AbstractStringLengthAssertion extends AbstractStringAssertion<Integer> {

    public AbstractStringLengthAssertion(SpecVersion specVersion, Integer value)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

}
