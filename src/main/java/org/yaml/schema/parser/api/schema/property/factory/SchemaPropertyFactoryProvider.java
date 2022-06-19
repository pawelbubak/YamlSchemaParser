package org.yaml.schema.parser.api.schema.property.factory;

import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.internal.schema.property.factory.DefaultSchemaPropertyFactoryProvider;

import java.util.*;

public interface SchemaPropertyFactoryProvider {

    /**
     * Returns an instance of this interface.
     *
     * @return the instance of this interface.
     * @throws IllegalStateException if more than one provider is found.
     */
    static SchemaPropertyFactoryProvider provider() {
        SchemaPropertyFactoryProvider provider;

        List<SchemaPropertyFactoryProvider> providers = loadNotDefaultProviders();
        if (providers.size() == 0) {
            provider = new DefaultSchemaPropertyFactoryProvider();
        } else if (providers.size() == 1) {
            provider = providers.get(0);
        } else {
            throw new IllegalStateException("More than one implementation of the YamlReaderProvider interface found.");
        }

        return provider;
    }

    private static List<SchemaPropertyFactoryProvider> loadNotDefaultProviders() {
        ServiceLoader<SchemaPropertyFactoryProvider> loader = ServiceLoader.load(SchemaPropertyFactoryProvider.class);

        Set<SchemaPropertyFactoryProvider> providers = new HashSet<>();
        for (SchemaPropertyFactoryProvider provider : loader) {
            if (!(provider instanceof DefaultSchemaPropertyFactoryProvider)) {
                providers.add(provider);
            }
        }

        return new ArrayList<>(providers);
    }

    SchemaPropertyFactory getPropertyFactory(SpecVersion specVersion);

}
