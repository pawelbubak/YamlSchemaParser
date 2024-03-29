package org.yaml.schema.parser.internal.schema.property.core;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.Serializer;

import java.io.IOException;

@SchemaPropertyContext
@SchemaPropertyName("sequence")
@SchemaVersion(SpecVersion.DRAFT_01)
public class Sequence extends AbstractDescriptionProperty<Integer> {

    public Sequence(Integer value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public Sequence(SpecVersion specVersion, Integer value) throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<Integer> mapper() {
        return (specVersion, value, propertyFactory) -> new Sequence(specVersion, value);
    }

    @Override
    public int sequenceNumber() {
        return 3;
    }

    @Override
    protected void serializeValue(Serializer serializer) throws IOException {
        serializer.writePropertyValue(value());
    }

}
