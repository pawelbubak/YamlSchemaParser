package org.yaml.schema.parser.internal.schema.property.mapper.exception;

import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;

public class SchemaPropertyMapperNotExistsException extends Exception {

    public SchemaPropertyMapperNotExistsException(SchemaPropertyContext.Type schemaPropertyContext) {
        super(String.format("No mappers have been defined in the %s context.", schemaPropertyContext));
    }

    public SchemaPropertyMapperNotExistsException(SchemaPropertyContext.Type schemaPropertyContext,
            String schemaPropertyName) {
        super(String.format("No mapper defined for schema property %s in %s context.", schemaPropertyName,
                schemaPropertyContext));
    }

}
