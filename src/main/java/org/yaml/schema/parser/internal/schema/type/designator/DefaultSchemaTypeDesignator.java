package org.yaml.schema.parser.internal.schema.type.designator;

import org.yaml.schema.parser.api.schema.type.designator.SchemaTypeDesignator;

public class DefaultSchemaTypeDesignator implements SchemaTypeDesignator {

    @Override
    public String getType(Object value) {
        return "string";
    }

}
