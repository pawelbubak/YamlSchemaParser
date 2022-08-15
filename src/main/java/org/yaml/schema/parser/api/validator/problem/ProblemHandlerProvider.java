package org.yaml.schema.parser.api.validator.problem;

import org.yaml.schema.parser.internal.utils.ProviderUtils;

import java.util.Locale;
import java.util.function.Consumer;

public interface ProblemHandlerProvider {

    /**
     * Returns an instance of this interface.
     *
     * @return the instance of this interface.
     * @throws IllegalStateException if instance of {@code ProblemHandlerProvider} interface not found.
     */
    static ProblemHandlerProvider provider() {
        return ProviderUtils.provider(ProblemHandlerProvider.class);
    }

    default ProblemHandler createCollectProblemHandler(Consumer<String> lineConsumer) {
        return createCollectProblemHandler(lineConsumer, Locale.getDefault());
    }

    ProblemHandler createCollectProblemHandler(Consumer<String> lineConsumer, Locale locale);

    default ProblemHandler createThrowingProblemHandler() {
        return createThrowingProblemHandler(Locale.getDefault());
    }

    ProblemHandler createThrowingProblemHandler(Locale locale);

}
