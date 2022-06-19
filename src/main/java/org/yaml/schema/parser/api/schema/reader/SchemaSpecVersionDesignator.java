package org.yaml.schema.parser.api.schema.reader;

import org.yaml.schema.parser.api.schema.version.SpecVersion;

import java.util.Map;

public interface SchemaSpecVersionDesignator {

    SpecVersion getSpecVersion(Map<String, Object> rawSchema);

}
