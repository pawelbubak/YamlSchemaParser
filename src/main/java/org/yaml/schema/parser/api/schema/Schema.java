package org.yaml.schema.parser.api.schema;

import org.yaml.schema.parser.api.serializer.Serializer;

import java.util.stream.Stream;

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

    /**
     * Checks whether this schema has any subschemas or not.
     *
     * @return {@code true} if this schema contains any subschemas, {@code false} otherwise.
     */
    default boolean hasSubschemas() {
        return false;
    }

    /**
     * Returns all the subschemas contained in this schema.
     *
     * @return the stream of subschemas contained in this schema.
     */
    default Stream<Schema> getSubschemas() {
        return Stream.empty();
    }

    void serialize(Serializer serializer);

}
