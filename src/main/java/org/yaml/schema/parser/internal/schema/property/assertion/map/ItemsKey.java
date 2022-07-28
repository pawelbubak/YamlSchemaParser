package org.yaml.schema.parser.internal.schema.property.assertion.map;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.internal.utils.SchemaPropertyNameDesignator;

import java.util.Map;

@SchemaPropertyContext(SchemaPropertyContext.Type.MAP)
@SchemaPropertyName("itemsKey")
@SchemaVersion(SpecVersion.DRAFT_01)
public class ItemsKey extends AbstractMapAssertion<SchemaProperty> {

    public ItemsKey(SchemaProperty value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public ItemsKey(SpecVersion specVersion, SchemaProperty value)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<Map<String, Object>> mapper() {
        return (specVersion, value, propertyFactory) -> new ItemsKey(specVersion,
                propertyFactory.createType(getSchemaPropertyName(specVersion), value));
    }

    private static String getSchemaPropertyName(SpecVersion specVersion)
            throws SchemaPropertyNotExistsInSpecificationException {
        return SchemaPropertyNameDesignator.designatePropertyName(ItemsKey.class, specVersion);
    }

}
