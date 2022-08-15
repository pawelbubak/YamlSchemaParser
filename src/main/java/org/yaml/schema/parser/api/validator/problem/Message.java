package org.yaml.schema.parser.api.validator.problem;

import java.util.Locale;

public interface Message {

    default String getMessage() {
        return getMessage(Locale.getDefault());
    }

    String getMessage(Locale locale);

}
