package org.yaml.schema.parser.api.schema.reader;

import org.yaml.schema.parser.internal.schema.reader.DefaultYamlSchemaReaderFactoryProvider;

import java.util.*;

public interface YamlSchemaReaderFactoryProvider {

    /**
     * Returns an instance of this interface.
     *
     * @return the instance of this interface.
     * @throws IllegalStateException if more than one provider is found.
     */
    static YamlSchemaReaderFactoryProvider provider() {
        YamlSchemaReaderFactoryProvider provider;

        List<YamlSchemaReaderFactoryProvider> providers = loadNotDefaultProviders();
        if (providers.size() == 0) {
            provider = new DefaultYamlSchemaReaderFactoryProvider();
        } else if (providers.size() == 1) {
            provider = providers.get(0);
        } else {
            throw new IllegalStateException(
                    "More than one implementation of the YamlSchemaReaderFactoryProvider interface found.");
        }
        return provider;
    }

    private static List<YamlSchemaReaderFactoryProvider> loadNotDefaultProviders() {
        ServiceLoader<YamlSchemaReaderFactoryProvider> loader = ServiceLoader.load(
                YamlSchemaReaderFactoryProvider.class);

        Set<YamlSchemaReaderFactoryProvider> providers = new HashSet<>();
        for (YamlSchemaReaderFactoryProvider provider : loader) {
            if (!(provider instanceof DefaultYamlSchemaReaderFactoryProvider)) {
                providers.add(provider);
            }
        }

        return new ArrayList<>(providers);
    }

    YamlSchemaReaderFactory createFactory();

}
