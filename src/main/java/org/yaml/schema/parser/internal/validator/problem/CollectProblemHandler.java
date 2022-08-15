package org.yaml.schema.parser.internal.validator.problem;

import org.yaml.schema.parser.api.validator.problem.Problem;
import org.yaml.schema.parser.api.validator.problem.ProblemHandler;
import org.yaml.schema.parser.api.validator.problem.ProblemPrinter;
import org.yaml.schema.parser.api.validator.problem.ProblemPrinterProvider;

import java.util.Locale;
import java.util.function.Consumer;

public class CollectProblemHandler implements ProblemHandler {
    private final ProblemPrinter printer;
    private final Consumer<String> lineConsumer;
    private final Locale locale;

    public CollectProblemHandler(Consumer<String> lineConsumer) {
        this(ProblemPrinterProvider.provider().createPrinter(), lineConsumer);
    }

    public CollectProblemHandler(Consumer<String> lineConsumer, Locale locale) {
        this(ProblemPrinterProvider.provider().createPrinter(), lineConsumer, locale);
    }

    public CollectProblemHandler(ProblemPrinter printer, Consumer<String> lineConsumer) {
        this(printer, lineConsumer, Locale.getDefault());
    }

    public CollectProblemHandler(ProblemPrinter printer, Consumer<String> lineConsumer, Locale locale) {
        this.printer = printer;
        this.lineConsumer = lineConsumer;
        this.locale = locale;
    }

    @Override
    public void handleProblem(Problem problem) {
        printer.print(problem, locale, lineConsumer);
    }

}
