package org.yaml.schema.parser.api.schema.type.designator;

import org.yaml.schema.parser.api.exception.SchemaTypeDesignationException;
import org.yaml.schema.parser.api.schema.version.SpecVersion;

public interface SchemaTypeDesignator {

    String getType(Object value, SpecVersion specVersion) throws SchemaTypeDesignationException;

}
