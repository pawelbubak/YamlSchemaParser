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
        // String rawSchema = getWorkersSchema();
        // String rawYaml = getWorkersYaml();
        // String rawSchema = getKubernetesDeploymentSchema();
        // String rawYaml = getKubernetesDeploymentYaml();
        // String rawSchema = getDockerComposeSchema();
        // String rawYaml = getDockerComposeYaml();
        // String rawSchema = getPrometheusConfigurationSchema();
        // String rawYaml = getPrometheusConfigurationYaml();
        String rawSchema = getSpringConfigurationSchema();
        String rawYaml = getSpringConfigurationYaml();
        Object yaml = getYaml(rawYaml);
        try (InputStream inputStream = new ByteArrayInputStream(rawSchema.getBytes(StandardCharsets.UTF_8));
             YamlSchemaReader schemaReader = YamlSchemaReader.createDefaultReader(inputStream)) {
            Schema schema = schemaReader.read();
            ProblemHandler problemHandler = ProblemHandlerProvider.provider()
                                                                  .createCollectProblemHandler(System.out::println);
            schema.test(problemHandler, yaml);
//            serializeYamlSchema(schema);
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
                    idZespolu: 30
                  - id: 130
                    nazwisko: BRZEZINSKI
                    etat: PROFESOR
                    zatrudniony: 1968-07-01
                    idSzefa: 100
                    placaPodstawowa: 960.00
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
                    idZespolu: 20
                  - id: 160
                    nazwisko: KOSZLAJDA
                    etat: ADIUNKT
                    idSzefa: 130
                    zatrudniony: 1985-03-01
                    placaPodstawowa: 590.00
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
                    idZespolu: 20
                  - id: 180
                    nazwisko: MAREK
                    etat: SEKRETARKA
                    idSzefa: 100
                    zatrudniony: 1985-02-20
                    placaPodstawowa: 410.20
                    idZespolu: 10
                  - id: 200
                    nazwisko: ZAKRZEWICZ
                    etat: STAZYSTA
                    idSzefa: 140
                    zatrudniony: 1994-07-15
                    placaPodstawowa: 208.00
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

    private static String getKubernetesDeploymentSchema() {
        return """
                ---
                $schema:
                  id: "http://example.com/schemas/example.yaml"
                  version: "1.0"
                                
                description: Example docker-compose configuration schema
                                
                definitions:
                  port: &port
                    type: object
                    properties:
                      containerPort:
                        type: integer
                        exclusiveMinimum: 0
                        maximum: 65535
                  environmentVariable: &environmentVariable
                    type: object
                    properties:
                      name:
                        type: string
                      valueFrom:
                        type: object
                        properties:
                          secretKeyRef:
                            type: object
                            properties:
                              key:
                                type: string
                              name:
                                type: string
                  volumeMount: &volumeMount
                    type: object
                    properties:
                      mountPath:
                        type: string
                      name:
                        type: string
                  container: &container
                    type: object
                    properties:
                      name:
                        type: string
                      image:
                        type: string
                      imagePullPolicy:
                        type: string
                        enum: ['Always', 'IfNotPresent', 'Never']
                      ports:
                        type: array
                        itemsType: *port
                      env:
                        type: array
                        itemsType: *environmentVariable
                      volumeMounts:
                        type: array
                        itemsType: *volumeMount
                  volume: &volume
                    type: object
                    properties:
                      name:
                        type: string
                      persistentVolumeClaim:
                        type: object
                        properties:
                          claimName:
                            type: string
                                
                properties:
                  apiVersion:
                    type: string
                    constant: apps/v1
                  kind:
                    type: string
                    constant: Deployment
                  metadata:
                    type: map
                    itemsKey:
                      type: string
                    itemsType:
                      type: string
                  spec:
                    type: object
                    properties:
                      replicas:
                        type: integer
                        exclusiveMinimum: 0
                      selector:
                        type: object
                        properties:
                          matchLabels:
                            type: map
                            itemsKey:
                              type: string
                            itemsType:
                              type: string
                      template:
                        type: object
                        properties:
                          metadata:
                            type: object
                            properties:
                              labels:
                                type: map
                                itemsKey:
                                  type: string
                                itemsType:
                                  type: string
                          spec:
                            type: object
                            properties:
                              containers:
                                type: array
                                itemsType: *container
                              volumes:
                                type: array
                                itemsType: *volume
                """;
    }

    private static String getKubernetesDeploymentYaml() {
        return """
                apiVersion: apps/v1
                kind: Deployment
                metadata:
                  name: postgres
                spec:
                  replicas: 1
                  selector:
                    matchLabels:
                      app: postgres
                  template:
                    metadata:
                      labels:
                        app: postgres
                    spec:
                      containers:
                        - name: postgres
                          image: postgres:latest
                          imagePullPolicy: "IfNoPresent"
                          ports:
                            - containerPort: 5432
                          env:
                            - name: POSTGRES_PASSWORD
                              valueFrom:
                                secretKeyRef:
                                  key: POSTGRES_PASSWORD
                                  name: postgres-secret
                          volumeMounts:
                            - mountPath: /var/lib/postgresql/data
                              name: postgredb
                      volumes:
                        - name: postgredb
                          persistentVolumeClaim:
                            claimName: postgres-claim
                """;
    }

    private static String getDockerComposeSchema() {
        return """
                $schema:
                  id: "http://example.com/schemas/example.yaml"
                  version: "1.0"
                                
                description: Example docker-compose configuration schema
                                
                definitions:
                  container: &container
                    type: object
                    properties:
                      image:
                        type: string
                      ports:
                        type: array
                        itemsType:
                          type: string
                          pattern: "[0-9]*:[0-9]*"
                      configs:
                        type: array
                        itemsType:
                          type: string
                      secrets:
                        type: array
                        itemsType:
                          type: string
                      volumes:
                        type: array
                        itemsType:
                          type: string
                      networks:
                        type: array
                        itemsType:
                          type: string
                      environment:
                        type: array
                        itemsType:
                          type: string
                  volume: &volume
                    type: object
                    properties:
                      driver:
                        type: string
                        enum: [flocker]
                      driver_opts:
                        type: object
                        properties:
                          size:
                            type: string
                                
                properties:
                  version:
                    type: string
                    required: true
                    pattern: "[0-9]*[.][0-9]*"
                  services:
                    type: map
                    required: true
                    itemsKey:
                      type: string
                    itemsType: *container
                  volumes:
                    type: map
                    required: true
                    itemsKey:
                      type: string
                    itemsType: *volume
                  configs:
                    type: object
                    required: false
                    properties:
                      httpd-config:
                        type: object
                        properties:
                          external:
                            type: boolean
                  secrets:
                    type: object
                    required: false
                    properties:
                      server-certificate:
                        type: object
                        properties:
                          external:
                            type: boolean
                  networks:
                    type: map
                    required: false
                    itemsKey:
                      type: string
                    itemsType:
                      type: object
                """;
    }

    private static String getDockerComposeYaml() {
        return """
                version: "3.9"
                                
                services:
                  frontend:
                    image: awesome/webapp
                    ports:
                      - "443x:8043"
                    networks:
                      - front-tier
                      - back-tier
                    configs:
                      - httpd-config
                    secrets:
                      - server-certificate
                                
                  backend:
                    image: awesome/database
                    volumes:
                      - db-data:/etc/data
                    environment:
                      - POSTGRES_NAME=postgres
                      - POSTGRES_USER=postgres
                      - POSTGRES_PASSWORD=postgres
                    networks:
                      - back-tier
                                
                volumes:
                  db-data:
                    driver: flocker
                    driver_opts:
                      size: "10GiB"
                                
                configs:
                  httpd-config:
                    external: true
                                
                secrets:
                  server-certificate:
                    external: true
                                
                networks:
                  front-tier: {}
                  back-tier: {}
                """;
    }

    private static String getPrometheusConfigurationSchema() {
        return """
                $schema:
                  id: "http://example.com/schemas/example.yaml"
                  version: "1.0"
                                
                title: Example prometheus configuration
                                
                definitions:
                  jobTarget: &jobTarget
                    type: object
                    properties:
                      targets:
                        type: array
                        required: true
                        itemsType:
                          type: string
                      labels:
                        type: map
                        required: false
                        itemsKey:
                          type: string
                        itemsType:
                          type: string
                  job: &job
                    type: object
                    properties:
                      job_name:
                        type: string
                      scrape_interval:
                        type: string
                      static_configs:
                        type: array
                        itemsType: *jobTarget
                                
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
                  scrape_configs:
                    type: array
                    itemsType: *job
                """;
    }

    private static String getPrometheusConfigurationYaml() {
        return """
                global:
                  scrape_interval: 15s # By default, scrape targets every 15 seconds.
                  evaluation_interval: 15s # Evaluate rules every 15 seconds.
                                
                  # Attach these extra labels to all timeseries collected by this Prometheus instance.
                  external_labels:
                    monitor: 'codelab-monitor'
                                
                rule_files:
                  - 'prometheus.rules.yml'
                                
                scrape_configs:
                  - job_name: 'prometheus'
                                
                    # Override the global default and scrape targets from this job every 5 seconds.
                    scrape_interval: 5s
                                
                    static_configs:
                      - targets: ['localhost:9090']
                                
                  - job_name: 'node'
                                
                    # Override the global default and scrape targets from this job every 5 seconds.
                    scrape_interval: 5s
                                
                    static_configs:
                      - targets: ['localhost:8080', 'localhost:8081']
                        labels:
                          group: 'production'
                                
                      - targets: ['localhost:8082']
                        labels:
                          group: 'canary'
                """;
    }

    private static String getSpringConfigurationSchema() {
        return """
                $schema:
                  id: "http://example.com/schemas/example.yaml"
                  version: "1.0"
                                
                description: Example docker-compose configuration schema
                                
                definitions:
                  route: &route
                    type: object
                    properties:
                      id:
                        type: string
                        required: true
                      uri:
                        type: string
                        required: true
                      predicates:
                        type: array
                        itemsType:
                          type: string
                      filters:
                        type: array
                        itemsType:
                          type: string
                  url: &url
                    type: object
                    properties:
                      name:
                        type: string
                        required: true
                      url:
                        type: string
                        required: true
                                
                properties:
                  server:
                    type: object
                    properties:
                      port:
                        type: integer
                        exclusiveMinimum: 0
                        maximum: 65535
                  eureka:
                    type: object
                    properties:
                      client:
                        type: object
                        properties:
                          serviceUrl:
                            type: object
                            properties:
                              defaultZone:
                                type: string
                  logging:
                    type: object
                    properties:
                      pattern:
                        type: object
                        properties:
                          console:
                            type: string
                  spring:
                    type: object
                    properties:
                      cloud:
                        type: object
                        properties:
                          gateway:
                            type: object
                            properties:
                              discovery:
                                type: object
                                properties:
                                  locator:
                                    type: object
                                    properties:
                                      enabled:
                                        type: boolean
                              routes:
                                type: array
                                itemsType: *route
                  springdoc:
                    type: object
                    properties:
                      swagger-ui:
                        type: object
                        properties:
                          urls:
                            type: array
                            itemsType: *url
                """;
    }

    private static String getSpringConfigurationYaml() {
        return """
                server:
                  port: 8060
                                
                eureka:
                  client:
                    serviceUrl:
                      defaultZone: http://localhost:8061/eureka/
                                
                logging:
                  pattern:
                    console: "%d{yyyy-MM-dd HH:mm:ss} ${LOG_LEVEL_PATTERN:-%5p} %m%n"

                spring:
                  cloud:
                    gateway:
                      discovery:
                        locator:
                          enabled: true
                      routes:
                      - id: employee-service
                        uri: lb://employee-service
                        predicates:
                        - Path=/employee/**
                        filters:
                        - RewritePath=/employee/(?<path>.*), /$\\{path}
                      - id: department-service
                        uri: lb://department-service
                        predicates:
                        - Path=/department/**
                        filters:
                        - RewritePath=/department/(?<path>.*), /$\\{path}
                      - id: organization-service
                        uri: lb://organization-service
                        predicates:
                        - Path=/organization/**
                        filters:
                        - RewritePath=/organization/(?<path>.*), /$\\{path}
                      - id: openapi
                        predicates:
                        - Path=/v3/api-docs/**
                        filters:
                        - RewritePath=/v3/api-docs/(?<path>.*), /$\\{path}/v3/api-docs
                                
                springdoc:
                  swagger-ui:
                    urls:
                      - name: employee
                        url: /v3/api-docs/employee
                      - name: department
                        url: /v3/api-docs/department
                      - name: organization
                        url: /v3/api-docs/organization
                """;
    }

}
