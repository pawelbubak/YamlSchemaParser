package org.yaml.schema.parser.internal.validator.problem;

import org.yaml.schema.parser.api.validator.problem.LineFormatter;
import org.yaml.schema.parser.api.validator.problem.LineFormatterProvider;
import org.yaml.schema.parser.api.validator.problem.Problem;
import org.yaml.schema.parser.api.validator.problem.ProblemPrinter;

import java.util.Locale;
import java.util.function.Consumer;

public class DefaultProblemPrinter implements ProblemPrinter {
    private final LineFormatter lineFormatter;

    public DefaultProblemPrinter() {
        this(LineFormatterProvider.provider().createFormatter());
    }

    public DefaultProblemPrinter(LineFormatter lineFormatter) {
        this.lineFormatter = lineFormatter;
    }

    @Override
    public void print(Problem problem, Locale locale, Consumer<String> consumer) {
        consumer.accept(lineFormatter.format(problem, locale));
    }

}
