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
import java.util.Date;

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
    public void startSimpleElement(String propertyName) throws IOException {
        serializationContext.startElement();
        outputStream.write(serializationContext.getIndentation().getBytes(serializationConfiguration.getCharset()));
        outputStream.write(propertyName.getBytes(serializationConfiguration.getCharset()));
        outputStream.write(SerializationConfiguration.COLON.getBytes(serializationConfiguration.getCharset()));
    }

    @Override
    public void startComplexProperty(String propertyName) throws IOException {
        serializationContext.startElement();
        outputStream.write(serializationContext.getIndentation().getBytes(serializationConfiguration.getCharset()));
        outputStream.write(propertyName.getBytes(serializationConfiguration.getCharset()));
        outputStream.write(SerializationConfiguration.COLON.getBytes(serializationConfiguration.getCharset()));
        outputStream.write(
                SerializationConfiguration.LINE_TERMINATION.getBytes(serializationConfiguration.getCharset()));
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
    public void writePropertyValue(String value) throws IOException {
        outputStream.write(SerializationConfiguration.SPACE.getBytes(serializationConfiguration.getCharset()));
        outputStream.write(value.getBytes(serializationConfiguration.getCharset()));
    }

    @Override
    public void endComplexElement() {
        serializationContext.endElement();
    }

    @Override
    public void endSimpleElement() throws IOException {
        serializationContext.endElement();
        outputStream.write(
                SerializationConfiguration.LINE_TERMINATION.getBytes(serializationConfiguration.getCharset()));
    }

}
