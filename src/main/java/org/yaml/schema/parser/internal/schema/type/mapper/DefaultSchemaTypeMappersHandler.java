package org.yaml.schema.parser.internal.schema.type.mapper;

import org.yaml.schema.parser.api.schema.type.mapper.SchemaTypeMapper;
import org.yaml.schema.parser.api.schema.type.mapper.SchemaTypeMappersHandler;
import org.yaml.schema.parser.internal.schema.type.mapper.exception.SchemaTypeMapperNotExistsException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DefaultSchemaTypeMappersHandler implements SchemaTypeMappersHandler {
    private final Map<String, SchemaTypeMapper<Object>> mappers;

    public DefaultSchemaTypeMappersHandler() {
        this.mappers = new HashMap<>();
    }

    @Override
    public void addMapper(String schemaTypeName, SchemaTypeMapper<Object> schemaTypeMapper) {
        mappers.put(schemaTypeName, schemaTypeMapper);
    }

    @Override
    public SchemaTypeMapper<Object> getMapper(String schemaTypeName) throws SchemaTypeMapperNotExistsException {
        return Optional.ofNullable(mappers.get(schemaTypeName))
                .orElseThrow(() -> new SchemaTypeMapperNotExistsException(schemaTypeName));
    }

}
