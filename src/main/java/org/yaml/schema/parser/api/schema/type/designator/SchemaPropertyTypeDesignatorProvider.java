package org.yaml.schema.parser.api.schema.type.designator;

import org.yaml.schema.parser.internal.utils.ProviderUtils;

public interface SchemaPropertyTypeDesignatorProvider {

    /**
     * Returns an instance of this interface.
     *
     * @return the instance of this interface.
     * @throws IllegalStateException if instance of {@code SchemaPropertyTypeDesignatorProvider} interface not found.
     */
    static SchemaPropertyTypeDesignatorProvider provider() {
        return ProviderUtils.provider(SchemaPropertyTypeDesignatorProvider.class);
    }

    SchemaTypeDesignator createDesignator();

}
