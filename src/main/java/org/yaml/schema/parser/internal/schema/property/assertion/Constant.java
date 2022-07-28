package org.yaml.schema.parser.internal.schema.property.assertion;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.internal.schema.property.AbstractSchemaSimpleProperty;

@SchemaPropertyContext(SchemaPropertyContext.Type.DEFAULT)
@SchemaPropertyName("const")
@SchemaVersion(SpecVersion.DRAFT_01)
public class Constant extends AbstractSchemaSimpleProperty<Object> {

    public Constant(Object value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public Constant(SpecVersion specVersion, Object value) throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<Object> mapper() {
        return (specVersion, value, propertyFactory) -> new Constant(specVersion, value);
    }

}
