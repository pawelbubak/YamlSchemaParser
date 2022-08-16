package org.yaml.schema.parser;

import org.yaml.schema.parser.api.schema.Schema;
import org.yaml.schema.parser.api.schema.reader.YamlSchemaReader;
import org.yaml.schema.parser.api.schema.reader.YamlSchemaReaderFactory;
import org.yaml.schema.parser.api.schema.reader.YamlSchemaReaderFactoryProvider;
import org.yaml.schema.parser.api.validator.problem.Problem;
import org.yaml.schema.parser.api.validator.problem.ProblemHandler;
import org.yaml.schema.parser.api.validator.problem.ProblemHandlerProvider;
import org.yaml.schema.parser.internal.serializer.SerializerImpl;
import org.yaml.schema.parser.internal.validator.problem.DefaultProblem;
import org.yaml.schema.parser.internal.validator.problem.ValidationMessage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class Test {

    public static void main(String[] args) throws Exception {
        Problem problem = DefaultProblem.builder()
                .pointer("/xxx")
                .message(ValidationMessage.builder().bundleKey(ValidationMessage.Key.TEST_MESSAGE).build())
                .build();

        ProblemHandler problemHandler = ProblemHandlerProvider.provider()
                .createCollectProblemHandler(System.out::println);
        problemHandler.handleProblem(problem);

        problemHandler = ProblemHandlerProvider.provider().createThrowingProblemHandler();
//        problemHandler.handleProblem(problem);

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
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            schema.serialize(new SerializerImpl(byteArrayOutputStream));
            System.out.println(byteArrayOutputStream.toString(StandardCharsets.UTF_8));
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

}
