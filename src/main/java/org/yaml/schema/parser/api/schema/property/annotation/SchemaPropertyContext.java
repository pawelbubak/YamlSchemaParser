package org.yaml.schema.parser.api.schema.property.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Inherited
@Target(TYPE)
@Retention(RUNTIME)
public @interface SchemaPropertyContext {

    Type value() default Type.DEFAULT;

    enum Type {
        ARRAY, BOOLEAN, DATE, DEFAULT, NUMBER, OBJECT, STRING
    }

}
