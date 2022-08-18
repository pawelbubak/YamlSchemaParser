package org.yaml.schema.parser.internal.schema.property.core;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.property.SchemaAnnotationProperty;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.validator.problem.AbstractMessage;
import org.yaml.schema.parser.internal.schema.property.AbstractSchemaSimpleProperty;

import java.util.Map;

public abstract class AbstractDescriptionProperty<T> extends AbstractSchemaSimpleProperty<T>
        implements SchemaAnnotationProperty<T> {

    public AbstractDescriptionProperty(SpecVersion specVersion, T value)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    @Override
    public boolean testValue(Object value) {
        return true;
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
