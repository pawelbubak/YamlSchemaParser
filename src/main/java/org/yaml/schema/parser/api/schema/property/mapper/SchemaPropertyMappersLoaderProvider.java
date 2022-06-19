package org.yaml.schema.parser.api.schema.property.mapper;

import org.yaml.schema.parser.internal.schema.property.mapper.DefaultSchemaPropertyMappersLoaderProvider;

import java.util.*;

public interface SchemaPropertyMappersLoaderProvider {

    /**
     * Returns an instance of this interface.
     *
     * @return the instance of this interface.
     * @throws IllegalStateException if more than one provider is found.
     */
    static SchemaPropertyMappersLoaderProvider provider() {
        SchemaPropertyMappersLoaderProvider provider;

        List<SchemaPropertyMappersLoaderProvider> providers = loadNotDefaultProviders();
        if (providers.size() == 0) {
            provider = new DefaultSchemaPropertyMappersLoaderProvider();
        } else if (providers.size() == 1) {
            provider = providers.get(0);
        } else {
            throw new IllegalStateException(
                    "More than one implementation of the SchemaPropertyMappersLoaderProvider interface found.");
        }
        return provider;
    }

    private static List<SchemaPropertyMappersLoaderProvider> loadNotDefaultProviders() {
        ServiceLoader<SchemaPropertyMappersLoaderProvider> loader = ServiceLoader.load(
                SchemaPropertyMappersLoaderProvider.class);

        Set<SchemaPropertyMappersLoaderProvider> providers = new HashSet<>();
        for (SchemaPropertyMappersLoaderProvider provider : loader) {
            if (!(provider instanceof DefaultSchemaPropertyMappersLoaderProvider)) {
                providers.add(provider);
            }
        }

        return new ArrayList<>(providers);
    }

    SchemaPropertyMappersLoader createLoader();

}
