package org.yaml.schema.parser.api.validator.problem;

public interface ProblemHandler {

    /**
     * Handle the problem found while validating a YAML document.
     *
     * @param problem the problem found, cannot be {@code null}.
     */
    void handleProblem(Problem problem);

}
