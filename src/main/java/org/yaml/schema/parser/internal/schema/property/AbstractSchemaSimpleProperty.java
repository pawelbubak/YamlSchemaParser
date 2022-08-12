package org.yaml.schema.parser.internal.schema.property;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.property.SchemaSimpleProperty;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.SerializationContext;
import org.yaml.schema.parser.api.serializer.Serializer;

import java.io.IOException;

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
    public void serialize(Serializer serializer, SerializationContext serializationContext) throws IOException {
        serializer.startSimpleElement(name());
        serializeValue(serializer, serializationContext);
        serializer.endSimpleElement();
    }

    protected abstract void serializeValue(Serializer serializer, SerializationContext serializationContext)
            throws IOException;

}
