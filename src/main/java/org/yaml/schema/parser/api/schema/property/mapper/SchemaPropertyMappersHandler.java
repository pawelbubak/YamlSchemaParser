package org.yaml.schema.parser.api.schema.property.mapper;

import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.internal.schema.property.mapper.exception.SchemaPropertyMapperNotExistsException;

public interface SchemaPropertyMappersHandler {

    void addMapper(SchemaPropertyContext.Type schemaPropertyContext, String schemaPropertyName,
            SchemaPropertyMapper<Object> schemaPropertyMapper);

    SchemaPropertyMapper<Object> getMapper(SchemaPropertyContext.Type schemaPropertyContext, String schemaPropertyName)
            throws SchemaPropertyMapperNotExistsException;

}
