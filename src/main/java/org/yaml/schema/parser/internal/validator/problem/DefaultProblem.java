package org.yaml.schema.parser.internal.validator.problem;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.yaml.schema.parser.api.validator.problem.Message;
import org.yaml.schema.parser.api.validator.problem.Problem;

import java.util.Locale;

import static org.yaml.schema.parser.internal.utils.ArgumentsUtils.requireNonNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultProblem implements Problem {
    private final String pointer;
    private final Message message;

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String getPointer() {
        return pointer;
    }

    @Override
    public String getMessage(Locale locale) {
        return message.getMessage(locale);
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder {
        private String pointer;
        private Message message;

        public Builder pointer(String pointer) {
            requireNonNull(pointer, "pointer");
            this.pointer = pointer;
            return this;
        }

        public Builder message(Message message) {
            requireNonNull(message, "message");
            this.message = message;
            return this;
        }

        public Problem build() {
            return new DefaultProblem(pointer, message);
        }

    }

}
