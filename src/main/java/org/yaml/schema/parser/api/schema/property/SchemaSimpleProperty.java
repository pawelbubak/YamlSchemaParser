package org.yaml.schema.parser.api.schema.property;

public interface SchemaSimpleProperty<T> extends SchemaProperty {

    /**
     * Returns the value of this property.
     *
     * @return the value of this property.
     */
    T value();

}
