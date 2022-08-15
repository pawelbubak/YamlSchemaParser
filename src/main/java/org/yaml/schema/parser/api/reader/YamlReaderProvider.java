package org.yaml.schema.parser.api.reader;

import org.yaml.schema.parser.internal.utils.ProviderUtils;

public interface YamlReaderProvider {

    /**
     * Returns an instance of this interface.
     *
     * @return the instance of this interface.
     * @throws IllegalStateException if instance of {@code YamlReaderProvider} interface not found.
     */
    static YamlReaderProvider provider() {
        return ProviderUtils.provider(YamlReaderProvider.class);
    }

    YamlReader createReader();

}
