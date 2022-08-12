package org.yaml.schema.parser.internal.schema.property.assertion.array;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.internal.utils.SchemaPropertyNameDesignator;

import java.util.Map;

@SchemaPropertyContext(SchemaPropertyContext.Type.ARRAY)
@SchemaPropertyName("itemsType")
@SchemaVersion(SpecVersion.DRAFT_01)
public class ItemsType extends AbstractArrayItemsAssertion {

    public ItemsType(SchemaProperty value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public ItemsType(SpecVersion specVersion, SchemaProperty value)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<Map<String, Object>> mapper() {
        return (specVersion, value, propertyFactory) -> propertyFactory.createType(getSchemaPropertyName(specVersion),
                value);
    }

    private static String getSchemaPropertyName(SpecVersion specVersion)
            throws SchemaPropertyNotExistsInSpecificationException {
        return SchemaPropertyNameDesignator.designatePropertyName(ItemsType.class, specVersion);
    }

}
