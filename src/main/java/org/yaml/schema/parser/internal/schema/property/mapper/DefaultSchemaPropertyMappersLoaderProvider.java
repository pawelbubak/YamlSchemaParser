package org.yaml.schema.parser.internal.schema.property.mapper;

import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMappersLoader;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMappersLoaderProvider;

public class DefaultSchemaPropertyMappersLoaderProvider implements SchemaPropertyMappersLoaderProvider {

    @Override
    public SchemaPropertyMappersLoader createLoader() {
        return new DefaultSchemaPropertyMappersLoader();
    }

}
