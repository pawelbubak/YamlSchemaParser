package org.yaml.schema.parser.api.exception;

import org.yaml.schema.parser.api.schema.version.SpecVersion;

public class SchemaPropertyNotExistsInSpecificationException extends Exception {

    public SchemaPropertyNotExistsInSpecificationException(String propertyName, SpecVersion specVersion) {
        super(String.format("The %s property does not exist in the %s specification.", propertyName, specVersion.id()));
    }

}
