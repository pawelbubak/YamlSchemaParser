package org.yaml.schema.parser.internal.schema.property.assertion.array;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;

@SchemaPropertyContext(SchemaPropertyContext.Type.ARRAY)
@SchemaPropertyName("minItems")
@SchemaVersion(SpecVersion.DRAFT_01)
public class MinimumItems extends AbstractArrayLengthAssertion {

    public MinimumItems(Integer value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public MinimumItems(SpecVersion specVersion, Integer value) throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<Integer> mapper() {
        return (specVersion, value, propertyFactory) -> new MinimumItems(specVersion, value);
    }

}
