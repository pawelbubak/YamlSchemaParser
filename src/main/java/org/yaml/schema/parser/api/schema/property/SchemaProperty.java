package org.yaml.schema.parser.api.schema.property;

import org.yaml.schema.parser.api.schema.Schema;

import java.util.stream.Stream;

public interface SchemaProperty {

    /**
     * Returns the name of this property.
     *
     * @return the name of this property, never be {@code null}.
     */
    String name();

    /**
     * Checks whether this property has any subschemas or not.
     *
     * @return {@code true} if this property contains any subschemas, {@code false} otherwise.
     */
    default boolean hasSubschemas() {
        return false;
    }

    /**
     * Returns all the subschemas owned by this property.
     *
     * @return the stream of subschemas owned by this property.
     */
    default Stream<Schema> getSubschemas() {
        return Stream.empty();
    }

}
