package org.yaml.schema.parser.api.schema;

import org.yaml.schema.parser.api.schema.version.SpecVersion;

import java.net.URI;

public interface YamlSchema extends Schema {

    /**
     * Returns the identifier of this schema, which is supplied by "id" keyword.
     *
     * @return the identifier of this schema, or {@code null} if not exists.
     */
    default URI id() {
        return null;
    }

    /**
     * Returns the version identifier of this schema, which is supplied by "version" keyword.
     *
     * @return the version identifier of this schema, or {@code null} if not exists.
     */
    default SpecVersion version() {
        return null;
    }

}
