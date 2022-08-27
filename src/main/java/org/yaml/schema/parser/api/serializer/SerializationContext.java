package org.yaml.schema.parser.api.serializer;

public interface SerializationContext {

    Context getContext();

    void setContext(Context context);

    String getIndentation();

    void startElement();

    void startArrayElement();

    void endElement();

    void endArrayElement();

    enum Context {
        ARRAY_ELEMENT, MAP_ELEMENT, NONE
    }

}
