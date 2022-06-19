package org.yaml.schema.parser.internal.schema.property.mapper;

import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMappersHandler;
import org.yaml.schema.parser.internal.schema.property.mapper.exception.SchemaPropertyMapperNotExistsException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DefaultSchemaPropertyMappersHandler implements SchemaPropertyMappersHandler {
    private final Map<SchemaPropertyContext.Type, Map<String, SchemaPropertyMapper<Object>>> mappers;

    public DefaultSchemaPropertyMappersHandler() {
        this.mappers = new HashMap<>();
    }

    @Override
    public void addMapper(SchemaPropertyContext.Type schemaPropertyContext, String schemaPropertyName,
            SchemaPropertyMapper<Object> schemaPropertyMapper) {
        mappers.computeIfAbsent(schemaPropertyContext, (key) -> new HashMap<>())
                .put(schemaPropertyName, schemaPropertyMapper);
    }

    @Override
    public SchemaPropertyMapper<Object> getMapper(SchemaPropertyContext.Type schemaPropertyContext,
            String schemaPropertyName) throws SchemaPropertyMapperNotExistsException {
        Map<String, SchemaPropertyMapper<Object>> contextMappers = Optional.ofNullable(mappers.get(schemaPropertyContext))
                .orElseThrow(() -> new SchemaPropertyMapperNotExistsException(schemaPropertyContext));
        return Optional.ofNullable(contextMappers.get(schemaPropertyName))
                .orElseThrow(() -> new SchemaPropertyMapperNotExistsException(schemaPropertyContext, schemaPropertyName));
    }

}
