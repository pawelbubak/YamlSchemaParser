package org.yaml.schema.parser.internal.schema.property;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.property.SchemaSimpleProperty;
import org.yaml.schema.parser.api.schema.version.SpecVersion;

public abstract class AbstractSchemaSimpleProperty<T> extends AbstractSchemaProperty
        implements SchemaSimpleProperty<T> {
    /**
     * The value of this property.
     */
    private final T value;

    public AbstractSchemaSimpleProperty(SpecVersion specVersion, T value)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion);
        this.value = value;
    }

    @Override
    public T value() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s\n", value().toString());
    }

}
