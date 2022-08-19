package org.yaml.schema.parser.internal.schema.property.assertion.array;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.validator.problem.AbstractMessage;
import org.yaml.schema.parser.internal.schema.property.assertion.AbstractNumberAssertion;

import java.math.BigDecimal;

import static org.yaml.schema.parser.internal.schema.property.assertion.utils.MapperUtils.mapToBigDecimal;

@SchemaPropertyContext(SchemaPropertyContext.Type.ARRAY)
@SchemaPropertyName("maxItems")
@SchemaVersion(SpecVersion.DRAFT_01)
public class MaximumItems extends AbstractNumberAssertion {

    public MaximumItems(BigDecimal value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public MaximumItems(SpecVersion specVersion, BigDecimal value) throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<Number> mapper() {
        return (specVersion, value, propertyFactory) -> new MaximumItems(specVersion, mapToBigDecimal(value));
    }

    @Override
    public boolean testValue(Object value) {
        return false;
    }

    @Override
    protected AbstractMessage.Key getProblemMessageCode() {
        return null;
    }

}
