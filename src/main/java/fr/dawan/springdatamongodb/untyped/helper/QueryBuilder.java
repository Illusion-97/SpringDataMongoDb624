package fr.dawan.springdatamongodb.untyped.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.*;
import java.util.function.Supplier;

public class QueryBuilder {

    public static Query build(Map<String,String> params) {
        List<Criteria> list = params
                .entrySet()
                .stream()
                .map(e -> {
                    String[] keyAndMethod = e.getKey().split("\\.");
                    String key = keyAndMethod[0];
                    String method = keyAndMethod.length > 1 ? keyAndMethod[1] : "is";
                    return getCriteria(key, method, e.getValue());
                })
                .toList();
        Criteria filter = new Criteria().andOperator(list);
        return new Query(filter);
    }

    private static Criteria getCriteria(String field, String methodName, String stringValue) {
        Criteria criteria = Criteria.where(field);
        return invokeCriteriaMethod(methodName, Object.class, criteria, parse(stringValue, new Object()),
                () -> invokeCriteriaMethod(methodName, Collection.class, criteria, parse(stringValue, new ArrayList<>()),
                        () -> criteria.is(stringValue)));
    }

    private static <T> Criteria invokeCriteriaMethod(String methodName, Class<T> argumentClass, Criteria criteria, T argument, Supplier<Criteria> orElse) {
        try {
            return (Criteria) Criteria.class.getDeclaredMethod(methodName, argumentClass).invoke(criteria, argument);
        } catch (Exception e) {
            return orElse.get();
        }
    }

    private static <T> T parse(String input,@NonNull T defaultValue) {
        try {
            return new ObjectMapper().readValue(Objects.toString(input, ""), new TypeReference<>() {});
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
