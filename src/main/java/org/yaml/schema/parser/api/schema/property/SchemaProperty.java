package org.yaml.schema.parser.api.schema.property;

import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.api.validator.YamlValidator;

import java.io.IOException;

public interface SchemaProperty {

    /**
     * Returns the name of this property.
     *
     * @return the name of this property, never be {@code null}.
     */
    String name();

    /**
     * Returns the sequence number of this property. Used for the schema serialization.
     *
     * @return the sequence number of this property, never be {@code null}.
     */
    int sequenceNumber();

    void serialize(Serializer serializer) throws IOException;

    void test(YamlValidator validator, Object value);

    void format(Serializer serializer, Object value) throws IOException;

}
