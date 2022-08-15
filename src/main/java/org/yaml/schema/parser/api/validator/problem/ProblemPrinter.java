package org.yaml.schema.parser.api.validator.problem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public interface ProblemPrinter {

    default String print(Problem problem, Locale locale) {
        List<String> messageLines = new ArrayList<>();
        print(problem, locale, messageLines::add);
        return String.join("\n", messageLines);
    }

    void print(Problem problem, Locale locale, Consumer<String> consumer);

}
