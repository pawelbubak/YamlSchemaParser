package org.yaml.schema.parser.internal.schema.property.factory;

import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.factory.SchemaPropertyFactory;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMappersHandler;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMappersLoader;
import org.yaml.schema.parser.api.schema.type.designator.SchemaTypeDesignator;
import org.yaml.schema.parser.api.schema.type.mapper.SchemaTypeMapper;
import org.yaml.schema.parser.api.schema.type.mapper.SchemaTypeMappersHandler;
import org.yaml.schema.parser.api.schema.type.mapper.SchemaTypeMappersLoader;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.internal.schema.property.mapper.exception.SchemaPropertyMapperNotExistsException;

public class DefaultSchemaPropertyFactory implements SchemaPropertyFactory {
    private final SpecVersion specVersion;
    private final SchemaPropertyMappersHandler propertyMappersHandler;
    private final SchemaTypeMappersHandler typeMappersHandler;
    private final SchemaTypeDesignator propertyTypeDesignator;

    public DefaultSchemaPropertyFactory(SpecVersion specVersion, SchemaPropertyMappersLoader propertyMappersLoader,
            SchemaTypeMappersLoader typeMappersLoader, SchemaTypeDesignator propertyTypeDesignator) {
        this.specVersion = specVersion;
        this.propertyMappersHandler = propertyMappersLoader.loadMappers(specVersion);
        this.typeMappersHandler = typeMappersLoader.loadMappers(specVersion);
        this.propertyTypeDesignator = propertyTypeDesignator;
    }

    @Override
    public SchemaProperty createProperty(SchemaPropertyContext.Type propertyContext, String propertyName, Object value)
            throws Exception {
        SchemaPropertyMapper<Object> mapper;
        try {
            mapper = propertyMappersHandler.getMapper(propertyContext, propertyName);
        } catch (SchemaPropertyMapperNotExistsException e) {
            mapper = propertyMappersHandler.getMapper(SchemaPropertyContext.Type.DEFAULT, propertyName);
        }
        return mapper.map(specVersion, value, this);
    }

    @Override
    public SchemaProperty createType(String typeName, Object value) throws Exception {
        String type = propertyTypeDesignator.getType(value, specVersion);
        SchemaTypeMapper<Object> mapper = typeMappersHandler.getMapper(type);
        return mapper.map(specVersion, typeName, value, this);
    }

}
