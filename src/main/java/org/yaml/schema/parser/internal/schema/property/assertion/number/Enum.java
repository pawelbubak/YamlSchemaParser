package org.yaml.schema.parser.internal.schema.property.assertion.number;

import org.yaml.schema.parser.api.exception.SchemaPropertyNotExistsInSpecificationException;
import org.yaml.schema.parser.api.schema.annotation.SchemaVersion;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyContext;
import org.yaml.schema.parser.api.schema.property.annotation.SchemaPropertyName;
import org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMapper;
import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.serializer.Serializer;
import org.yaml.schema.parser.api.validator.problem.AbstractMessage;
import org.yaml.schema.parser.internal.schema.property.AbstractSchemaSimpleProperty;
import org.yaml.schema.parser.internal.schema.property.assertion.MapperUtils;
import org.yaml.schema.parser.internal.validator.problem.ValidationMessage;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static org.yaml.schema.parser.internal.schema.property.assertion.MapperUtils.mapToBigDecimal;

@SchemaPropertyContext(SchemaPropertyContext.Type.NUMBER)
@SchemaPropertyName("enum")
@SchemaVersion(SpecVersion.DRAFT_01)
public class Enum extends AbstractSchemaSimpleProperty<List<BigDecimal>> {

    public Enum(List<BigDecimal> value) throws SchemaPropertyNotExistsInSpecificationException {
        this(SpecVersion.current(), value);
    }

    public Enum(SpecVersion specVersion, List<BigDecimal> value)
            throws SchemaPropertyNotExistsInSpecificationException {
        super(specVersion, value);
    }

    public static SchemaPropertyMapper<List<Number>> mapper() {
        return (specVersion, value, propertyFactory) -> {
            List<BigDecimal> values = value.stream().map(MapperUtils::mapToBigDecimal).toList();
            return new Enum(specVersion, values);
        };
    }

    @Override
    protected void serializeValue(Serializer serializer) throws IOException {
        serializer.writePropertyValue(value().toString());
    }

    @Override
    public boolean testValue(Object rawValue) {
        if (rawValue instanceof Number) {
            BigDecimal value = mapToBigDecimal(rawValue);
            for (BigDecimal expected : value()) {
                if (expected.compareTo(value) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected AbstractMessage.Key getProblemMessageCode() {
        return ValidationMessage.Key.ENUM_VALIDATION_PROBLEM;
    }

    @Override
    protected Map<String, Object> getProblemMessageArguments() {
        DecimalFormat decimalFormatter = new DecimalFormat("0.###", new DecimalFormatSymbols(Locale.US));
        String expectedValues = value().stream().map(decimalFormatter::format).collect(Collectors.joining(","));
        return Collections.singletonMap("expected", expectedValues);
    }

}
