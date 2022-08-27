package org.yaml.schema.parser.api.serializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface Serializer {

    SerializationContext createInitialContext();

    void setContext(SerializationContext.Context context);

    void startArrayElement() throws IOException;

    void startComplexElement(Object rawPropertyName) throws IOException;

    void startComplexElement(String propertyName) throws IOException;

    void startComplexMapElement(Object rawPropertyName) throws IOException;

    void startSimpleElement(Object rawPropertyName) throws IOException;

    void startSimpleElement(String propertyName) throws IOException;

    void startSimpleMapElement(Object rawPropertyName) throws IOException;

    void writeEmptyObject() throws IOException;

    void writePropertyValue(BigDecimal value) throws IOException;

    void writePropertyValue(Boolean value) throws IOException;

    void writePropertyValue(Date value) throws IOException;

    void writePropertyValue(Double value) throws IOException;

    void writePropertyValue(Integer value) throws IOException;

    void writePropertyValue(Long value) throws IOException;

    void writePropertyValue(Number value) throws IOException;

    void writePropertyValue(String value) throws IOException;

    void writePropertyValue(List<?> value) throws IOException;

    void endArrayElement() throws IOException;

    void endComplexElement() throws IOException;

    void endComplexMapElement() throws IOException;

    void endSimpleElement() throws IOException;

    void endSimpleMapElement() throws IOException;

}
