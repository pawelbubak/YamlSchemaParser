package org.yaml.schema.parser.internal.schema.property;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.internal.utils.SchemaPropertyNameDesignator;

public abstract class AbstractSchemaProperty implements SchemaProperty {
    /**
     * The version of schema specification.
     */
    private final SpecVersion specVersion;
    /**
     * The name of this property.
     */
    private final String name;

    public AbstractSchemaProperty(SpecVersion specVersion) throws SchemaPropertyNotExistsInSpecificationException {
        this.specVersion = specVersion;
        this.name = designateOwnName(specVersion);
    }

    public AbstractSchemaProperty(SpecVersion specVersion, String name) {
        this.specVersion = specVersion;
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public int sequenceNumber() {
        return Integer.MAX_VALUE;
    }

    protected SpecVersion getUsedSpecVersion() {
        return specVersion;
    }

    private String designateOwnName(SpecVersion specVersion) throws SchemaPropertyNotExistsInSpecificationException {
        return SchemaPropertyNameDesignator.designatePropertyName(getClass(), specVersion);
    }

    @Override
    public void serialize(Serializer serializer) {
        System.out.println(name);
    }
}
