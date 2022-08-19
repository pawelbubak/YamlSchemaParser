package org.yaml.schema.parser.internal.schema.property.assertion.number;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.SchemaAssertionProperty;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.api.validator.problem.AbstractMessage;
import org.yaml.schema.parser.internal.schema.property.AbstractSchemaSimpleProperty;
import org.yaml.schema.parser.internal.validator.problem.ValidationMessage;

import java.math.BigDecimal;
import java.util.Map;

import static org.yaml.schema.parser.internal.schema.property.assertion.utils.MapperUtils.mapToBigDecimal;

@SchemaPropertyContext(SchemaPropertyContext.Type.NUMBER)
@SchemaPropertyName("integer")
@SchemaVersion(SpecVersion.DRAFT_01)
public class IntegerAssertion extends AbstractSchemaSimpleProperty<Boolean>
        implements SchemaAssertionProperty<Boolean> {

    public IntegerAssertion(Boolean value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public IntegerAssertion(SpecVersion specVersion, Boolean value)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<Boolean> mapper() {
        return (specVersion, value, propertyFactory) -> new IntegerAssertion(specVersion, value);
    }

    @Override
    public void serialize(Serializer serializer) {
        // DO NOTHING
    }

    @Override
    protected void serializeValue(Serializer serializer) {
        // DO NOTHING
    }

    @Override
    public boolean testValue(Object rawValue) {
        if (rawValue != null) {
            try {
                BigDecimal value = mapToBigDecimal(rawValue);
                return value.signum() == 0 || value.scale() <= 0 || value.stripTrailingZeros().scale() <= 0;
            } catch (NumberFormatException ignored) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected AbstractMessage.Key getProblemMessageCode() {
        return ValidationMessage.Key.INTEGER_VALIDATION_PROBLEM;
    }

    @Override
    protected Map<String, Object> getProblemMessageArguments() {
        return null;
    }

}
