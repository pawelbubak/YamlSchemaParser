package org.yaml.schema.parser.api.validator.problem;

import org.yaml.schema.parser.internal.utils.ProviderUtils;

public interface ProblemPrinterProvider {

    /**
     * Returns an instance of this interface.
     *
     * @return the instance of this interface.
     * @throws IllegalStateException if instance of {@code ProblemPrinterProvider} interface not found.
     */
    static ProblemPrinterProvider provider() {
        return ProviderUtils.provider(ProblemPrinterProvider.class);
    }

    ProblemPrinter createPrinter();

}
