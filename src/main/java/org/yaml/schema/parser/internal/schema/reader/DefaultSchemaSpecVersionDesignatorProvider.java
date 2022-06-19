package org.yaml.schema.parser.internal.schema.reader;

import org.yaml.schema.parser.api.schema.reader.SchemaSpecVersionDesignator;
import org.yaml.schema.parser.api.schema.reader.SchemaSpecVersionDesignatorProvider;

public class DefaultSchemaSpecVersionDesignatorProvider implements SchemaSpecVersionDesignatorProvider {

    @Override
    public SchemaSpecVersionDesignator createDesignator() {
        return new DefaultSchemaSpecVersionDesignator();
    }

}
