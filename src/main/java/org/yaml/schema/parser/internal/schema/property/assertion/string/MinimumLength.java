package org.yaml.schema.parser.internal.schema.property.assertion.string;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;

@SchemaPropertyContext(SchemaPropertyContext.Type.STRING)
@SchemaPropertyName("minLength")
@SchemaVersion(SpecVersion.DRAFT_01)
public class MinimumLength extends AbstractStringLengthAssertion {

    public MinimumLength(Integer value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public MinimumLength(SpecVersion specVersion, Integer value)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<Integer> mapper() {
        return (specVersion, value, propertyFactory) -> new MinimumLength(specVersion, value);
    }

}
