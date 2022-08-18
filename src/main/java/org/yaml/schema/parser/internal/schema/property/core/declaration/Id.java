package org.yaml.schema.parser.internal.schema.property.core.declaration;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.internal.schema.property.core.AbstractDescriptionProperty;

import java.io.IOException;
import java.net.URI;

@SchemaPropertyContext
@SchemaPropertyName("id")
@SchemaVersion(SpecVersion.DRAFT_01)
public class Id extends AbstractDescriptionProperty<URI> {

    public Id(URI value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public Id(SpecVersion specVersion, URI value) throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<String> mapper() {
        return (specVersion, value, propertyFactory) -> new Id(specVersion, new URI(value));
    }

    @Override
    public int sequenceNumber() {
        return 2;
    }

    @Override
    protected void serializeValue(Serializer serializer) throws IOException {
        serializer.writePropertyValue(value().toString());
    }

}
