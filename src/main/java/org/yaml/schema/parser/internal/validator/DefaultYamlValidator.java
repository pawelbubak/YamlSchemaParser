package org.yaml.schema.parser.internal.validator;

import org.yaml.schema.parser.api.validator.YamlValidator;
import org.yaml.schema.parser.api.validator.context.ValidationContext;
import org.yaml.schema.parser.api.validator.context.ValidationContextProvider;
import org.yaml.schema.parser.api.validator.problem.Problem;
import org.yaml.schema.parser.api.validator.problem.ProblemHandler;

import java.math.BigDecimal;
import java.util.Date;

public class DefaultYamlValidator implements YamlValidator {
    private final ValidationContext context;
    private final ProblemHandler problemHandler;

    public DefaultYamlValidator(ProblemHandler problemHandler) {
        this(ValidationContextProvider.provider().createContext(), problemHandler);
    }

    public DefaultYamlValidator(ValidationContext context, ProblemHandler problemHandler) {
        this.context = context;
        this.problemHandler = problemHandler;
    }

    @Override
    public void reportProblem(Problem problem) {
        problemHandler.handleProblem(problem);
    }

    @Override
    public void startElement(Object name) {
        if (name instanceof BigDecimal) {
            context.push((BigDecimal) name);
        } else if (name instanceof Boolean) {
            context.push((Boolean) name);
        } else if (name instanceof Date) {
            context.push((Date) name);
        } else if (name instanceof Double) {
            context.push((Double) name);
        } else if (name instanceof Integer) {
            context.push((Integer) name);
        } else if (name instanceof Long) {
            context.push((Long) name);
        } else if (name instanceof String) {
            context.push((String) name);
        } else {
            context.push(name.toString());
        }
    }

    @Override
    public void endElement() {
        context.pop();
    }

    @Override
    public String getContext() {
        return context.current();
    }

    @Override
    public String getPointer() {
        return context.pointer();
    }

}
