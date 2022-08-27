package org.yaml.schema.parser.api.schema;

import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.api.validator.YamlValidator;
import org.yaml.schema.parser.api.validator.problem.ProblemHandler;
import org.yaml.schema.parser.internal.schema.property.core.Definitions;
import org.yaml.schema.parser.internal.schema.property.core.Properties;
import org.yaml.schema.parser.internal.serializer.SerializerImpl;

import java.io.IOException;

public interface Schema {

    /**
     * Returns the title of this schema, which is supplied by "title" keyword.
     *
     * @return the title of this schema, or {@code null} if not exists.
     */
    default String title() {
        return null;
    }

    /**
     * Returns the description of this schema, which is supplied by "description" keyword.
     *
     * @return the description of this schema, or {@code null} if not exists.
     */
    default String description() {
        return null;
    }

    default Definitions definitions() {
        return null;
    }

    default Properties properties() {
        return null;
    }

    void serialize(Serializer serializer) throws IOException;

    void test(ProblemHandler problemHandler, Object yaml);

    void test(YamlValidator validator, Object yaml);

    void format(Serializer serializer, Object yaml) throws IOException;

}
