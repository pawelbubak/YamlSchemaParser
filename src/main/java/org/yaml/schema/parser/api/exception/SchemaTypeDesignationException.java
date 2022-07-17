package org.yaml.schema.parser.api.exception;

public class SchemaTypeDesignationException extends Exception {

    public SchemaTypeDesignationException() {
        super("Cannot designate schema property.");
    }

    public SchemaTypeDesignationException(String typeName) {
        super(String.format("Cannot designate schema property by name %s.", typeName));
    }

    public SchemaTypeDesignationException(Throwable cause) {
        super("Cannot designate schema property.", cause);
    }

}
