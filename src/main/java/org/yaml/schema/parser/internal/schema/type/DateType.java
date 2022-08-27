package org.yaml.schema.parser.internal.schema.type;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.type.annotation.SchemaTypeName;
import org.yaml.schema.parser.api.schema.type.mapper.SchemaTypeMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.internal.schema.property.assertion.date.Pattern;
import org.yaml.schema.parser.internal.utils.SchemaPropertyNameDesignator;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SchemaTypeName("date")
@SchemaVersion(SpecVersion.DRAFT_01)
public class DateType extends AbstractType {

    public DateType(String name, Map<String, SchemaProperty> properties) {
        this(SpecVersion.current(), name, properties);
    }

    public DateType(SpecVersion specVersion, String name, Map<String, SchemaProperty> properties) {
        super(specVersion, name, properties);
    }

    public static SchemaTypeMapper<Map<String, Object>> mapper() {
        return (specVersion, name, value, propertyFactory) -> {
            Map<String, SchemaProperty> properties = new HashMap<>();
            for (Map.Entry<String, Object> object : value.entrySet()) {
                properties.put(object.getKey(),
                        propertyFactory.createProperty(SchemaPropertyContext.Type.DATE, object.getKey(),
                                object.getValue()));
            }
            return new DateType(specVersion, name, properties);
        };
    }

    @Override
    public void format(Serializer serializer, Object rawValue) throws IOException {
        if (rawValue != null) {
            if (rawValue instanceof String value) {
                serializer.startSimpleElement(name());
                serializer.writePropertyValue(value);
                serializer.endSimpleElement();
            } else if (rawValue instanceof Date value) {
                Pattern property = getPatternProperty();
                serializer.startSimpleElement(name());
                if (property != null) {
                    DateFormat dateFormat = new SimpleDateFormat(property.value());
                    serializer.writePropertyValue(dateFormat.format(value));
                } else {
                    serializer.writePropertyValue(value);
                }
                serializer.endSimpleElement();
            }
        } else if (isRequired()) {
            serializer.startSimpleElement(name());
            serializer.endSimpleElement();
        }
    }

    private Pattern getPatternProperty() {
        try {
            String propertyName = SchemaPropertyNameDesignator.designatePropertyName(Pattern.class,
                    getUsedSpecVersion());
            return (Pattern) getProperty(propertyName);
        } catch (SchemaPropertyNotExistsInSpecificationException e) {
            return null;
        }
    }

}
