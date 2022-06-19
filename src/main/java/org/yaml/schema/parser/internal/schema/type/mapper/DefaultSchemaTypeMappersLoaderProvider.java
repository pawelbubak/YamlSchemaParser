package org.yaml.schema.parser.internal.schema.type.mapper;

import org.yaml.schema.parser.api.schema.type.mapper.SchemaTypeMappersLoader;
import org.yaml.schema.parser.api.schema.type.mapper.SchemaTypeMappersLoaderProvider;

public class DefaultSchemaTypeMappersLoaderProvider implements SchemaTypeMappersLoaderProvider {

    @Override
    public SchemaTypeMappersLoader createLoader() {
        return new DefaultSchemaTypeMappersLoader();
    }

}
