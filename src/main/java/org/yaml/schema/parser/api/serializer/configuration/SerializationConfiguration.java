package org.yaml.schema.parser.api.serializer.configuration;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DecimalFormat;

public interface SerializationConfiguration {
    String TAB = "\t";
    String COLON = ":";
    String DASH = "-";
    String SPACE = " ";
    String COMMA = ",";
    String QUOTATION_MARK = "\"";
    String ARRAY_START_SIGN = "[";
    String ARRAY_END_SIGN = "]";
    String LINE_TERMINATION = "\n";

    Charset getCharset();

    DateFormat getDateFormatter();

    DecimalFormat getDecimalFormatter();

    DecimalFormat getIntegerFormatter();

    IndentationType getIndentationType();

    int getIndentationSize();

    boolean indentArrayItems();

}
