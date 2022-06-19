package org.yaml.schema.parser.internal.schema.validation;

public class SchemaPropertyValidationUtils {

    public static <T> T requireNonNull(T object, String name) {
        if (object == null) {
            throw new NullPointerException(name + " must not be null.");
        }
        return object;
    }

}
