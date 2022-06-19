package org.yaml.schema.parser.api.schema.type.mapper;

import org.yaml.schema.parser.api.schema.version.SpecVersion;

public interface SchemaTypeMappersLoader {

    SchemaTypeMappersHandler loadMappers(SpecVersion specVersion);

}
