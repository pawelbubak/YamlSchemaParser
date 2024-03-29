package org.yaml.schema.parser.api.schema.reader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * A factory interface for creating {@link YamlSchemaReader} instances.
 */
public interface YamlSchemaReaderFactory {

    /**
     * Creates a YAML schema reader from a path.
     *
     * @param path the path from which a YAML schema is to be read.
     * @return newly created instance of YAML schema reader.
     * @throws IOException          if an I/O error occurs while creating reader.
     * @throws NullPointerException if the specified {@code path} is {@code null}.
     * @see YamlSchemaReader
     */
    YamlSchemaReader createSchemaReader(Path path) throws IOException;

    /**
     * Creates a YAML schema reader from a byte stream.
     *
     * @param input the byte stream from which a YAML schema is to be read.
     * @return newly created instance of YAML schema reader.
     * @throws NullPointerException if the specified {@code in} is {@code null}.
     * @see YamlSchemaReader
     */
    YamlSchemaReader createSchemaReader(InputStream input);

}
