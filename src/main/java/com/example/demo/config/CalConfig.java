package com.example.demo.config;

import calculator.config.Config;
import calculator.config.DefaultConfig;
import calculator.engine.ExecutionEngine;
import calculator.engine.SchemaWrapper;
import com.netflix.graphql.dgs.internal.DgsSchemaProvider;
import graphql.execution.AsyncExecutionStrategy;
import graphql.execution.ExecutionStrategy;
import graphql.execution.instrumentation.Instrumentation;
import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.visibility.GraphqlFieldVisibility;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.logging.LoggingMeterRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CalConfig {

    @Bean
    public Config registACalculatorConfig() {
        return DefaultConfig.newConfig().build();
    }

    @Bean
    public Instrumentation registCalSimpleInstrumentation(Config calculatorConfig) {
        return ExecutionEngine.newInstance(calculatorConfig);
    }

    @Bean
    public GraphQLSchema schema(DgsSchemaProvider dgsSchemaProvider, GraphqlFieldVisibility fieldVisibility,Config calculatorConfig) {
        GraphQLSchema existingSchema = dgsSchemaProvider.schema(null, fieldVisibility);
        return SchemaWrapper.wrap(calculatorConfig, existingSchema);
    }

    @Bean("query")
    public ExecutionStrategy registAsyncExecutionStrategy(){
        return new AsyncExecutionStrategy();
    }
}

