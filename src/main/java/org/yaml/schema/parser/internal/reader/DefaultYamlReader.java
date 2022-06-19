package org.yaml.schema.parser.internal.reader;

import org.yaml.schema.parser.api.reader.YamlReader;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class DefaultYamlReader implements YamlReader {
    private final Yaml yaml;

    public DefaultYamlReader() {
        this.yaml = new Yaml();
    }

    @Override
    public Map<String, Object> read(InputStream inputStream) {
        try (inputStream) {
            return yaml.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
