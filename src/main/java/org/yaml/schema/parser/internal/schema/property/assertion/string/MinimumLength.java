package org.yaml.schema.parser.internal.schema.property.assertion.string;

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

@SchemaPropertyContext(SchemaPropertyContext.Type.STRING)
@SchemaPropertyName("minimumLength")
@SchemaVersion(SpecVersion.DRAFT_01)
public class MinimumLength extends AbstractNumberAssertion {

    public MinimumLength(BigDecimal value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public MinimumLength(SpecVersion specVersion, BigDecimal value)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<Number> mapper() {
        return (specVersion, value, propertyFactory) -> new MinimumLength(specVersion, mapToBigDecimal(value));
    }

    @Override
    public boolean testValue(Object rawValue) {
        if (rawValue instanceof String) {
            BigDecimal value = mapToBigDecimal(((String) rawValue).length());
            return value.compareTo(value()) >= 0;
        }
        return false;
    }

    @Override
    protected AbstractMessage.Key getProblemMessageCode() {
        return ValidationMessage.Key.MINIMUM_LENGTH_VALIDATION_PROBLEM;
    }

}
