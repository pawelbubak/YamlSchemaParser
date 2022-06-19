package org.yaml.schema.parser.internal.schema.property.factory;

import org.yaml.schema.parser.api.schema.property.factory.SchemaPropertyFactory;
import org.yaml.schema.parser.api.schema.property.factory.SchemaPropertyFactoryProvider;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMappersLoader;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMappersLoaderProvider;
import org.yaml.schema.parser.api.schema.type.designator.SchemaPropertyTypeDesignatorProvider;
import org.yaml.schema.parser.api.schema.type.designator.SchemaTypeDesignator;
import org.yaml.schema.parser.api.schema.type.mapper.SchemaTypeMappersLoader;
import org.yaml.schema.parser.api.schema.type.mapper.SchemaTypeMappersLoaderProvider;
import org.yaml.schema.parser.api.schema.version.SpecVersion;

public class DefaultSchemaPropertyFactoryProvider implements SchemaPropertyFactoryProvider {
    private final SchemaPropertyMappersLoader propertyMappersLoader;
    private final SchemaTypeMappersLoader typeMappersLoader;
    private final SchemaTypeDesignator propertyTypeDesignator;

    public DefaultSchemaPropertyFactoryProvider() {
        this(SchemaPropertyMappersLoaderProvider.provider().createLoader(),
                SchemaTypeMappersLoaderProvider.provider().createLoader(),
                SchemaPropertyTypeDesignatorProvider.provider().createDesignator());
    }

    public DefaultSchemaPropertyFactoryProvider(SchemaPropertyMappersLoader propertyMappersLoader,
            SchemaTypeMappersLoader typeMappersLoader, SchemaTypeDesignator propertyTypeDesignator) {
        this.propertyMappersLoader = propertyMappersLoader;
        this.typeMappersLoader = typeMappersLoader;
        this.propertyTypeDesignator = propertyTypeDesignator;
    }

    @Override
    public SchemaPropertyFactory getPropertyFactory(SpecVersion specVersion) {
        return new DefaultSchemaPropertyFactory(specVersion, propertyMappersLoader, typeMappersLoader,
                propertyTypeDesignator);
    }

}
