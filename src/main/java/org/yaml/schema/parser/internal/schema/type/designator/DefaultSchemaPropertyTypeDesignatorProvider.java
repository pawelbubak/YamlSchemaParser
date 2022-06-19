package org.yaml.schema.parser.internal.schema.type.designator;

import org.yaml.schema.parser.api.schema.type.designator.SchemaTypeDesignator;
import org.yaml.schema.parser.api.schema.type.designator.SchemaPropertyTypeDesignatorProvider;

public class DefaultSchemaPropertyTypeDesignatorProvider implements SchemaPropertyTypeDesignatorProvider {

    @Override
    public SchemaTypeDesignator createDesignator() {
        return new DefaultSchemaTypeDesignator();
    }

}
