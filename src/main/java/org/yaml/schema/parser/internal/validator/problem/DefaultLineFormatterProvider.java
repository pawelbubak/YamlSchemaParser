package org.yaml.schema.parser.internal.validator.problem;

import org.yaml.schema.parser.api.validator.problem.LineFormatter;
import org.yaml.schema.parser.api.validator.problem.LineFormatterProvider;

public class DefaultLineFormatterProvider implements LineFormatterProvider {

    @Override
    public LineFormatter createFormatter() {
        return new DefaultLineFormatter();
    }

}
