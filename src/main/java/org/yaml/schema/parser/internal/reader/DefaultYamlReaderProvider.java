package org.yaml.schema.parser.internal.reader;

import org.yaml.schema.parser.api.reader.YamlReader;
import org.yaml.schema.parser.api.reader.YamlReaderProvider;

public class DefaultYamlReaderProvider implements YamlReaderProvider {

    @Override
    public YamlReader createReader() {
        return new DefaultYamlReader();
    }

}
