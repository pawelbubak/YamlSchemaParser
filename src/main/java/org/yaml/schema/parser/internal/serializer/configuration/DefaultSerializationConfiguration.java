package org.yaml.schema.parser.internal.serializer.configuration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.yaml.schema.parser.api.serializer.configuration.IndentationType;
import org.yaml.schema.parser.api.serializer.configuration.SerializationConfiguration;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultSerializationConfiguration implements SerializationConfiguration {
    @Builder.Default
    private Charset charset = StandardCharsets.UTF_8;
    @Builder.Default
    private DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    @Builder.Default
    private DecimalFormat decimalFormatter = new DecimalFormat("0.###", new DecimalFormatSymbols(Locale.US));
    @Builder.Default
    private DecimalFormat integerFormatter = new DecimalFormat("#", new DecimalFormatSymbols(Locale.US));
    @Builder.Default
    private IndentationType indentationType = IndentationType.SPACE;
    @Builder.Default
    private int indentationSize = 2;
    @Builder.Default
    private boolean indentArrayItems = true;

    @Override
    public Charset getCharset() {
        return charset;
    }

    @Override
    public DateFormat getDateFormatter() {
        return dateFormatter;
    }

    @Override
    public DecimalFormat getDecimalFormatter() {
        return decimalFormatter;
    }

    @Override
    public DecimalFormat getIntegerFormatter() {
        return integerFormatter;
    }

    @Override
    public IndentationType getIndentationType() {
        return indentationType;
    }

    @Override
    public int getIndentationSize() {
        return indentationSize;
    }

    @Override
    public boolean indentArrayItems() {
        return true;
    }

    public static class CustomSerializationConfigurationBuilder {
        private Charset charset;

        public CustomSerializationConfigurationBuilder charset(Charset charset) {
            verifyCharset(charset);
            this.charset = charset;
            return this;
        }

        private void verifyCharset(Charset charset) {
            if (!(StandardCharsets.UTF_8.equals(charset) || StandardCharsets.UTF_16.equals(charset))) {
                throw new IllegalStateException("Not supported charset!");
            }
        }

    }

}
