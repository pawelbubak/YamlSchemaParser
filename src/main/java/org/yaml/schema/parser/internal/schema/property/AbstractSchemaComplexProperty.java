package org.yaml.schema.parser.internal.schema.property;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.property.SchemaComplexProperty;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.version.SpecVersion;

import java.util.Collection;
import java.util.Map;

import static org.yaml.schema.parser.internal.schema.validation.SchemaPropertyValidationUtils.requireNonNull;

public abstract class AbstractSchemaComplexProperty extends AbstractSchemaProperty implements SchemaComplexProperty {
    /**
     * The properties of this property.
     */
    private final Map<String, SchemaProperty> properties;

    public AbstractSchemaComplexProperty(SpecVersion specVersion, Map<String, SchemaProperty> properties)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion);
        this.properties = properties;
    }

    public AbstractSchemaComplexProperty(SpecVersion specVersion, String name, Map<String, SchemaProperty> properties) {
        super(specVersion, name);
        this.properties = properties;
    }

    public boolean containsProperty(String propertyName) {
        requireNonNull(propertyName, "Property name");
        return properties.containsKey(propertyName);
    }

    @Override
    public SchemaProperty getProperty(String propertyName) {
        requireNonNull(propertyName, "Property name");
        return properties.get(propertyName);
    }

    public Collection<SchemaProperty> getProperties() {
        return properties.values();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        for (SchemaProperty schemaProperty : getProperties()) {
            stringBuilder.append(String.format("\t%s: %s", schemaProperty.name(), schemaProperty));
        }
        return stringBuilder.toString();
    }

}
