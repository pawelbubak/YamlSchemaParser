package org.yaml.schema.parser.api.schema.type.mapper;

import org.yaml.schema.parser.internal.schema.type.mapper.exception.SchemaTypeMapperNotExistsException;

public interface SchemaTypeMappersHandler {

    void addMapper(String schemaTypeName, SchemaTypeMapper<Object> schemaTypeMapper);

    SchemaTypeMapper<Object> getMapper(String schemaTypeName) throws SchemaTypeMapperNotExistsException;

}
