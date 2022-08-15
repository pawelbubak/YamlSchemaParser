package org.yaml.schema.parser.api.schema.property.factory;

import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.internal.utils.ProviderUtils;

public interface SchemaPropertyFactoryProvider {

    /**
     * Returns an instance of this interface.
     *
     * @return the instance of this interface.
     * @throws IllegalStateException if instance of {@code SchemaPropertyFactoryProvider} interface not found.
     */
    static SchemaPropertyFactoryProvider provider() {
        return ProviderUtils.provider(SchemaPropertyFactoryProvider.class);
    }

    SchemaPropertyFactory getPropertyFactory(SpecVersion specVersion);

}
