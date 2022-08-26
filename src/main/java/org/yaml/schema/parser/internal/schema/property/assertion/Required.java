package org.yaml.schema.parser.internal.schema.property.assertion;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.api.validator.YamlValidator;
import org.yaml.schema.parser.api.validator.problem.AbstractMessage;
import org.yaml.schema.parser.api.validator.problem.Message;
import org.yaml.schema.parser.api.validator.problem.Problem;
import org.yaml.schema.parser.internal.validator.problem.DefaultProblem;
import org.yaml.schema.parser.internal.validator.problem.ValidationMessage;

import java.io.IOException;
import java.util.Collections;

@SchemaPropertyContext(SchemaPropertyContext.Type.DEFAULT)
@SchemaPropertyName("required")
@SchemaVersion(SpecVersion.DRAFT_01)
public class Required extends AbstractBooleanAssertion {

    public Required(Boolean value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public Required(SpecVersion specVersion, Boolean value) throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<Boolean> mapper() {
        return (specVersion, value, propertyFactory) -> new Required(specVersion, value);
    }

    @Override
    public int sequenceNumber() {
        return 2;
    }

    @Override
    protected void serializeValue(Serializer serializer) throws IOException {
        serializer.writePropertyValue(value());
    }

    @Override
    public void test(YamlValidator validator, Object value) {
        if (!testValue(value)) {
            Problem problem = DefaultProblem.builder()
                                            .pointer(validator.getPointer())
                                            .message(getProblemMessage(validator.getContext()))
                                            .build();
            validator.reportProblem(problem);
        }
    }

    @Override
    public boolean testValue(Object value) {
        return !value() || value != null;
    }

    @Override
    protected AbstractMessage.Key getProblemMessageCode() {
        return ValidationMessage.Key.REQUIRED_VALIDATION_PROBLEM;
    }

    protected Message getProblemMessage(String context) {
        return ValidationMessage.builder()
                                .bundleKey(getProblemMessageCode())
                                .arguments(Collections.singletonMap("element", context))
                                .build();
    }

}
