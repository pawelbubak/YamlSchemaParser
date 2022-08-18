package org.yaml.schema.parser;

import org.yaml.schema.parser.api.reader.YamlReader;
import org.yaml.schema.parser.api.reader.YamlReaderProvider;
import org.yaml.schema.parser.api.schema.Schema;
import org.yaml.schema.parser.api.schema.reader.YamlSchemaReader;
import org.yaml.schema.parser.api.validator.problem.ProblemHandler;
import org.yaml.schema.parser.api.validator.problem.ProblemHandlerProvider;
import org.yaml.schema.parser.internal.serializer.SerializerImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Test {

    public static void main(String[] args) throws Exception {
        test();
    }

    private static void test() throws Exception {
        String rawSchema = getWorkerSchema();
        String rawYaml = getWorkerYaml();
        Object yaml = getYaml(rawYaml);

        try (InputStream inputStream = new ByteArrayInputStream(rawSchema.getBytes(StandardCharsets.UTF_8));
             YamlSchemaReader schemaReader = YamlSchemaReader.createDefaultReader(inputStream)) {
            Schema schema = schemaReader.read();
            ProblemHandler problemHandler = ProblemHandlerProvider.provider()
                                                                  .createCollectProblemHandler(System.out::println);
            schema.test(problemHandler, yaml);
            // System.out.println(schema);
            serializeYamlSchema(schema);
        }
    }

    private static Object getYaml(String rawYaml) throws IOException {
        YamlReader yamlReader = YamlReaderProvider.provider().createReader();
        try (InputStream inputStream = new ByteArrayInputStream(rawYaml.getBytes(StandardCharsets.UTF_8))) {
            return yamlReader.read(inputStream);
        }
    }

    private static void serializeYamlSchema(Schema schema) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        schema.serialize(new SerializerImpl(byteArrayOutputStream));
        System.out.println(byteArrayOutputStream.toString(StandardCharsets.UTF_8));
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
                        minDate: 2023-05-20T02:00:00.000+02:00
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

    private static String getWorkerSchema() {
        return """
                $schema:
                  id: "https://example.com/schemas/workers.yaml"
                  version: "1.0"
                title: Docker-compose schema
                description: Example docker-compose configuration schema

                properties:
                  id:
                    type: integer
                    required: true
                    seq: 1
                    exclusiveMin: 0
                  nazwisko:
                    type: string
                    required: true
                    seq: 2
                  etat:
                    type: string
                    required: true
                    seq: 3
                  idSzefa:
                    type: integer
                    seq: 4
                  zatrudniony:
                    type: date
                    seq: 5
                  placaPodstawowa:
                    type: number
                    seq: 6
                    exclusiveMin: 0
                  placaDodatkowa:
                    type: number
                    seq: 7
                    min: 0
                  idZespolu:
                    type: integer
                    required: true
                    seq: 8
                    enum: [ 10, 20, 30, 40 ]
                  students:
                    type: array
                    itemsType:
                      type: integer
                      exclusiveMin: 0
                """;
    }

    private static String getWorkerYaml() {
        return """
                id: 100
                nazwisko: "WĘGLARZ"
                etat: "DYREKTOR"
                idSzefa: null
                zatrudniony: 1968-01-01
                placaPodstawowa: 1730
                placaDodatkowa: 0
                idZespolu: 0,
                students:
                  - 0
                  - 132197
                  - 132260
                  - 132348
                """;
    }

}
