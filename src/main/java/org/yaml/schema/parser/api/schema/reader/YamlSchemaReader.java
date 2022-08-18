package org.yaml.schema.parser.api.schema.reader;

import org.yaml.schema.parser.api.schema.YamlSchema;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;


/**
 * A reader interface for reading a JSON schema from an input source.
 */
public interface YamlSchemaReader extends Closeable {

    /**
     * Creates a default YAML schema reader from a path.
     *
     * @param path the path from which a YAML schema is to be read.
     * @return newly created instance of YAML schema reader.
     * @throws IOException          if an I/O error occurs while creating reader.
     * @throws NullPointerException if the specified {@code path} is {@code null}.
     * @see YamlSchemaReader
     */
    static YamlSchemaReader createDefaultReader(Path path) throws IOException {
        YamlSchemaReaderFactory readerFactory = getDefaultFactory();
        return readerFactory.createSchemaReader(path);
    }

    /**
     * Creates a default YAML schema reader from a byte stream.
     *
     * @param input the byte stream from which a YAML schema is to be read.
     * @return newly created instance of YAML schema reader.
     * @throws NullPointerException if the specified {@code in} is {@code null}.
     * @see YamlSchemaReader
     */
    static YamlSchemaReader createDefaultReader(InputStream input) {
        YamlSchemaReaderFactory readerFactory = getDefaultFactory();
        return readerFactory.createSchemaReader(input);
    }

    /**
     * Creates a default YAML schema reader factory.
     *
     * @return newly created instance of YAML schema reader factory.
     * @throws NullPointerException if the specified {@code in} is {@code null}.
     * @see YamlSchemaReader
     */
    static YamlSchemaReaderFactory getDefaultFactory() {
        return YamlSchemaReaderFactoryProvider.provider().createFactory();
    }

    /**
     * Returns a YAML schema that is represented in the input source. This method needs to be called only once for a
     * reader instance.
     */
    YamlSchema read();

    /**
     * Closes this reader and frees any resources associated with this reader. This method closes the underlying
     * input source.
     */
    @Override
    void close() throws IOException;

}
