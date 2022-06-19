package org.yaml.schema.parser.api.schema.type.designator;

import org.yaml.schema.parser.internal.schema.type.designator.DefaultSchemaPropertyTypeDesignatorProvider;

import java.util.*;

public interface SchemaPropertyTypeDesignatorProvider {

    /**
     * Returns an instance of this interface.
     *
     * @return the instance of this interface.
     * @throws IllegalStateException if more than one provider is found.
     */
    static SchemaPropertyTypeDesignatorProvider provider() {
        SchemaPropertyTypeDesignatorProvider provider;

        List<SchemaPropertyTypeDesignatorProvider> providers = loadNotDefaultProviders();
        if (providers.size() == 0) {
            provider = new DefaultSchemaPropertyTypeDesignatorProvider();
        } else if (providers.size() == 1) {
            provider = providers.get(0);
        } else {
            throw new IllegalStateException(
                    "More than one implementation of the SchemaPropertyMappersLoaderProvider interface found.");
        }
        return provider;
    }

    private static List<SchemaPropertyTypeDesignatorProvider> loadNotDefaultProviders() {
        ServiceLoader<SchemaPropertyTypeDesignatorProvider> loader = ServiceLoader.load(
                SchemaPropertyTypeDesignatorProvider.class);

        Set<SchemaPropertyTypeDesignatorProvider> providers = new HashSet<>();
        for (SchemaPropertyTypeDesignatorProvider provider : loader) {
            if (!(provider instanceof DefaultSchemaPropertyTypeDesignatorProvider)) {
                providers.add(provider);
            }
        }

        return new ArrayList<>(providers);
    }

    SchemaTypeDesignator createDesignator();

}
