package org.yaml.schema.parser.api.schema.property.mapper;

import org.yaml.schema.parser.api.schema.version.SpecVersion;

public interface SchemaPropertyMappersLoader {

    SchemaPropertyMappersHandler loadMappers(SpecVersion specVersion);

}
