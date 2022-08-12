package org.yaml.schema.parser.internal.schema.property.assertion;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.SerializationContext;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.internal.schema.property.AbstractSchemaSimpleProperty;

import java.io.IOException;
import java.util.List;

@SchemaPropertyContext(SchemaPropertyContext.Type.DEFAULT)
@SchemaPropertyName("enum")
@SchemaVersion(SpecVersion.DRAFT_01)
public class Enum extends AbstractSchemaSimpleProperty<List<Object>> {

    public Enum(List<Object> value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public Enum(SpecVersion specVersion, List<Object> value) throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<List<Object>> mapper() {
        return (specVersion, value, propertyFactory) -> new Enum(specVersion, value);
    }

    @Override
    protected void serializeValue(Serializer serializer, SerializationContext serializationContext) throws IOException {
        serializer.writePropertyValue(value().toString());
    }

}
