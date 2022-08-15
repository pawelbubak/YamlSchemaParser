package org.yaml.schema.parser.api.validator.problem;

import java.util.Locale;

public interface LineFormatter {

    String format(Problem problem, Locale locale);

}
