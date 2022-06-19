package org.yaml.schema.parser.api.reader;

import java.io.InputStream;
import java.util.Map;

public interface YamlReader {

    Map<String, Object> read(InputStream inputStream);

}
