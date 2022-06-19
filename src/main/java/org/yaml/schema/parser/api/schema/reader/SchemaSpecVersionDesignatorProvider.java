package org.yaml.schema.parser.api.schema.reader;

import org.yaml.schema.parser.internal.schema.reader.DefaultSchemaSpecVersionDesignatorProvider;

import java.util.*;

public interface SchemaSpecVersionDesignatorProvider {

    /**
     * Returns an instance of this interface.
     *
     * @return the instance of this interface.
     * @throws IllegalStateException if more than one provider is found.
     */
    static SchemaSpecVersionDesignatorProvider provider() {
        SchemaSpecVersionDesignatorProvider provider;

        List<SchemaSpecVersionDesignatorProvider> providers = loadNotDefaultProviders();
        if (providers.size() == 0) {
            provider = new DefaultSchemaSpecVersionDesignatorProvider();
        } else if (providers.size() == 1) {
            provider = providers.get(0);
        } else {
            throw new IllegalStateException(
                    "More than one implementation of the SchemaSpecVersionDesignatorProvider interface found.");
        }
        return provider;
    }

    private static List<SchemaSpecVersionDesignatorProvider> loadNotDefaultProviders() {
        ServiceLoader<SchemaSpecVersionDesignatorProvider> loader = ServiceLoader.load(
                SchemaSpecVersionDesignatorProvider.class);

        Set<SchemaSpecVersionDesignatorProvider> providers = new HashSet<>();
        for (SchemaSpecVersionDesignatorProvider provider : loader) {
            if (!(provider instanceof DefaultSchemaSpecVersionDesignatorProvider)) {
                providers.add(provider);
            }
        }

        return new ArrayList<>(providers);
    }

    SchemaSpecVersionDesignator createDesignator();

}
