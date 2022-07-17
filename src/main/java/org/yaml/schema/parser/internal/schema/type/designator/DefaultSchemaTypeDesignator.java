package org.yaml.schema.parser.internal.schema.type.designator;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.exception.SchemaTypeDesignationException;
import org.yaml.schema.parser.api.schema.type.designator.SchemaTypeDesignator;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.internal.schema.property.core.Type;
import org.yaml.schema.parser.internal.utils.SchemaPropertyNameDesignator;

import java.util.Map;

public class DefaultSchemaTypeDesignator implements SchemaTypeDesignator {

    @Override
    public String getType(Object value, SpecVersion specVersion) throws SchemaTypeDesignationException {
        if (value instanceof Map) {
            try {
                String typePropertyName = getTypePropertyName(specVersion);
                return (String) ((Map<String, Object>) value).get(typePropertyName);
            } catch (SchemaPropertyNotExistsInSpecificationException e) {
                throw new SchemaTypeDesignationException(e);
            }
        }
        throw new SchemaTypeDesignationException();
    }

    private String getTypePropertyName(SpecVersion specVersion) throws SchemaPropertyNotExistsInSpecificationException {
        return SchemaPropertyNameDesignator.designatePropertyName(Type.class, specVersion);
    }

}
