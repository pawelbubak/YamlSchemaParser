package org.yaml.schema.parser.internal.schema.property;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.property.SchemaComplexProperty;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.property.SchemaSimpleProperty;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.api.validator.YamlValidator;
import org.yaml.schema.parser.internal.schema.property.core.Sequence;
import org.yaml.schema.parser.internal.utils.SchemaPropertyNameDesignator;

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
    public int sequenceNumber() {
        try {
            String propertyName = SchemaPropertyNameDesignator.designatePropertyName(Sequence.class,
                    getUsedSpecVersion());
            SchemaProperty schemaProperty = properties.get(propertyName);
            if (schemaProperty instanceof SchemaSimpleProperty<?> sequenceProperty) {
                Object sequenceValue = sequenceProperty.value();
                if (sequenceValue instanceof Number) {
                    return ((Number) sequenceValue).intValue();
                }
            }
            return super.sequenceNumber();
        } catch (SchemaPropertyNotExistsInSpecificationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        serializer.startComplexElement(name());
        serializeProperties(serializer);
        serializer.endComplexElement();
    }

    @Override
    public void test(YamlValidator validator, Object value) {
        if (value instanceof Map<?, ?>) {
            for (SchemaProperty property : getPropertiesSortedBySequenceNumberAndName()) {
                validator.startElement(property.name());
                Object propertyValue = ((Map<?, ?>) value).get(property.name());
                property.test(validator, propertyValue);
                validator.endElement();
            }
        }
    }

    @Override
    public void format(Serializer serializer, Object rawValue) throws IOException {
        if (rawValue instanceof Map<?, ?> values) {
            serializer.startComplexElement(name());
            formatMapProperties(serializer, values);
            serializer.endComplexElement();
        }
    }

    private void formatMapProperties(Serializer serializer, Map<?, ?> values) throws IOException {
        for (Map.Entry<?, ?> valueEntry : values.entrySet()) {
            SchemaProperty schemaProperty = getProperty(String.valueOf(valueEntry.getKey()));
            if (schemaProperty != null) {
                schemaProperty.format(serializer, valueEntry.getValue());
            }
        }
    }

    protected void serializeProperties(Serializer serializer) throws IOException {
        List<SchemaProperty> sortedProperties = getPropertiesSortedBySequenceNumberAndName();
        for (SchemaProperty property : sortedProperties) {
            property.serialize(serializer);
        }
    }

    protected List<SchemaProperty> getPropertiesSortedBySequenceNumberAndName() {
        return properties.values()
                         .stream()
                         .sorted(Comparator.comparingInt(SchemaProperty::sequenceNumber)
                                           .thenComparing(SchemaProperty::name))
                         .toList();
    }

}
