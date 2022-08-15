package org.yaml.schema.parser.api.schema.type.mapper;

import org.yaml.schema.parser.internal.utils.ProviderUtils;

public interface SchemaTypeMappersLoaderProvider {

    /**
     * Returns an instance of this interface.
     *
     * @return the instance of this interface.
     * @throws IllegalStateException if instance of {@code SchemaTypeMappersLoaderProvider} interface not found.
     */
    static SchemaTypeMappersLoaderProvider provider() {
        return ProviderUtils.provider(SchemaTypeMappersLoaderProvider.class);
    }

    SchemaTypeMappersLoader createLoader();

}
