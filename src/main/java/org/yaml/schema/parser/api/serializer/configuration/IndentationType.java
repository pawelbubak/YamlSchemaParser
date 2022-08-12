package org.yaml.schema.parser.api.serializer.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IndentationType {
    SPACE(SerializationConfiguration.SPACE), TAB(SerializationConfiguration.TAB);

    private final String value;

}
