package com.example.demo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.iterableWithSize;

import com.netflix.graphql.dgs.internal.DefaultDgsQueryExecutor;
import graphql.ExecutionResult;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author fuzi1996
 * @since 2.3
 */
@SpringBootTest
public class CalculatorExample {

    @Autowired
    private DefaultDgsQueryExecutor queryExecutor;

    @Test
    void testCal() {
        String query = ""
            + "query mapListArgument($itemIds: [Int]){ \n" +
            "    commodity{\n" +
            "        itemList(itemIds: $itemIds)\n" +
            "        @argumentTransform(argumentName: \"itemIds\",operateType: LIST_MAP,expression: \"ele*10\")\n"
            +
            "        {\n" +
            "            itemId\n" +
            "            name\n" +
            "        }\n" +
            "    }\n" +
            "}";
        ExecutionResult result = queryExecutor.execute(query,
            Collections.singletonMap("itemIds", Arrays.asList(1, 2, 3)));

        assertThat(result.getErrors(), emptyIterable());
        Map<String, Object> data = (Map<String, Object>) result.getData();
        assertThat(data, hasKey("commodity"));
        Map<String, Object> commodity = (Map<String, Object>) data.get("commodity");
        assertThat(commodity, hasKey("itemList"));
        List<Map<String, Object>> itemList = (List<Map<String, Object>>) commodity.get("itemList");
        assertThat(itemList, iterableWithSize(3));
    }
}
