package org.yaml.schema.parser.internal.validator.problem;

import org.yaml.schema.parser.api.validator.problem.Problem;
import org.yaml.schema.parser.api.validator.problem.ProblemHandler;
import org.yaml.schema.parser.api.validator.problem.ProblemPrinter;
import org.yaml.schema.parser.api.validator.problem.ProblemPrinterProvider;

import java.util.Locale;

public class ThrowingProblemHandler implements ProblemHandler {
    private final ProblemPrinter printer;
    private final Locale locale;

    public ThrowingProblemHandler() {
        this(ProblemPrinterProvider.provider().createPrinter(), Locale.getDefault());
    }

    public ThrowingProblemHandler(ProblemPrinter printer) {
        this(printer, Locale.getDefault());
    }

    public ThrowingProblemHandler(Locale locale) {
        this(ProblemPrinterProvider.provider().createPrinter(), locale);
    }

    public ThrowingProblemHandler(ProblemPrinter printer, Locale locale) {
        this.printer = printer;
        this.locale = locale;
    }

    @Override
    public void handleProblem(Problem problem) {
        String message = printer.print(problem, locale);
        throw new RuntimeException(message);
    }

}
