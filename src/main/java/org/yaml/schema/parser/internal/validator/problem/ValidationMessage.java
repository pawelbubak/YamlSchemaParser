package org.yaml.schema.parser.internal.validator.problem;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.yaml.schema.parser.api.validator.problem.AbstractMessage;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationMessage extends AbstractMessage {
    private static final String BUNDLE_PATH = "org/yaml/schema/parser/validator/messages";

    private final AbstractMessage.Key bundleKey;
    private final Map<String, Object> arguments;

    public static Builder builder() {
        return new Builder();
    }

    @Override
    protected String getBundlePath() {
        return BUNDLE_PATH;
    }

    @Override
    protected AbstractMessage.Key getBundleKey() {
        return bundleKey;
    }

    @Override
    protected Map<String, Object> getArguments() {
        return arguments;
    }

    public enum Key implements AbstractMessage.Key {
        /* Array validation messages */
        MAXIMUM_ITEMS_VALIDATION_PROBLEM,
        MINIMUM_ITEMS_VALIDATION_PROBLEM,
        /* Boolean validation messages */
        BOOLEAN_VALIDATION_PROBLEM,
        /* Date validation messages */
        DATE_VALIDATION_PROBLEM,
        DATE_PATTERN_VALIDATION_PROBLEM,
        MAXIMUM_DATE_VALIDATION_PROBLEM,
        MINIMUM_DATE_VALIDATION_PROBLEM,
        /* Number validation messages */
        EXCLUSIVE_MAXIMUM_VALIDATION_PROBLEM,
        EXCLUSIVE_MINIMUM_VALIDATION_PROBLEM,
        INTEGER_VALIDATION_PROBLEM,
        MAXIMUM_VALIDATION_PROBLEM,
        MINIMUM_VALIDATION_PROBLEM,
        /* String validation messages */
        MAXIMUM_LENGTH_VALIDATION_PROBLEM,
        MINIMUM_LENGTH_VALIDATION_PROBLEM,
        STRING_PATTERN_VALIDATION_PROBLEM,
        /* Other validation messages */
        ENUM_VALIDATION_PROBLEM,
        REQUIRED_VALIDATION_PROBLEM,
        /* Test messages */
        TEST_MESSAGE
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder {
        private final Map<String, Object> arguments = new HashMap<>();
        private AbstractMessage.Key bundleKey;

        public Builder bundleKey(AbstractMessage.Key bundleKey) {
            this.bundleKey = bundleKey;
            return this;
        }

        public Builder argument(String key, Object value) {
            if (key != null) {
                this.arguments.put(key, value);
            }
            return this;
        }

        public Builder arguments(Map<String, Object> arguments) {
            if (arguments != null) {
                this.arguments.putAll(arguments);
            }
            return this;
        }

        public ValidationMessage build() {
            return new ValidationMessage(bundleKey, arguments);
        }

    }

}

