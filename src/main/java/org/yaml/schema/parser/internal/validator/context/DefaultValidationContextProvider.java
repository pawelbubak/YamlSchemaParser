package org.yaml.schema.parser.internal.validator.context;

import org.yaml.schema.parser.api.validator.context.ValidationContext;
import org.yaml.schema.parser.api.validator.context.ValidationContextProvider;

public class DefaultValidationContextProvider implements ValidationContextProvider {

    @Override
    public ValidationContext createContext() {
        return new DefaultValidationContext();
    }

}
