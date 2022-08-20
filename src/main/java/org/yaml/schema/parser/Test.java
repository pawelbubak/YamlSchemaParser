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
        String rawSchema = getWorkersSchema();
//        String rawSchema = getStringSchema();
        String rawYaml = getWorkersYaml();
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
                        sequence: 1
                        itemsType:
                          type: string
                      labels:
                        type: map
                        required: false
                        sequence: 2
                        itemsKey:
                          type: string
                        itemsType:
                          type: string
                  job:
                    type: object
                    properties:
                      job_id:
                        type: integer
                        exclusiveMinimum: 0
                      job_name:
                        type: string
                        minimumLength: 5
                        maximumLength: 20
                        pattern: "[a-zA-Z]*"
                      job_start:
                        type: date
                        minimumDate: 2022-06-20T02:00:00.000+02:00
                      scrape_interval:
                        type: string
                        enum: [ second, minute, hour ]
                      one_time_job:
                        type: boolean
                        constant: false

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
                    sequence: 1
                    exclusiveMinimum: 0
                  nazwisko:
                    type: string
                    required: true
                    sequence: 2
                  etat:
                    type: string
                    required: true
                    sequence: 3
                  idSzefa:
                    type: integer
                    sequence: 4
                  zatrudniony:
                    type: date
                    sequence: 5
                  placaPodstawowa:
                    type: number
                    sequence: 6
                    exclusiveMinimum: 0
                  placaDodatkowa:
                    type: number
                    sequence: 7
                    minimum: 0
                  idZespolu:
                    type: integer
                    required: true
                    sequence: 8
                    enum: [ 10, 20, 30, 40 ]
                  students:
                    type: array
                    itemsType:
                      type: integer
                      exclusiveMinimum: 0
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

    private static String getWorkersSchema() {
        return """
                $schema:
                  id: 'https://example.com/schemas/pracownicy.yaml'
                  version: '1.0'
                                
                title: Pracownicy
                description: Opis dokumntu zawierajacego liste pracownikow oraz zespolow w ktorych pracuja
                                
                definitions:
                  pracownik: &pracownik
                    type: object
                    properties:
                      id:
                        type: integer
                        required: true
                        sequence: 1
                        exclusiveMinimum: 0
                      nazwisko:
                        type: string
                        required: true
                        sequence: 2
                      etat:
                        type: string
                        required: true
                        sequence: 3
                        enum: [ADIUNKT, ASYSTENT, DYREKTOR, PROFESOR, SEKRETARKA, STAZYSTA]
                      idSzefa:
                        type: integer
                        sequence: 4
                      zatrudniony:
                        type: date
                        sequence: 5
                      placaPodstawowa:
                        type: number
                        sequence: 6
                        exclusiveMinimum: 0
                      placaDodatkowa:
                        type: number
                        sequence: 7
                        minimum: 0
                      idZespolu:
                        type: integer
                        required: true
                        sequence: 8
                        enum: [10, 20, 30, 40, 50]
                  zespol: &zespol
                    type: object
                    properties:
                      id:
                        type: integer
                        required: true
                        sequence: 1
                        exclusiveMinimum: 0
                      nazwa:
                        type: string
                        required: true
                        sequence: 2
                      adres:
                        type: string
                        required: true
                        sequence: 3
                                
                properties:
                  pracownicy:
                    type: array
                    required: true
                    itemsType: *pracownik
                  zespoly:
                    type: map
                    required: true
                    itemsKey:
                      type: number
                    itemsType: *zespol
                                
                """;
    }

    private static String getWorkersYaml() {
        return """
                pracownicy:
                  - id: 100
                    nazwisko: WEGLARZ
                    etat: DYREKTOR
                    idSzefa: null
                    zatrudniony: 1968-01-01
                    placaPodstawowa: 1730.00
                    placaDodatkowa: 420.50
                    idZespolu: 10
                  - id: 110
                    nazwisko: BLAZEWICZ
                    etat: PROFESOR
                    idSzefa: 100
                    zatrudniony: 1973-05-01
                    placaPodstawowa: 1350.00
                    placaDodatkowa: 210.00
                    idZespolu: 40
                  - id: 120
                    nazwisko: SLOWINSKI
                    etat: PROFESOR
                    idSzefa: 100
                    zatrudniony: 1977-09-01
                    placaPodstawowa: 1070.00
                    placaDodatkowa: null
                    idZespolu: 30
                  - id: 130
                    nazwisko: BRZEZINSKI
                    etat: PROFESOR
                    zatrudniony: 1968-07-01
                    idSzefa: 100
                    placaPodstawowa: 960.00
                    placaDodatkowa: null
                    idZespolu: 20
                  - id: 140
                    nazwisko: MORZY
                    etat: PROFESOR
                    idSzefa: 130
                    zatrudniony: 1975-09-15
                    placaPodstawowa: 830.00
                    placaDodatkowa: 105.00
                    idZespolu: 20
                  - id: 150
                    nazwisko: KROLIKOWSKI
                    etat: ADIUNKT
                    idSzefa: 130
                    zatrudniony: 1977-09-01
                    placaPodstawowa: 645.50
                    placaDodatkowa: null
                    idZespolu: 20
                  - id: 160
                    nazwisko: KOSZLAJDA
                    etat: ADIUNKT
                    idSzefa: 130
                    zatrudniony: 1985-03-01
                    placaPodstawowa: 590.00
                    placaDodatkowa: null
                    idZespolu: 20
                  - id: 170
                    nazwisko: JEZIERSKI
                    etat: ASYSTENT
                    idSzefa: 130
                    zatrudniony: 1992-10-01
                    placaPodstawowa: 439.70
                    placaDodatkowa: 80.50
                    idZespolu: 20
                  - id: 190
                    nazwisko: MATYSIAK
                    etat: ASYSTENT
                    idSzefa: 140
                    zatrudniony: 1993-09-01
                    placaPodstawowa: 371.00
                    placaDodatkowa: null
                    idZespolu: 20
                  - id: 180
                    nazwisko: MAREK
                    etat: SEKRETARKA
                    idSzefa: 100
                    zatrudniony: 1985-02-20
                    placaPodstawowa: 410.20
                    placaDodatkowa: null
                    idZespolu: 10
                  - id: 200
                    nazwisko: ZAKRZEWICZ
                    etat: STAZYSTA
                    idSzefa: 140
                    zatrudniony: 1994-07-15
                    placaPodstawowa: 208.00
                    placaDodatkowa: null
                    idZespolu: 30
                  - id: 210
                    nazwisko: BIALY
                    etat: STAZYSTA
                    idSzefa: 130
                    zatrudniony: 1993-10-15
                    placaPodstawowa: 250.00
                    placaDodatkowa: 170.60
                    idZespolu: 30
                  - id: 220
                    nazwisko: KONOPKA
                    etat: ASYSTENT
                    idSzefa: 110
                    zatrudniony: 1993-10-01
                    placaPodstawowa: 480.00
                    placaDodatkowa: null
                    idZespolu: 20
                  - id: 230
                    nazwisko: HAPKE
                    etat: ASYSTENT
                    idSzefa: 120
                    zatrudniony: 1992-09-01
                    placaPodstawowa: 480.00
                    placaDodatkowa: 90.00
                    idZespolu: 30
                zespoly:
                  10:
                    id: 10
                    nazwa: ADMINISTRACJA
                    adres: PIOTROWO 3A
                  20:
                    id: 20
                    nazwa: SYSTEMY ROZPROSZONE
                    adres: PIOTROWO 3A
                  30:
                    id: 30
                    nazwa: SYSTEMY EKSPERCKIE
                    adres: STRZELECKA 14
                  40:
                    id: 40
                    nazwa: ALGORYTMY
                    adres: WLODKOWICA 16
                  50:
                    id: 50
                    nazwa: BADANIA OPERACYJNE
                    adres: MIELZYNSKIEGO 30
                """;
    }

}
