package org.yaml.schema.parser.internal.validator.problem;

import org.yaml.schema.parser.api.validator.problem.LineFormatter;
import org.yaml.schema.parser.api.validator.problem.Problem;

import java.util.Locale;

public class DefaultLineFormatter implements LineFormatter {
    private final static String LINE_FORMAT = "[%s] %s";

    public DefaultLineFormatter() {
    }

    @Override
    public String format(Problem problem, Locale locale) {
        return String.format(LINE_FORMAT, problem.getPointer(), problem.getMessage(locale));
    }

}
