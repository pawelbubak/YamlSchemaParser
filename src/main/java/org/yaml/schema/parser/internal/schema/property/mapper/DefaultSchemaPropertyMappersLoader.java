package org.yaml.schema.parser.internal.schema.property.mapper;

import org.reflections.Reflections;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMappersLoader;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMappersHandler;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.internal.schema.property.factory.exception.SchemaPropertyContextNotSetException;
import org.yaml.schema.parser.internal.schema.property.factory.exception.SchemaPropertyMapperCreationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DefaultSchemaPropertyMappersLoader implements SchemaPropertyMappersLoader {
    private static final String SCHEMA_PROPERTY_CLASSES_PACKAGE = "org.yaml.schema.parser.internal.schema.property";

    @Override
    @SuppressWarnings("unchecked")
    public SchemaPropertyMappersHandler loadMappers(SpecVersion specVersion) {
        SchemaPropertyMappersHandler mappersHandler = new DefaultSchemaPropertyMappersHandler();

        Reflections reflections = new Reflections(SCHEMA_PROPERTY_CLASSES_PACKAGE);
        for (Class<?> clazz : reflections.getTypesAnnotatedWith(SchemaPropertyName.class)) {
            if (SchemaProperty.class.isAssignableFrom(clazz)) {
                Class<SchemaProperty> schemaPropertyClass = (Class<SchemaProperty>) clazz;
                loadMapperIfItIsInSpecification(specVersion, mappersHandler, schemaPropertyClass);
            }
        }

        return mappersHandler;
    }

    private void loadMapperIfItIsInSpecification(SpecVersion specVersion, SchemaPropertyMappersHandler mappersHandler,
            Class<SchemaProperty> schemaPropertyClass) {
        if (isPropertyInSpecification(schemaPropertyClass, specVersion)) {
            SchemaPropertyContext schemaPropertyContext = schemaPropertyClass.getAnnotation(SchemaPropertyContext.class);

            if (schemaPropertyContext == null) {
                String propertyName = getSchemaPropertyName(schemaPropertyClass, specVersion);
                throw new SchemaPropertyContextNotSetException(propertyName);
            }

            loadMapper(specVersion, mappersHandler, schemaPropertyClass, schemaPropertyContext.value());
        }
    }

    private boolean isPropertyInSpecification(Class<SchemaProperty> schemaPropertyClass, SpecVersion specVersion) {
        return getSchemaVersionAnnotationBySpecVersion(schemaPropertyClass, specVersion) != null;
    }

    private void loadMapper(SpecVersion specVersion, SchemaPropertyMappersHandler mappersHandler,
            Class<SchemaProperty> schemaPropertyClass, SchemaPropertyContext.Type propertyContext) {
        String propertyName = getSchemaPropertyName(schemaPropertyClass, specVersion);
        SchemaPropertyMapper<Object> propertyMapper = getSchemaPropertyMapper(specVersion, schemaPropertyClass);

        mappersHandler.addMapper(propertyContext, propertyName, propertyMapper);
    }

    @SuppressWarnings("unchecked")
    private SchemaPropertyMapper<Object> getSchemaPropertyMapper(SpecVersion specVersion,
            Class<SchemaProperty> schemaPropertyClass) {
        try {
            Method method = schemaPropertyClass.getMethod("mapper");
            return (SchemaPropertyMapper<Object>) method.invoke(null);
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
        SchemaPropertyName schemaPropertyName = schemaPropertyClass.getAnnotation(SchemaPropertyName.class);
        return schemaPropertyName != null ? schemaPropertyName.value() : null;
    }

}
