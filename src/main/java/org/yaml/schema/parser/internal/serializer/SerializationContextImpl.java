package org.yaml.schema.parser.internal.serializer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.yaml.schema.parser.api.serializer.SerializationContext;
import org.yaml.schema.parser.api.serializer.configuration.SerializationConfiguration;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SerializationContextImpl implements SerializationContext {
    @Builder.Default
    private int depth = -1;
    private SerializationConfiguration configuration;

    @Override
    public String getIndentation() {
        return configuration.getIndentationType().getValue().repeat(configuration.getIndentationSize()).repeat(depth);
    }

    @Override
    public SerializationContext getArraySerializationContext() {
        return SerializationContextImpl.builder()
                .configuration(configuration)
                .depth(configuration.indentArrayItems() ? depth + 1 : depth)
                .build();
    }

    @Override
    public SerializationContext getObjectSerializationContext() {
        return SerializationContextImpl.builder().configuration(configuration).depth(depth).build();
    }

    @Override
    public void startElement() {
        depth++;
    }

    @Override
    public void endElement() {
        depth--;
    }

}
