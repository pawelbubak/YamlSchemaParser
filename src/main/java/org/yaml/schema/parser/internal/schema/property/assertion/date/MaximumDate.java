package org.yaml.schema.parser.internal.schema.property.assertion.date;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.validator.problem.AbstractMessage;
import org.yaml.schema.parser.internal.schema.property.assertion.AbstractDateAssertion;
import org.yaml.schema.parser.internal.validator.problem.ValidationMessage;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

@SchemaPropertyContext(SchemaPropertyContext.Type.DATE)
@SchemaPropertyName("maxDate")
@SchemaVersion(SpecVersion.DRAFT_01)
public class MaximumDate extends AbstractDateAssertion {

    public MaximumDate(Date value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public MaximumDate(SpecVersion specVersion, Date value) throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<Date> mapper() {
        return (specVersion, value, propertyFactory) -> new MaximumDate(specVersion, value);
    }

    @Override
    public boolean testValue(Object value) {
        if (value instanceof Date) {
            return ((Date) value).before(value());
        }
        return false;
    }

    @Override
    protected AbstractMessage.Key getProblemMessageCode() {
        return ValidationMessage.Key.MAXIMUM_DATE_VALIDATION_PROBLEM;
    }

    @Override
    protected Map<String, Object> getProblemMessageArguments() {
        return Collections.singletonMap("date", value());
    }

}
