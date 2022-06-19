package org.yaml.schema.parser.internal.schema.reader;

import org.yaml.schema.parser.api.reader.YamlReaderProvider;
import org.yaml.schema.parser.api.schema.property.factory.SchemaPropertyFactoryProvider;
import org.yaml.schema.parser.api.schema.reader.SchemaSpecVersionDesignatorProvider;
import org.yaml.schema.parser.api.schema.reader.YamlSchemaReader;
import org.yaml.schema.parser.api.schema.reader.YamlSchemaReaderFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class DefaultYamlSchemaReaderFactory implements YamlSchemaReaderFactory {
    private final YamlReaderProvider yamlReaderProvider;
    private final SchemaSpecVersionDesignatorProvider specVersionDesignatorProvider;
    private final SchemaPropertyFactoryProvider propertyFactoryProvider;

    public DefaultYamlSchemaReaderFactory() {
        this.yamlReaderProvider = YamlReaderProvider.provider();
        this.specVersionDesignatorProvider = SchemaSpecVersionDesignatorProvider.provider();
        this.propertyFactoryProvider = SchemaPropertyFactoryProvider.provider();
    }

    @Override
    public YamlSchemaReader createSchemaReader(InputStream inputStream) {
        return new DefaultYamlSchemaReader(yamlReaderProvider.createReader(),
                specVersionDesignatorProvider.createDesignator(), propertyFactoryProvider, inputStream);
    }

    @Override
    public YamlSchemaReader createSchemaReader(Path path) throws IOException {
        return createSchemaReader(Files.newInputStream(path));
    }

}
