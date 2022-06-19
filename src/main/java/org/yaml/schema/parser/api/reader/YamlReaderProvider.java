package org.yaml.schema.parser.api.reader;

import org.yaml.schema.parser.internal.reader.DefaultYamlReaderProvider;

import java.util.*;

public interface YamlReaderProvider {

    /**
     * Returns an instance of this interface.
     *
     * @return the instance of this interface.
     * @throws IllegalStateException if more than one provider is found.
     */
    static YamlReaderProvider provider() {
        YamlReaderProvider provider;

        List<YamlReaderProvider> providers = loadNotDefaultProviders();
        if (providers.size() == 0) {
            provider = new DefaultYamlReaderProvider();
        } else if (providers.size() == 1) {
            provider = providers.get(0);
        } else {
            throw new IllegalStateException("More than one implementation of the YamlReaderProvider interface found.");
        }
        return provider;
    }

    private static List<YamlReaderProvider> loadNotDefaultProviders() {
        ServiceLoader<YamlReaderProvider> loader = ServiceLoader.load(YamlReaderProvider.class);

        Set<YamlReaderProvider> providers = new HashSet<>();
        for (YamlReaderProvider provider : loader) {
            if (!(provider instanceof DefaultYamlReaderProvider)) {
                providers.add(provider);
            }
        }

        return new ArrayList<>(providers);
    }

    YamlReader createReader();

}
