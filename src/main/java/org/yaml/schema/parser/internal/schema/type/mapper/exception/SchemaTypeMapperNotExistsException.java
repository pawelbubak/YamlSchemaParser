package org.yaml.schema.parser.internal.schema.type.mapper.exception;

public class SchemaTypeMapperNotExistsException extends Exception {

    public SchemaTypeMapperNotExistsException(String schemaPropertyName) {
        super(String.format("No mapper defined for schema type with name %s", schemaPropertyName));
    }

}
