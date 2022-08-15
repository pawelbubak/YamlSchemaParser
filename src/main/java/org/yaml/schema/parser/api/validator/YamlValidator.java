package org.yaml.schema.parser.api.validator;

import org.yaml.schema.parser.api.schema.Schema;

import java.util.Map;

public interface YamlValidator {

    void validate(Schema schema, Map<String, Object> yaml);

}
