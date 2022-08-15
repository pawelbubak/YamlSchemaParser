package org.yaml.schema.parser.api.validator.problem;

import org.yaml.schema.parser.internal.utils.ProviderUtils;

public interface LineFormatterProvider {

    /**
     * Returns an instance of this interface.
     *
     * @return the instance of this interface.
     * @throws IllegalStateException if instance of {@code LineFormatterProvider} interface not found.
     */
    static LineFormatterProvider provider() {
        return ProviderUtils.provider(LineFormatterProvider.class);
    }

    LineFormatter createFormatter();

}
