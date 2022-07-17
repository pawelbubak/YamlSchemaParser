package org.yaml.schema.parser;

import org.yaml.schema.parser.api.schema.Schema;
import org.yaml.schema.parser.api.schema.reader.YamlSchemaReader;
import org.yaml.schema.parser.api.schema.reader.YamlSchemaReaderFactory;
import org.yaml.schema.parser.api.schema.reader.YamlSchemaReaderFactoryProvider;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class Test {

    public static void main(String[] args) throws Exception {
        test();
    }

    private static void test() throws Exception {
        String rawSchema = getStringSchema();

        YamlSchemaReaderFactory schemaReaderFactory = YamlSchemaReaderFactoryProvider.provider().createFactory();

        YamlSchemaReader schemaReader = schemaReaderFactory.createSchemaReader(
                new ByteArrayInputStream(rawSchema.getBytes(StandardCharsets.UTF_8)));

        try (schemaReader) {
            Schema schema = schemaReader.read();
            System.out.println(schema);
        }
    }

    private static String getStringSchema() {
        return """
                $schema:
                  id: "https://example.com/schemas/example.yaml"
                  version: "1.0"
                title: Docker-compose schema
                description: Example docker-compose configuration schema
                
                definitions:
                  job-target:
                    type: object
                    properties:
                      targets:
                        type: array
                        itemsType:
                          type: string
                  job_id:
                    type: integer
                    exclusiveMin: 0
                  job_name:
                    type: string
                    minLength: 5
                    maxLength: 20
                    pattern: "[a-zA-Z]*"
                  job_start:
                    type: date
                    minDate: 2022-17-20
                  one_time_job:
                    type: boolean
                """;
    }

}
