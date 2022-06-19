package org.yaml.schema.parser.internal.schema.reader;

import org.yaml.schema.parser.api.reader.YamlReader;
import org.yaml.schema.parser.api.schema.YamlSchema;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.property.factory.SchemaPropertyFactory;
import org.yaml.schema.parser.api.schema.property.factory.SchemaPropertyFactoryProvider;
import org.yaml.schema.parser.api.schema.reader.SchemaSpecVersionDesignator;
import org.yaml.schema.parser.api.schema.reader.YamlSchemaReader;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.internal.schema.DefaultYamlSchema;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class DefaultYamlSchemaReader implements YamlSchemaReader {
    private final YamlReader yamlReader;
    private final SchemaSpecVersionDesignator specVersionDesignator;
    private final SchemaPropertyFactoryProvider propertyFactoryProvider;
    private final InputStream inputStream;

    private boolean alreadyRead = false;
    private boolean alreadyClosed = false;

    public DefaultYamlSchemaReader(YamlReader yamlReader, SchemaSpecVersionDesignator specVersionDesignator,
            SchemaPropertyFactoryProvider propertyFactoryProvider, InputStream inputStream) {
        this.yamlReader = yamlReader;
        this.specVersionDesignator = specVersionDesignator;
        this.propertyFactoryProvider = propertyFactoryProvider;
        this.inputStream = inputStream;
    }

    @Override
    public YamlSchema read() {
        if (alreadyRead || alreadyClosed) {
            throw new IllegalStateException("Already read or closed.");
        }
        alreadyRead = true;

        Map<String, Object> rawSchema = readYaml();

        SpecVersion specVersion = specVersionDesignator.getSpecVersion(rawSchema);
        SchemaPropertyFactory propertyFactory = getPropertyFactory(specVersion);

        Map<String, SchemaProperty> properties = prepareSchemaProperties(rawSchema, propertyFactory);

        return new DefaultYamlSchema(specVersion, properties);
    }

    @Override
    public void close() throws IOException {
        alreadyClosed = true;
        inputStream.close();
    }

    private Map<String, Object> readYaml() {
        return yamlReader.read(inputStream);
    }

    private SchemaPropertyFactory getPropertyFactory(SpecVersion specVersion) {
        return propertyFactoryProvider.getPropertyFactory(specVersion);
    }

    private Map<String, SchemaProperty> prepareSchemaProperties(Map<String, Object> rawSchema,
            SchemaPropertyFactory propertyFactory) {
        Map<String, SchemaProperty> properties = new HashMap<>();
        for (Map.Entry<String, Object> entry : rawSchema.entrySet()) {
            try {
                SchemaProperty property = propertyFactory.createProperty(entry.getKey(), entry.getValue());
                properties.put(property.name(), property);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return properties;
    }

}
