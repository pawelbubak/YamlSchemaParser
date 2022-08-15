package org.yaml.schema.parser.api.validator.problem;

import java.util.Locale;

public interface Problem {

    /**
     * Returns the YAML pointer where this problem is found in the input source.
     *
     * @return the string representation of YAML pointer where this problem
     * occurred. This can be {@code null} if the location is unknown.
     */
    String getPointer();

    /**
     * Return the message describing this problem.
     *
     * @return the message describing this problem, never be {@code null}.
     */
    default String getMessage() {
        return getMessage(Locale.getDefault());
    }

    /**
     * Return the message describing this problem, which will be localized for the
     * specified locale.
     *
     * @param locale the locale for which the message will be localized.
     * @return the message describing this problem, never be {@code null}.
     * @throws NullPointerException if the specified {@code locale} is {@code null}.
     */
    String getMessage(Locale locale);

}
