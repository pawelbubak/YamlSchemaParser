package org.yaml.schema.parser.api.validator.context;

import org.yaml.schema.parser.internal.utils.ProviderUtils;

public interface ValidationContextProvider {

    /**
     * Returns an instance of this interface.
     *
     * @return the instance of this interface.
     * @throws IllegalStateException if instance of {@code ValidationContextProvider} interface not found.
     */
    static ValidationContextProvider provider() {
        return ProviderUtils.provider(ValidationContextProvider.class);
    }

    ValidationContext createContext();

}
