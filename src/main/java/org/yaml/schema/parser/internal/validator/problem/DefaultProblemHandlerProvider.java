package org.yaml.schema.parser.internal.validator.problem;

import org.yaml.schema.parser.api.validator.problem.ProblemHandler;
import org.yaml.schema.parser.api.validator.problem.ProblemHandlerProvider;

import java.util.Locale;
import java.util.function.Consumer;

public class DefaultProblemHandlerProvider implements ProblemHandlerProvider {

    @Override
    public ProblemHandler createCollectProblemHandler(Consumer<String> lineConsumer, Locale locale) {
        return new CollectProblemHandler(lineConsumer, locale);
    }

    @Override
    public ProblemHandler createThrowingProblemHandler(Locale locale) {
        return new ThrowingProblemHandler(locale);
    }

}
