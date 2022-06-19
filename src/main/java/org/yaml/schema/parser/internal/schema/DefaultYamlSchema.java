package org.yaml.schema.parser.internal.schema;

import org.yaml.schema.parser.api.schema.version.SpecVersion;
import org.yaml.schema.parser.api.schema.YamlSchema;
import org.yaml.schema.parser.api.schema.property.SchemaProperty;
import org.yaml.schema.parser.internal.schema.property.declaration.Declaration;

import java.net.URI;
import java.util.Map;

public class DefaultYamlSchema extends AbstractSchema implements YamlSchema {

    public DefaultYamlSchema(Map<String, SchemaProperty> schemaProperties) {
        this(SpecVersion.current(), schemaProperties);
    }

    public DefaultYamlSchema(SpecVersion specVersion, Map<String, SchemaProperty> schemaProperties) {
        super(specVersion, schemaProperties);
    }

    @Override
    public URI id() {
        Declaration declaration = getDeclaration();
        return declaration != null ? declaration.id() : null;
    }

    @Override
    public SpecVersion version() {
        Declaration declaration = getDeclaration();
        return declaration != null ? declaration.version() : null;
    }

    private Declaration getDeclaration() {
        String declarationPropertyName = getPropertyName(Declaration.class);
        if (!containsProperty(declarationPropertyName)) {
            return null;
        }
        return (Declaration) getProperty(declarationPropertyName);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (SchemaProperty schemaProperty: this.values()) {
            stringBuilder.append(String.format("%s: %s", schemaProperty.name(), schemaProperty));
        }
        return stringBuilder.toString();
    }
}
