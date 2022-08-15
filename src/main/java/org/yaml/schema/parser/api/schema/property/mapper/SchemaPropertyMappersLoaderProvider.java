package org.yaml.schema.parser.api.schema.property.mapper;

import org.yaml.schema.parser.internal.utils.ProviderUtils;

public interface SchemaPropertyMappersLoaderProvider {

    /**
     * Returns an instance of this interface.
     *
     * @return the instance of this interface.
     * @throws IllegalStateException if instance of {@code SchemaPropertyMappersLoaderProvider} interface not found.
     */
    static SchemaPropertyMappersLoaderProvider provider() {
        return ProviderUtils.provider(SchemaPropertyMappersLoaderProvider.class);
    }

    SchemaPropertyMappersLoader createLoader();

}
