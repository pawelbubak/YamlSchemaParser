package org.yaml.schema.parser.internal.schema.property.assertion.date;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.api.validator.problem.AbstractMessage;
import org.yaml.schema.parser.internal.schema.property.assertion.AbstractDateAssertion;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@SchemaPropertyContext(SchemaPropertyContext.Type.DATE)
@SchemaPropertyName("constant")
@SchemaVersion(SpecVersion.DRAFT_01)
public class Constant extends AbstractDateAssertion {

    public Constant(Date value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public Constant(SpecVersion specVersion, Date value) throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<Date> mapper() {
        return (specVersion, value, propertyFactory) -> new Constant(specVersion, value);
    }

    @Override
    protected void serializeValue(Serializer serializer) throws IOException {
        serializer.writePropertyValue(value());
    }

    @Override
    public boolean testValue(Object value) {
        if (value instanceof Date) {
            return value().equals(value);
        }
        return false;
    }

    @Override
    protected AbstractMessage.Key getProblemMessageCode() {
        return null;
    }

    @Override
    protected Map<String, Object> getProblemMessageArguments() {
        return null;
    }

}
