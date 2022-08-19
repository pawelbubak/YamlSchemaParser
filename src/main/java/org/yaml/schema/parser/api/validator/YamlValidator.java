package org.yaml.schema.parser.api.validator;

import org.yaml.schema.parser.api.validator.problem.Problem;

public interface YamlValidator {

    void reportProblem(Problem problem);

    void startElement(Object name);

    void endElement();

    String getContext();

    String getPointer();

}
