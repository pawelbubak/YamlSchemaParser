package org.yaml.schema.parser.internal.schema;

import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.schema.Schema;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.internal.schema.property.annotation.Description;
import org.yaml.schema.parser.internal.schema.property.annotation.Title;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.yaml.schema.parser.internal.schema.validation.SchemaPropertyValidationUtils.requireNonNull;

public abstract class AbstractSchema extends AbstractMap<String, SchemaProperty> implements Schema {
    /**
     * The version of schema specification.
     */
    private final SpecVersion specVersion;
    /**
     * The properties of this schema.
     */
    private final Map<String, SchemaProperty> schemaProperties;

    protected AbstractSchema(SpecVersion specVersion, Map<String, SchemaProperty> schemaProperties) {
        this.specVersion = specVersion;
        this.schemaProperties = schemaProperties;
    }

    @Override
    public String title() {
        String titlePropertyName = getPropertyName(Title.class);
        if (!containsProperty(titlePropertyName)) {
            return null;
        }
        Title title = (Title) getProperty(titlePropertyName);
        return title.value();
    }

    @Override
    public String description() {
        String descriptionPropertyName = getPropertyName(Description.class);
        if (!containsProperty(descriptionPropertyName)) {
            return null;
        }
        Description description = (Description) getProperty(descriptionPropertyName);
        return description.value();
    }

    @Override
    public Stream<Schema> getSubschemas() {
        return schemaProperties.values().stream()
                .filter(SchemaProperty::hasSubschemas)
                .flatMap(SchemaProperty::getSubschemas);
    }

    @Override
    public Set<Entry<String, SchemaProperty>> entrySet() {
        return schemaProperties.entrySet();
    }

    protected String getPropertyName(Class<?> clazz) {
        for (SchemaVersion schemaVersion : clazz.getAnnotationsByType(SchemaVersion.class)) {
            if (specVersion.equals(schemaVersion.value()) && !schemaVersion.name().isBlank()) {
                return schemaVersion.name();
            }
        }
        return clazz.getAnnotation(SchemaPropertyName.class).value();
    }

    protected boolean containsProperty(String propertyName) {
        requireNonNull(propertyName, "Property name");
        return schemaProperties.containsKey(propertyName);
    }

    protected SchemaProperty getProperty(String name) {
        return schemaProperties.get(name);
    }

}
