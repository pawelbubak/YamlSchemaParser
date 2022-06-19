package org.yaml.schema.parser.internal.schema.property.type;

import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.internal.schema.property.AbstractSchemaComplexProperty;

import java.util.Map;

@SchemaVersion(SpecVersion.DRAFT_01)
public class IntegerType extends AbstractSchemaComplexProperty {

    public IntegerType(String name, Map<String, SchemaProperty> properties) {
        this(SpecVersion.current(), name, properties);
    }

    public IntegerType(SpecVersion specVersion, String name, Map<String, SchemaProperty> properties) {
        super(specVersion, name, properties);
    }

}
