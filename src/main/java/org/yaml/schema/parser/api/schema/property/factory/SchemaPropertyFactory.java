package org.yaml.schema.parser.api.schema.property.factory;

import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;

/**
 * The factory of schema properties.
 */
public interface SchemaPropertyFactory {

    /**
     * Creates the schema property in {@code SchemaPropertyContext.Type.DEFAULT} context.
     *
     * @param propertyName name of schema property.
     * @param value        value of schema property.
     * @return created schema property.
     */
    default SchemaProperty createProperty(String propertyName, Object value) throws Exception {
        return createProperty(SchemaPropertyContext.Type.DEFAULT, propertyName, value);
    }

    /**
     * Creates the schema property in specified context.
     *
     * @param propertyContext context in which the schema property is created.
     * @param propertyName    name of schema property.
     * @param value           value of schema property.
     * @return created schema property.
     */
    SchemaProperty createProperty(SchemaPropertyContext.Type propertyContext, String propertyName, Object value)
            throws Exception;

    /**
     * Creates the schema type.
     *
     * @param typeName name of schema type.
     * @param value    value of schema type.
     * @return created schema type.
     */
    SchemaProperty createType(String typeName, Object value) throws Exception;

}
