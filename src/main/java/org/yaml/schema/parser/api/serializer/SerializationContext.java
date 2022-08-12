package org.yaml.schema.parser.api.serializer;

public interface SerializationContext {

    String getIndentation();

    SerializationContext getArraySerializationContext();

    SerializationContext getObjectSerializationContext();

    void startElement();

    void endElement();

}
