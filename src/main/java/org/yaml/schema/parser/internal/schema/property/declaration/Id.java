package org.yaml.schema.parser.internal.schema.property.declaration;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.SchemaDeclarationProperty;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.internal.schema.property.AbstractSchemaSimpleProperty;

import java.net.URI;

@SchemaPropertyContext
@SchemaPropertyName("id")
@SchemaVersion(SpecVersion.DRAFT_01)
public class Id extends AbstractSchemaSimpleProperty<URI> implements SchemaDeclarationProperty<URI> {

    public Id(URI value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public Id(SpecVersion specVersion, URI value) throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    @Override
    public int sequenceNumber() {
        return 2;
    }

    public static SchemaPropertyMapper<String> mapper() {
        return (specVersion, value, propertyFactory) -> new Id(specVersion, new URI(value));
    }

}
