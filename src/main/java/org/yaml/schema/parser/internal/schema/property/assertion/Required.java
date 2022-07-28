package org.yaml.schema.parser.internal.schema.property.assertion;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.internal.schema.property.AbstractSchemaSimpleProperty;

@SchemaPropertyContext(SchemaPropertyContext.Type.DEFAULT)
@SchemaPropertyName("required")
@SchemaVersion(SpecVersion.DRAFT_01)
public class Required extends AbstractSchemaSimpleProperty<Boolean> {

    public Required(Boolean value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public Required(SpecVersion specVersion, Boolean value) throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<Boolean> mapper() {
        return (specVersion, value, propertyFactory) -> new Required(specVersion, value);
    }

}
