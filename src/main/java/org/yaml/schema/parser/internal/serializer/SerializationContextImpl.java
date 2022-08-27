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
    @Builder.Default
    private Context context = Context.NONE;
    private SerializationConfiguration configuration;
    private boolean firstArrayElement;

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public String getIndentation() {
        if (firstArrayElement) {
            firstArrayElement = false;
            return configuration.getIndentationType()
                                .getValue()
                                .repeat(configuration.getIndentationSize())
                                .substring(1);
        } else {
            return configuration.getIndentationType()
                                .getValue()
                                .repeat(configuration.getIndentationSize())
                                .repeat(depth);
        }
    }

    @Override
    public void startElement() {
        depth++;
    }

    @Override
    public void startArrayElement() {
        firstArrayElement = true;
    }

    @Override
    public void endElement() {
        depth--;
        context = Context.NONE;
    }

    @Override
    public void endArrayElement() {
        firstArrayElement = false;
    }

}
