package org.yaml.schema.parser.internal.schema.property.assertion.map;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.validator.problem.AbstractMessage;
import org.yaml.schema.parser.internal.schema.property.assertion.AbstractNumberAssertion;

import java.math.BigDecimal;
import java.util.Map;

import static org.yaml.schema.parser.internal.schema.property.assertion.utils.MapperUtils.mapToBigDecimal;

@SchemaPropertyContext(SchemaPropertyContext.Type.MAP)
@SchemaPropertyName("maximumItems")
@SchemaVersion(SpecVersion.DRAFT_01)
public class MaximumItems extends AbstractNumberAssertion {

    public MaximumItems(BigDecimal value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public MaximumItems(SpecVersion specVersion, BigDecimal value)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<Number> mapper() {
        return (specVersion, value, propertyFactory) -> new MaximumItems(specVersion, mapToBigDecimal(value));
    }

    @Override
    public boolean testValue(Object rawValue) {
        if (rawValue instanceof Map<?, ?> values) {
            BigDecimal mapSize = mapToBigDecimal(values.size());
            return mapSize.compareTo(value()) <= 0;
        }
        return true;
    }

    @Override
    protected AbstractMessage.Key getProblemMessageCode() {
        return null;
    }

}
