package org.yaml.schema.parser.internal.serializer;

import lombok.AllArgsConstructor;
import org.yaml.schema.parser.api.serializer.SerializationContext;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.api.serializer.configuration.SerializationConfiguration;
import org.yaml.schema.parser.internal.serializer.configuration.DefaultSerializationConfiguration;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.yaml.schema.parser.internal.schema.property.assertion.utils.MapperUtils.mapToBigDecimal;

@AllArgsConstructor
public class SerializerImpl implements Serializer {
    private final OutputStream outputStream;
    private final SerializationConfiguration serializationConfiguration;
    private final SerializationContext serializationContext;

    public SerializerImpl(OutputStream outputStream) {
        this(outputStream, DefaultSerializationConfiguration.builder().build());
    }

    public SerializerImpl(OutputStream outputStream, SerializationConfiguration serializationConfiguration) {
        this.outputStream = outputStream;
        this.serializationConfiguration = serializationConfiguration;
        this.serializationContext = createInitialContext();
    }

    @Override
    public SerializationContext createInitialContext() {
        return SerializationContextImpl.builder().configuration(serializationConfiguration).build();
    }

    @Override
    public void setContext(SerializationContext.Context context) {
        serializationContext.setContext(context);
    }

    @Override
    public void startArrayElement() throws IOException {
        serializationContext.startElement();
        outputStream.write(serializationContext.getIndentation().getBytes(serializationConfiguration.getCharset()));
        outputStream.write(SerializationConfiguration.DASH.getBytes(serializationConfiguration.getCharset()));
        serializationContext.startArrayElement();
        serializationContext.setContext(SerializationContext.Context.ARRAY_ELEMENT);
    }

    @Override
    public void startComplexElement(Object rawPropertyName) throws IOException {
        String propertyName = getStringValue(rawPropertyName);
        startComplexElement(propertyName);
    }

    @Override
    public void startComplexElement(String propertyName) throws IOException {
        if (SerializationContext.Context.ARRAY_ELEMENT.equals(serializationContext.getContext())) {
            serializationContext.setContext(SerializationContext.Context.NONE);
        } else if (SerializationContext.Context.MAP_ELEMENT.equals(serializationContext.getContext())) {
            serializationContext.setContext(SerializationContext.Context.NONE);
        } else {
            serializationContext.startElement();
            outputStream.write(serializationContext.getIndentation().getBytes(serializationConfiguration.getCharset()));
            outputStream.write(propertyName.getBytes(serializationConfiguration.getCharset()));
            outputStream.write(SerializationConfiguration.COLON.getBytes(serializationConfiguration.getCharset()));
            outputStream.write(
                    SerializationConfiguration.LINE_TERMINATION.getBytes(serializationConfiguration.getCharset()));
        }
    }

    @Override
    public void startComplexMapElement(Object rawPropertyName) throws IOException {
        startComplexElement(rawPropertyName);
        serializationContext.setContext(SerializationContext.Context.MAP_ELEMENT);
    }

    @Override
    public void startSimpleElement(Object rawPropertyName) throws IOException {
        String propertyName = getStringValue(rawPropertyName);
        startSimpleElement(propertyName);
    }

    @Override
    public void startSimpleElement(String propertyName) throws IOException {
        if (SerializationContext.Context.NONE.equals(serializationContext.getContext())) {
            serializationContext.startElement();
            outputStream.write(serializationContext.getIndentation().getBytes(serializationConfiguration.getCharset()));
            outputStream.write(propertyName.getBytes(serializationConfiguration.getCharset()));
            outputStream.write(SerializationConfiguration.COLON.getBytes(serializationConfiguration.getCharset()));
        } else if (SerializationContext.Context.MAP_ELEMENT.equals(serializationContext.getContext())) {
            serializationContext.startElement();
        }
    }

    @Override
    public void startSimpleMapElement(Object rawPropertyName) throws IOException {
        startSimpleElement(rawPropertyName);
        serializationContext.setContext(SerializationContext.Context.MAP_ELEMENT);
    }

    @Override
    public void writeEmptyObject() throws IOException {
        outputStream.write(SerializationConfiguration.SPACE.getBytes(serializationConfiguration.getCharset()));
        outputStream.write("{}".getBytes(serializationConfiguration.getCharset()));
    }


    @Override
    public void writePropertyValue(BigDecimal value) throws IOException {
        boolean isInteger = value.signum() == 0 || value.scale() <= 0 || value.stripTrailingZeros().scale() <= 0;
        NumberFormat formatter = isInteger
                ? serializationConfiguration.getIntegerFormatter()
                : serializationConfiguration.getDecimalFormatter();
        outputStream.write(SerializationConfiguration.SPACE.getBytes(serializationConfiguration.getCharset()));
        outputStream.write(formatter.format(value).getBytes(serializationConfiguration.getCharset()));
    }

    @Override
    public void writePropertyValue(Boolean value) throws IOException {
        outputStream.write(SerializationConfiguration.SPACE.getBytes(serializationConfiguration.getCharset()));
        outputStream.write(String.valueOf(value).getBytes(serializationConfiguration.getCharset()));
    }

    @Override
    public void writePropertyValue(Date value) throws IOException {
        outputStream.write(SerializationConfiguration.SPACE.getBytes(serializationConfiguration.getCharset()));
        outputStream.write(serializationConfiguration.getDateFormatter()
                                                     .format(value)
                                                     .getBytes(serializationConfiguration.getCharset()));
    }

    @Override
    public void writePropertyValue(Double value) throws IOException {
        outputStream.write(SerializationConfiguration.SPACE.getBytes(serializationConfiguration.getCharset()));
        outputStream.write(serializationConfiguration.getDecimalFormatter()
                                                     .format(value)
                                                     .getBytes(serializationConfiguration.getCharset()));
    }

    @Override
    public void writePropertyValue(Integer value) throws IOException {
        outputStream.write(SerializationConfiguration.SPACE.getBytes(serializationConfiguration.getCharset()));
        outputStream.write(serializationConfiguration.getIntegerFormatter()
                                                     .format(value)
                                                     .getBytes(serializationConfiguration.getCharset()));
    }

    @Override
    public void writePropertyValue(Long value) throws IOException {
        outputStream.write(SerializationConfiguration.SPACE.getBytes(serializationConfiguration.getCharset()));
        outputStream.write(serializationConfiguration.getIntegerFormatter()
                                                     .format(value)
                                                     .getBytes(serializationConfiguration.getCharset()));
    }

    @Override
    public void writePropertyValue(Number value) throws IOException {
        BigDecimal testValue = BigDecimal.valueOf(value.doubleValue());
        boolean isInteger =
                testValue.signum() == 0 || testValue.scale() <= 0 || testValue.stripTrailingZeros().scale() <= 0;
        NumberFormat formatter = isInteger
                ? serializationConfiguration.getIntegerFormatter()
                : serializationConfiguration.getDecimalFormatter();
        outputStream.write(SerializationConfiguration.SPACE.getBytes(serializationConfiguration.getCharset()));
        outputStream.write(formatter.format(value).getBytes(serializationConfiguration.getCharset()));
    }

    @Override
    public void writePropertyValue(String value) throws IOException {
        outputStream.write(SerializationConfiguration.SPACE.getBytes(serializationConfiguration.getCharset()));
        boolean writeQuotationMarks =
                !value.isEmpty() && (!Character.isLetterOrDigit(value.charAt(0)) || isNumeric(value));
        if (writeQuotationMarks) {
            outputStream.write(
                    SerializationConfiguration.QUOTATION_MARK.getBytes(serializationConfiguration.getCharset()));
        }
        outputStream.write(value.getBytes(serializationConfiguration.getCharset()));
        if (writeQuotationMarks) {
            outputStream.write(
                    SerializationConfiguration.QUOTATION_MARK.getBytes(serializationConfiguration.getCharset()));
        }
    }

    @Override
    public void writePropertyValue(List<?> rawValues) throws IOException {
        String values = rawValues.stream().map(value -> {
            if (value instanceof Date) {
                return serializationConfiguration.getDateFormatter().format(value);
            } else if (value instanceof Number) {
                BigDecimal numberValue = mapToBigDecimal(value);
                boolean isInteger = numberValue.signum() == 0 || numberValue.scale() <= 0 ||
                        numberValue.stripTrailingZeros().scale() <= 0;
                NumberFormat formatter = isInteger
                        ? serializationConfiguration.getIntegerFormatter()
                        : serializationConfiguration.getDecimalFormatter();
                return formatter.format(numberValue);
            } else if (value instanceof String) {
                return (String) value;
            } else {
                return String.valueOf(value);
            }
        }).collect(Collectors.joining(SerializationConfiguration.COMMA + SerializationConfiguration.SPACE));
        outputStream.write(SerializationConfiguration.SPACE.getBytes(serializationConfiguration.getCharset()));
        outputStream.write(
                SerializationConfiguration.ARRAY_START_SIGN.getBytes(serializationConfiguration.getCharset()));
        outputStream.write(SerializationConfiguration.SPACE.getBytes(serializationConfiguration.getCharset()));
        outputStream.write(values.getBytes(serializationConfiguration.getCharset()));
        outputStream.write(SerializationConfiguration.SPACE.getBytes(serializationConfiguration.getCharset()));
        outputStream.write(SerializationConfiguration.ARRAY_END_SIGN.getBytes(serializationConfiguration.getCharset()));
    }

    @Override
    public void endArrayElement() {
        serializationContext.endArrayElement();
    }

    @Override
    public void endComplexElement() {
        serializationContext.endElement();
    }

    @Override
    public void endComplexMapElement() {
    }

    @Override
    public void endSimpleElement() throws IOException {
        serializationContext.endElement();
        outputStream.write(
                SerializationConfiguration.LINE_TERMINATION.getBytes(serializationConfiguration.getCharset()));
    }

    @Override
    public void endSimpleMapElement() {
        serializationContext.endElement();
    }

    private String getStringValue(Object rawPropertyName) {
        if (rawPropertyName instanceof Boolean propertyName) {
            return String.valueOf(propertyName);
        } else if (rawPropertyName instanceof Date propertyName) {
            return serializationConfiguration.getDateFormatter().format(propertyName);
        } else if (rawPropertyName instanceof Number propertyName) {
            BigDecimal testValue = BigDecimal.valueOf(propertyName.doubleValue());
            boolean isInteger =
                    testValue.signum() == 0 || testValue.scale() <= 0 || testValue.stripTrailingZeros().scale() <= 0;
            NumberFormat formatter = isInteger
                    ? serializationConfiguration.getIntegerFormatter()
                    : serializationConfiguration.getDecimalFormatter();
            return formatter.format(propertyName);
        } else if (rawPropertyName instanceof String propertyName) {
            return propertyName;
        } else {
            return String.valueOf(rawPropertyName);
        }
    }

    public boolean isNumeric(String str) {
        ParsePosition pos = new ParsePosition(0);
        serializationConfiguration.getDecimalFormatter().parse(str, pos);
        return str.length() == pos.getIndex();
    }

}
