package org.yaml.schema.parser.internal.schema.type;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.validator.YamlValidator;
import org.yaml.schema.parser.internal.schema.property.AbstractSchemaComplexProperty;
import org.yaml.schema.parser.internal.schema.property.assertion.Required;
import org.yaml.schema.parser.internal.utils.SchemaPropertyNameDesignator;

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
            if (value != null || isRequired()) {
                property.test(validator, value);
            }
        }
    }

    protected boolean isRequired() {
        try {
            String propertyName = SchemaPropertyNameDesignator.designatePropertyName(Required.class,
                    getUsedSpecVersion());
            Required property = (Required) getProperty(propertyName);
            return property != null && property.value();
        } catch (SchemaPropertyNotExistsInSpecificationException e) {
            return false;
        }
    }

}
