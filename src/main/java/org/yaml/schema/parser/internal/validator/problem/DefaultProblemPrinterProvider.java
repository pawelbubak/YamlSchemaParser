package org.yaml.schema.parser.internal.validator.problem;

import org.yaml.schema.parser.api.validator.problem.ProblemPrinter;
import org.yaml.schema.parser.api.validator.problem.ProblemPrinterProvider;

public class DefaultProblemPrinterProvider implements ProblemPrinterProvider {

    @Override
    public ProblemPrinter createPrinter() {
        return new DefaultProblemPrinter();
    }

}
