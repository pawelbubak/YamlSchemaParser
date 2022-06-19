package org.yaml.schema.parser.internal.schema.reader;

import org.yaml.schema.parser.api.schema.reader.YamlSchemaReaderFactory;
import org.yaml.schema.parser.api.schema.reader.YamlSchemaReaderFactoryProvider;

public class DefaultYamlSchemaReaderFactoryProvider implements YamlSchemaReaderFactoryProvider {

    @Override
    public YamlSchemaReaderFactory createFactory() {
        return new DefaultYamlSchemaReaderFactory();
    }

}
