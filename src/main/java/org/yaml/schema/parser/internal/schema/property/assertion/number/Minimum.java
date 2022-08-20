package org.yaml.schema.parser.internal.schema.property.assertion.number;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.validator.problem.AbstractMessage;
import org.yaml.schema.parser.internal.schema.property.assertion.AbstractNumberAssertion;
import org.yaml.schema.parser.internal.validator.problem.ValidationMessage;

import java.math.BigDecimal;

import static org.yaml.schema.parser.internal.schema.property.assertion.utils.MapperUtils.mapToBigDecimal;

@SchemaPropertyContext(SchemaPropertyContext.Type.NUMBER)
@SchemaPropertyName("minimum")
@SchemaVersion(SpecVersion.DRAFT_01)
public class Minimum extends AbstractNumberAssertion {

    public Minimum(BigDecimal value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public Minimum(SpecVersion specVersion, BigDecimal value) throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<Number> mapper() {
        return (specVersion, value, propertyFactory) -> new Minimum(specVersion, mapToBigDecimal(value));
    }

    @Override
    public boolean testValue(Object rawValue) {
        if (rawValue != null) {
            BigDecimal value = mapToBigDecimal(rawValue);
            return value.compareTo(value()) >= 0;
        }
        return false;
    }

    @Override
    protected AbstractMessage.Key getProblemMessageCode() {
        return ValidationMessage.Key.MINIMUM_VALIDATION_PROBLEM;
    }

}
