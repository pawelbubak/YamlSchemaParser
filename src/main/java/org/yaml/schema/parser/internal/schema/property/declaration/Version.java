package org.yaml.schema.parser.internal.schema.property.declaration;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.SchemaDeclarationProperty;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.SerializationContext;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.internal.schema.property.AbstractSchemaSimpleProperty;

import java.io.IOException;

@SchemaPropertyContext
@SchemaPropertyName("version")
@SchemaVersion(SpecVersion.DRAFT_01)
public class Version extends AbstractSchemaSimpleProperty<SpecVersion>
        implements SchemaDeclarationProperty<SpecVersion> {

    public Version() throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current());
    }

    public Version(SpecVersion specVersion) throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, specVersion);
    }

    public static SchemaPropertyMapper<String> mapper() {
        return (specVersion, value, provider) -> new Version(SpecVersion.of(value));
    }

    @Override
    public int sequenceNumber() {
        return 1;
    }

    @Override
    protected void serializeValue(Serializer serializer, SerializationContext serializationContext) throws IOException {
        serializer.writePropertyValue(value().id());
    }

}
