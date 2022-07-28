package org.yaml.schema.parser;

import org.yaml.schema.parser.api.schema.Schema;
import org.yaml.schema.parser.api.schema.reader.YamlSchemaReader;
import org.yaml.schema.parser.api.schema.reader.YamlSchemaReaderFactory;
import org.yaml.schema.parser.api.schema.reader.YamlSchemaReaderFactoryProvider;
import org.yaml.schema.parser.internal.serializer.SerializerImpl;

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
            // System.out.println(schema);
            schema.serialize(new SerializerImpl());
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
                        required: true
                        seq: 1
                        itemsType:
                          type: string
                      labels:
                        type: map
                        required: false
                        seq: 2
                        itemsKey:
                          type: string
                        itemsType:
                          type: string
                  job:
                    type: object
                    properties:
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
                      scrape_interval:
                        type: string
                        enum: [ second, minute, hour ]
                      one_time_job:
                        type: boolean
                        const: false

                properties:
                  global:
                    type: object
                    properties:
                      scrape_interval:
                        type: string
                      evaluation_interval:
                        type: string
                      external_labels:
                        type: map
                        itemsKey:
                          type: string
                        itemsType:
                          type: string
                  rule_files:
                    type: array
                    itemsType:
                      type: string
                """;
    }

}
