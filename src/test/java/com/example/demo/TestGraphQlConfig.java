package com.example.demo;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsRuntimeWiring;
import graphql.schema.DataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.TypeRuntimeWiring;
import java.util.Map;

/**
 * @author fuzi1996
 * @since 2.3
 */
@DgsComponent
public class TestGraphQlConfig {

    @DgsRuntimeWiring
    public RuntimeWiring.Builder testRuntimeWiringConfigurer(RuntimeWiring.Builder wiringBuilder) {
        for (Map.Entry<String, Map<String, DataFetcher>> entry : GraphQLSourceHolder
            .defaultDataFetcherInfo().entrySet()) {
            TypeRuntimeWiring.Builder typeWiring = TypeRuntimeWiring
                .newTypeWiring(entry.getKey())
                .dataFetchers(entry.getValue());
            wiringBuilder.type(typeWiring);
        }
        return wiringBuilder;
    }
}
