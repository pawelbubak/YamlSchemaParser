package org.yaml.schema.parser.api.validator.problem;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class AbstractMessage implements Message {

    @Override
    public String getMessage(Locale locale) {
        if (hasArguments()) {
            return format(locale);
        } else {
            return getLocalizedMessage(locale);
        }
    }

    protected abstract String getBundlePath();

    protected abstract Key getBundleKey();

    protected abstract Map<String, Object> getArguments();

    private boolean hasArguments() {
        Map<String, Object> arguments = getArguments();
        return arguments != null && !arguments.isEmpty();
    }

    private String format(Locale locale) {
        String message = getLocalizedMessage(locale);
        Map<String, Object> arguments = getArguments();
        for (String argumentName : arguments.keySet().stream().sorted().toList()) {
            String argumentPattern = "${" + argumentName + "}";
            message = message.replace(argumentPattern, arguments.get(argumentName).toString());
        }
        return message;
    }

    private String getLocalizedMessage(Locale locale) {
        return getBundle(locale).getString(getBundleKey().name());
    }

    private ResourceBundle getBundle(Locale locale) {
        return ResourceBundle.getBundle(getBundlePath(), locale);
    }

    public interface Key {

        String name();

    }

}
