package org.yaml.schema.parser.internal.schema.property.factory.exception;

public class SchemaPropertyContextNotSetException extends RuntimeException {

    public SchemaPropertyContextNotSetException(String propertyName) {
        super(String.format("The %s property has no context set.", propertyName));
    }

}
