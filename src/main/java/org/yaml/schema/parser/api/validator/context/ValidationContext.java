package org.yaml.schema.parser.api.validator.context;

import java.math.BigDecimal;
import java.util.Date;

public interface ValidationContext {

    String current();

    String pointer();

    void push(BigDecimal value);

    void push(Boolean value);

    void push(Date value);

    void push(Double value);

    void push(Integer value);

    void push(Long value);

    void push(String value);

    void pop();

}
