package org.yaml.schema.parser.api.serializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

public interface Serializer {

    SerializationContext createInitialContext();

    void startSimpleElement(String propertyName) throws IOException;

    void startComplexProperty(String propertyName) throws IOException;

    void writePropertyValue(BigDecimal value) throws IOException;

    void writePropertyValue(Boolean value) throws IOException;

    void writePropertyValue(Date value) throws IOException;

    void writePropertyValue(Double value) throws IOException;

    void writePropertyValue(Integer value) throws IOException;

    void writePropertyValue(Long value) throws IOException;

    void writePropertyValue(String value) throws IOException;

    void endComplexElement() throws IOException;

    void endSimpleElement() throws IOException;

}
