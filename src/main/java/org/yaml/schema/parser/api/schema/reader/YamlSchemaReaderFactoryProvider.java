package org.yaml.schema.parser.api.schema.reader;

import org.yaml.schema.parser.internal.utils.ProviderUtils;

public interface YamlSchemaReaderFactoryProvider {

    /**
     * Returns an instance of this interface.
     *
     * @return the instance of this interface.
     * @throws IllegalStateException if instance of {@code YamlSchemaReaderFactoryProvider} interface not found.
     */
    static YamlSchemaReaderFactoryProvider provider() {
        return ProviderUtils.provider(YamlSchemaReaderFactoryProvider.class);
    }

    YamlSchemaReaderFactory createFactory();

}
