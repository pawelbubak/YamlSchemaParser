package org.yaml.schema.parser.api.schema.type.mapper;

import org.yaml.schema.parser.internal.schema.type.mapper.DefaultSchemaTypeMappersLoaderProvider;

import java.util.*;

public interface SchemaTypeMappersLoaderProvider {

    /**
     * Returns an instance of this interface.
     *
     * @return the instance of this interface.
     * @throws IllegalStateException if more than one provider is found.
     */
    static SchemaTypeMappersLoaderProvider provider() {
        SchemaTypeMappersLoaderProvider provider;

        List<SchemaTypeMappersLoaderProvider> providers = loadNotDefaultProviders();
        if (providers.size() == 0) {
            provider = new DefaultSchemaTypeMappersLoaderProvider();
        } else if (providers.size() == 1) {
            provider = providers.get(0);
        } else {
            throw new IllegalStateException(
                    "More than one implementation of the SchemaTypeMappersLoaderProvider interface found.");
        }
        return provider;
    }

    private static List<SchemaTypeMappersLoaderProvider> loadNotDefaultProviders() {
        ServiceLoader<SchemaTypeMappersLoaderProvider> loader = ServiceLoader.load(
                SchemaTypeMappersLoaderProvider.class);

        Set<SchemaTypeMappersLoaderProvider> providers = new HashSet<>();
        for (SchemaTypeMappersLoaderProvider provider : loader) {
            if (!(provider instanceof DefaultSchemaTypeMappersLoaderProvider)) {
                providers.add(provider);
            }
        }

        return new ArrayList<>(providers);
    }

    SchemaTypeMappersLoader createLoader();

}
