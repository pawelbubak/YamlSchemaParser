package org.yaml.schema.parser.internal.schema;

import lombok.AccessLevel;
import lombok.Getter;
import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.Schema;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.api.validator.YamlValidator;
import org.yaml.schema.parser.api.validator.problem.ProblemHandler;
import org.yaml.schema.parser.internal.schema.property.core.Definitions;
import org.yaml.schema.parser.internal.schema.property.core.Properties;
import org.yaml.schema.parser.internal.schema.property.core.annotation.Description;
import org.yaml.schema.parser.internal.schema.property.core.annotation.Title;
import org.yaml.schema.parser.internal.serializer.SerializerImpl;
import org.yaml.schema.parser.internal.utils.SchemaPropertyNameDesignator;
import org.yaml.schema.parser.internal.validator.DefaultYamlValidator;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import static org.yaml.schema.parser.internal.schema.validation.SchemaPropertyValidationUtils.requireNonNull;

public abstract class AbstractSchema extends AbstractMap<String, SchemaProperty> implements Schema {
    /**
     * The version of schema specification.
     */
    @Getter(AccessLevel.PROTECTED)
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
        try {
            String propertyName = SchemaPropertyNameDesignator.designatePropertyName(Title.class, specVersion);
            if (!containsProperty(propertyName)) {
                return null;
            }
            Title title = (Title) getProperty(propertyName);
            return title.value();
        } catch (SchemaPropertyNotExistsInSpecificationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String description() {
        try {
            String propertyName = SchemaPropertyNameDesignator.designatePropertyName(Description.class, specVersion);
            if (!containsProperty(propertyName)) {
                return null;
            }
            Description description = (Description) getProperty(propertyName);
            return description.value();
        } catch (SchemaPropertyNotExistsInSpecificationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Definitions definitions() {
        try {
            String propertyName = SchemaPropertyNameDesignator.designatePropertyName(Definitions.class, specVersion);
            if (!containsProperty(propertyName)) {
                return null;
            }
            return (Definitions) schemaProperties.get(propertyName);
        } catch (SchemaPropertyNotExistsInSpecificationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Properties properties() {
        try {
            String propertyName = SchemaPropertyNameDesignator.designatePropertyName(Properties.class, specVersion);
            if (!containsProperty(propertyName)) {
                return null;
            }
            return (Properties) schemaProperties.get(propertyName);
        } catch (SchemaPropertyNotExistsInSpecificationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Entry<String, SchemaProperty>> entrySet() {
        return schemaProperties.entrySet();
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        List<SchemaProperty> sortedProperties = getPropertiesSortedBySequenceNumberAndName();
        for (SchemaProperty property : sortedProperties) {
            property.serialize(serializer);
        }
    }

    @Override
    public void test(ProblemHandler problemHandler, Object yaml) {
        YamlValidator validator = new DefaultYamlValidator(problemHandler);
        properties().test(validator, yaml);
    }

    @Override
    public void test(YamlValidator validator, Object yaml) {
        properties().test(validator, yaml);
    }

    @Override
    public void format(OutputStream outputStream, Object yaml) throws IOException {
        format(new SerializerImpl(outputStream), yaml);
    }

    @Override
    public void format(Serializer serializer, Object yaml) throws IOException {
        properties().format(serializer, yaml);
    }

    protected boolean containsProperty(String propertyName) {
        requireNonNull(propertyName, "Property name");
        return schemaProperties.containsKey(propertyName);
    }

    protected SchemaProperty getProperty(String name) {
        return schemaProperties.get(name);
    }

    private List<SchemaProperty> getPropertiesSortedBySequenceNumberAndName() {
        return schemaProperties.values()
                               .stream()
                               .sorted(Comparator.comparingInt(SchemaProperty::sequenceNumber)
                                                 .thenComparing(SchemaProperty::name))
                               .toList();
    }

}
