package org.yaml.schema.parser.internal.utils;

public class ArgumentsUtils {

    public static <T> T requireNonNull(T object, String name) {
        if (object == null) {
            throw new NullPointerException(String.format("Argument \"%s\" must not be null.", name));
        }
        return object;
    }

}
