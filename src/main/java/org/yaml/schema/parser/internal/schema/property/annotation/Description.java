package org.yaml.schema.parser.internal.schema.property.annotation;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.SchemaAnnotationProperty;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.SerializationContext;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.internal.schema.property.AbstractSchemaSimpleProperty;

import java.io.IOException;

@SchemaPropertyContext
@SchemaPropertyName("description")
@SchemaVersion(SpecVersion.DRAFT_01)
public class Description extends AbstractSchemaSimpleProperty<String> implements SchemaAnnotationProperty<String> {

    public Description(String value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public Description(SpecVersion specVersion, String value) throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    @Override
    public int sequenceNumber() {
        return 3;
    }

    public static SchemaPropertyMapper<String> mapper() {
        return (specVersion, value, propertyFactory) -> new Description(specVersion, value);
    }

    @Override
    protected void serializeValue(Serializer serializer, SerializationContext serializationContext) throws IOException {
        serializer.writePropertyValue(value());
    }

}
