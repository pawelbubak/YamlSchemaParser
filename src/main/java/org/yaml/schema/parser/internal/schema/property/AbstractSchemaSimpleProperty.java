package org.yaml.schema.parser.internal.schema.property;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.property.SchemaSimpleProperty;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.api.validator.YamlValidator;
import org.yaml.schema.parser.api.validator.problem.AbstractMessage;
import org.yaml.schema.parser.api.validator.problem.Message;
import org.yaml.schema.parser.api.validator.problem.Problem;
import org.yaml.schema.parser.internal.validator.problem.DefaultProblem;
import org.yaml.schema.parser.internal.validator.problem.ValidationMessage;

import java.io.IOException;
import java.util.Map;

public abstract class AbstractSchemaSimpleProperty<T> extends AbstractSchemaProperty
        implements SchemaSimpleProperty<T> {
    /**
     * The value of this property.
     */
    private final T value;

    public AbstractSchemaSimpleProperty(SpecVersion specVersion, T value)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion);
        this.value = value;
    }

    @Override
    public T value() {
        return value;
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        serializer.startSimpleElement(name());
        serializeValue(serializer);
        serializer.endSimpleElement();
    }

    protected abstract void serializeValue(Serializer serializer) throws IOException;

    @Override
    public void test(YamlValidator validator, Object value) {
        if (!testValue(value)) {
            Problem problem =
                    DefaultProblem.builder()
                                  .pointer(validator.getContext().getPointer())
                                  .message(getProblemMessage())
                                  .build();
            validator.reportProblem(problem);
        }
    }

    protected Message getProblemMessage() {
        return ValidationMessage.builder()
                                .bundleKey(getProblemMessageCode())
                                .arguments(getProblemMessageArguments())
                                .build();
    }

    protected abstract AbstractMessage.Key getProblemMessageCode();

    protected abstract Map<String, Object> getProblemMessageArguments();

}
