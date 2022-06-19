package org.yaml.schema.parser.internal.schema.property.factory.exception;

public class SchemaPropertyMapperCreationException extends RuntimeException {

    public SchemaPropertyMapperCreationException(String propertyName, Exception e) {
        super(String.format("The %s property mapper could not be created.", propertyName), e);
    }

}
