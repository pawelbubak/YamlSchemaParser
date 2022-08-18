package org.yaml.schema.parser.internal.schema.type;

import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.validator.YamlValidator;
import org.yaml.schema.parser.internal.schema.property.AbstractSchemaComplexProperty;

import java.util.List;
import java.util.Map;

public abstract class AbstractType extends AbstractSchemaComplexProperty {

    public AbstractType(SpecVersion specVersion, String name, Map<String, SchemaProperty> properties) {
        super(specVersion, name, properties);
    }

    @Override
    public void test(YamlValidator validator, Object value) {
        List<SchemaProperty> sortedProperties = getPropertiesSortedBySequenceNumberAndName();
        for (SchemaProperty property : sortedProperties) {
            property.test(validator, value);
        }
    }

}
