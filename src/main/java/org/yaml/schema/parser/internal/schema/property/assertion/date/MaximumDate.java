package org.yaml.schema.parser.internal.schema.property.assertion.date;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.internal.schema.property.assertion.number.AbstractNumberAssertion;

import java.util.Date;

@SchemaPropertyContext(SchemaPropertyContext.Type.DATE)
@SchemaPropertyName("maxDate")
@SchemaVersion(SpecVersion.DRAFT_01)
public class MaximumDate extends AbstractNumberAssertion<Date> {

    public MaximumDate(Date value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public MaximumDate(SpecVersion specVersion, Date value) throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<Date> mapper() {
        return (specVersion, value, propertyFactory) -> new MaximumDate(specVersion, value);
    }

}
