package org.yaml.schema.parser.internal.schema.type.mapper;

import org.reflections.Reflections;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.type.annotation.SchemaTypeName;
import org.yaml.schema.parser.api.schema.type.mapper.SchemaTypeMapper;
import org.yaml.schema.parser.api.schema.type.mapper.SchemaTypeMappersHandler;
import org.yaml.schema.parser.api.schema.type.mapper.SchemaTypeMappersLoader;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.internal.schema.property.factory.exception.SchemaPropertyMapperCreationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DefaultSchemaTypeMappersLoader implements SchemaTypeMappersLoader {
    private static final String SCHEMA_PROPERTY_CLASSES_PACKAGE = "org.yaml.schema.parser.internal.schema.type";

    @Override
    @SuppressWarnings("unchecked")
    public SchemaTypeMappersHandler loadMappers(SpecVersion specVersion) {
        SchemaTypeMappersHandler mappersHandler = new DefaultSchemaTypeMappersHandler();

        Reflections reflections = new Reflections(SCHEMA_PROPERTY_CLASSES_PACKAGE);
        for (Class<?> clazz : reflections.getTypesAnnotatedWith(SchemaTypeName.class)) {
            if (SchemaProperty.class.isAssignableFrom(clazz)) {
                Class<SchemaProperty> schemaPropertyClass = (Class<SchemaProperty>) clazz;
                loadMapperIfItIsInSpecification(specVersion, mappersHandler, schemaPropertyClass);
            }
        }

        return mappersHandler;
    }

    private void loadMapperIfItIsInSpecification(SpecVersion specVersion, SchemaTypeMappersHandler mappersHandler,
            Class<SchemaProperty> schemaPropertyClass) {
        if (isPropertyInSpecification(schemaPropertyClass, specVersion)) {
            loadMapper(specVersion, mappersHandler, schemaPropertyClass);
        }
    }

    private boolean isPropertyInSpecification(Class<SchemaProperty> schemaPropertyClass, SpecVersion specVersion) {
        return getSchemaVersionAnnotationBySpecVersion(schemaPropertyClass, specVersion) != null;
    }

    private void loadMapper(SpecVersion specVersion, SchemaTypeMappersHandler mappersHandler,
            Class<SchemaProperty> schemaPropertyClass) {
        String propertyName = getSchemaPropertyName(schemaPropertyClass, specVersion);
        SchemaTypeMapper<Object> propertyMapper = getSchemaPropertyMapper(specVersion, schemaPropertyClass);

        mappersHandler.addMapper(propertyName, propertyMapper);
    }

    @SuppressWarnings("unchecked")
    private SchemaTypeMapper<Object> getSchemaPropertyMapper(SpecVersion specVersion,
            Class<SchemaProperty> schemaPropertyClass) {
        try {
            Method method = schemaPropertyClass.getMethod("mapper");
            return (SchemaTypeMapper<Object>) method.invoke(null);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException |
                 SecurityException e) {
            String schemaPropertyName = getSchemaPropertyName(schemaPropertyClass, specVersion);
            throw new SchemaPropertyMapperCreationException(schemaPropertyName, e);
        }
    }

    private String getSchemaPropertyName(Class<SchemaProperty> schemaPropertyClass, SpecVersion specVersion) {
        SchemaVersion schemaVersion = getSchemaVersionAnnotationBySpecVersion(schemaPropertyClass, specVersion);
        return schemaVersion != null && schemaVersion.name() != null && !schemaVersion.name().isBlank()
                ? schemaVersion.name()
                : getDefaultPropertyName(schemaPropertyClass);
    }

    private SchemaVersion getSchemaVersionAnnotationBySpecVersion(Class<SchemaProperty> schemaPropertyClass,
            SpecVersion specVersion) {
        for (SchemaVersion schemaVersion : schemaPropertyClass.getAnnotationsByType(SchemaVersion.class)) {
            if (schemaVersion.value().equals(specVersion)) {
                return schemaVersion;
            }
        }
        return null;
    }

    private String getDefaultPropertyName(Class<SchemaProperty> schemaPropertyClass) {
        SchemaTypeName schemaTypeName = schemaPropertyClass.getAnnotation(SchemaTypeName.class);
        return schemaTypeName != null ? schemaTypeName.value() : null;
    }

}
