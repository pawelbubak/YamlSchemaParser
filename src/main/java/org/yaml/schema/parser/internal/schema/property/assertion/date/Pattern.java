package org.yaml.schema.parser.internal.schema.property.assertion.date;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.validator.YamlValidator;
import org.yaml.schema.parser.api.validator.problem.AbstractMessage;
import org.yaml.schema.parser.api.validator.problem.Message;
import org.yaml.schema.parser.api.validator.problem.Problem;
import org.yaml.schema.parser.internal.schema.property.assertion.AbstractStringAssertion;
import org.yaml.schema.parser.internal.validator.problem.DefaultProblem;
import org.yaml.schema.parser.internal.validator.problem.ValidationMessage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SchemaPropertyContext(SchemaPropertyContext.Type.DATE)
@SchemaPropertyName("pattern")
@SchemaVersion(SpecVersion.DRAFT_01)
public class Pattern extends AbstractStringAssertion {

    public Pattern(String value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public Pattern(SpecVersion specVersion, String value) throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<String> mapper() {
        return (specVersion, value, propertyFactory) -> new Pattern(specVersion, value);
    }

    @Override
    public void test(YamlValidator validator, Object value) {
        if (!testValue(value)) {
            Problem problem = DefaultProblem.builder()
                                            .pointer(validator.getPointer())
                                            .message(getProblemMessage(value))
                                            .build();
            validator.reportProblem(problem);
        }
    }

    @Override
    public boolean testValue(Object value) {
        if (value instanceof Date) {
            return true;
        } else if (value instanceof String) {
            SimpleDateFormat format = new SimpleDateFormat(value());
            try {
                format.parse((String) value);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }
        return false;
    }

    protected Message getProblemMessage(Object value) {
        Map<String, Object> arguments = new HashMap<>(getProblemMessageArguments());
        arguments.put("value", String.valueOf(value));
        return ValidationMessage.builder().bundleKey(getProblemMessageCode()).arguments(arguments).build();
    }

    @Override
    protected AbstractMessage.Key getProblemMessageCode() {
        return ValidationMessage.Key.DATE_PATTERN_VALIDATION_PROBLEM;
    }

    @Override
    protected Map<String, Object> getProblemMessageArguments() {
        return Collections.singletonMap("pattern", value());
    }

}
