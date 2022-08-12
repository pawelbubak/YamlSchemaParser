package org.yaml.schema.parser.internal.schema.property;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.property.SchemaComplexProperty;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.SerializationContext;
import org.yaml.schema.parser.api.serializer.Serializer;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
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
    public void serialize(Serializer serializer, SerializationContext serializationContext) throws IOException {
        serializer.startComplexProperty(name());
        serializeProperties(serializer, serializationContext);
        serializer.endComplexElement();
    }

    protected void serializeProperties(Serializer serializer, SerializationContext serializationContext)
            throws IOException {
        List<SchemaProperty> sortedProperties = getPropertiesSortedBySequenceNumberAndName();
        for (SchemaProperty property : sortedProperties) {
            property.serialize(serializer, serializationContext);
        }
    }

    private List<SchemaProperty> getPropertiesSortedBySequenceNumberAndName() {
        return properties.values()
                .stream()
                .sorted(Comparator.comparingInt(SchemaProperty::sequenceNumber).thenComparing(SchemaProperty::name))
                .toList();
    }

}
