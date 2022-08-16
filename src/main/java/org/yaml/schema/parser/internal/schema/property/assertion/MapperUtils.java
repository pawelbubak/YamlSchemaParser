package org.yaml.schema.parser.internal.schema.property.assertion;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MapperUtils {

    public static BigDecimal mapToBigDecimal(Object rawValue) {
        BigDecimal value = getBigDecimal(rawValue);
        if (value == null) {
            throw new IllegalStateException("Number value cannot be null.");
        }
        return value;
    }

    private static BigDecimal getBigDecimal(Object rawValue) {
        BigDecimal value = null;
        if (rawValue != null) {
            if (rawValue instanceof BigDecimal) {
                value = (BigDecimal) rawValue;
            } else if (rawValue instanceof String) {
                value = new BigDecimal((String) rawValue);
            } else if (rawValue instanceof BigInteger) {
                value = new BigDecimal((BigInteger) rawValue);
            } else if (rawValue instanceof Number) {
                value = BigDecimal.valueOf(((Number) rawValue).doubleValue());
            }
        }
        return value;
    }

}
