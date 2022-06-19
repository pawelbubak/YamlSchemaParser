package org.yaml.schema.parser.api.schema.property.mapper;

import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.property.factory.SchemaPropertyFactory;
import org.yaml.schema.parser.api.schema.version.SpecVersion;

/**
 * The mapper of schema property.
 */
public interface SchemaPropertyMapper<T> {

    /**
     * Returns the schema property.
     *
     * @return the schema property, never be {@code null}.
     */
    SchemaProperty map(SpecVersion specVersion, T value, SchemaPropertyFactory propertyFactory) throws Exception;

}
