package org.yaml.schema.parser.internal.validator.context;

import org.yaml.schema.parser.api.validator.context.ValidationContext;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Stack;

public class DefaultValidationContext implements ValidationContext {
    private final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    private final DecimalFormat decimalFormatter = new DecimalFormat("0.###", new DecimalFormatSymbols(Locale.US));
    private final Stack<String> stack = new Stack<>();

    @Override
    public String current() {
        return stack.peek();
    }

    @Override
    public String pointer() {
        return "/" + String.join("/", stack);
    }

    @Override
    public void push(BigDecimal value) {
        stack.push(decimalFormatter.format(value));
    }

    @Override
    public void push(Boolean value) {
        stack.push(String.valueOf(value));
    }

    @Override
    public void push(Date value) {
        stack.push(dateFormatter.format(dateFormatter));
    }

    @Override
    public void push(Double value) {
        stack.push(decimalFormatter.format(value));
    }

    @Override
    public void push(Integer value) {
        stack.push(decimalFormatter.format(value));
    }

    @Override
    public void push(Long value) {
        stack.push(decimalFormatter.format(value));
    }

    @Override
    public void push(String value) {
        stack.push(value);
    }

    @Override
    public void pop() {
        stack.pop();
    }

}
