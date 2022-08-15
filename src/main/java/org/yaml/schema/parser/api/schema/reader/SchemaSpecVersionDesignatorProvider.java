package org.yaml.schema.parser.api.schema.reader;

import org.yaml.schema.parser.internal.utils.ProviderUtils;

public interface SchemaSpecVersionDesignatorProvider {

    /**
     * Returns an instance of this interface.
     *
     * @return the instance of this interface.
     * @throws IllegalStateException if instance of {@code SchemaSpecVersionDesignatorProvider} interface not found.
     */
    static SchemaSpecVersionDesignatorProvider provider() {
        return ProviderUtils.provider(SchemaSpecVersionDesignatorProvider.class);
    }

    SchemaSpecVersionDesignator createDesignator();

}
