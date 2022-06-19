package org.yaml.schema.parser.api.schema.annotation;

import org.yaml.schema.parser.api.schema.version.SpecVersion;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(TYPE)
@Repeatable(SchemaVersions.class)
public @interface SchemaVersion {

    /**
     * Returns the supported specification version.
     *
     * @return the supported specification version.
     */
    SpecVersion value();

    /**
     * Returns the property name in current specification.
     *
     * @return the property name in current specification.
     */
    String name() default "";

}
