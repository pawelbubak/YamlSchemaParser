module YamlSchemaParser {
    exports org.yaml.schema.parser;

    requires static lombok;
    requires org.yaml.snakeyaml;
    requires org.reflections;

    uses org.yaml.schema.parser.api.reader.YamlReaderProvider;
    uses org.yaml.schema.parser.api.schema.property.factory.SchemaPropertyFactoryProvider;
    uses org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMappersLoaderProvider;
    uses org.yaml.schema.parser.api.schema.reader.SchemaSpecVersionDesignatorProvider;
    uses org.yaml.schema.parser.api.schema.reader.YamlSchemaReaderFactoryProvider;
    uses org.yaml.schema.parser.api.schema.type.designator.SchemaPropertyTypeDesignatorProvider;
    uses org.yaml.schema.parser.api.schema.type.mapper.SchemaTypeMappersLoaderProvider;
    uses org.yaml.schema.parser.api.validator.problem.LineFormatterProvider;
    uses org.yaml.schema.parser.api.validator.problem.ProblemHandlerProvider;
    uses org.yaml.schema.parser.api.validator.problem.ProblemPrinterProvider;

    provides org.yaml.schema.parser.api.reader.YamlReaderProvider with org.yaml.schema.parser.internal.reader.DefaultYamlReaderProvider;
    provides org.yaml.schema.parser.api.schema.property.factory.SchemaPropertyFactoryProvider with org.yaml.schema.parser.internal.schema.property.factory.DefaultSchemaPropertyFactoryProvider;
    provides org.yaml.schema.parser.api.schema.property.mapper.SchemaPropertyMappersLoaderProvider with org.yaml.schema.parser.internal.schema.property.mapper.DefaultSchemaPropertyMappersLoaderProvider;
    provides org.yaml.schema.parser.api.schema.reader.SchemaSpecVersionDesignatorProvider with org.yaml.schema.parser.internal.schema.reader.DefaultSchemaSpecVersionDesignatorProvider;
    provides org.yaml.schema.parser.api.schema.reader.YamlSchemaReaderFactoryProvider with org.yaml.schema.parser.internal.schema.reader.DefaultYamlSchemaReaderFactoryProvider;
    provides org.yaml.schema.parser.api.schema.type.designator.SchemaPropertyTypeDesignatorProvider with org.yaml.schema.parser.internal.schema.type.designator.DefaultSchemaPropertyTypeDesignatorProvider;
    provides org.yaml.schema.parser.api.schema.type.mapper.SchemaTypeMappersLoaderProvider with org.yaml.schema.parser.internal.schema.type.mapper.DefaultSchemaTypeMappersLoaderProvider;
    provides org.yaml.schema.parser.api.validator.problem.LineFormatterProvider with org.yaml.schema.parser.internal.validator.problem.DefaultLineFormatterProvider;
    provides org.yaml.schema.parser.api.validator.problem.ProblemHandlerProvider with org.yaml.schema.parser.internal.validator.problem.DefaultProblemHandlerProvider;
    provides org.yaml.schema.parser.api.validator.problem.ProblemPrinterProvider with org.yaml.schema.parser.internal.validator.problem.DefaultProblemPrinterProvider;

}