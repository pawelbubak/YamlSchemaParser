package org.yaml.schema.parser.api.schema.reader;

import org.yaml.schema.parser.api.schema.YamlSchema;

import java.io.Closeable;
import java.io.IOException;


/**
 * A reader interface for reading a JSON schema from an input source.
 */
public interface YamlSchemaReader extends Closeable {

    /**
     * Returns a JSON schema that is represented in the input source. This method needs to be called only once for a
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
