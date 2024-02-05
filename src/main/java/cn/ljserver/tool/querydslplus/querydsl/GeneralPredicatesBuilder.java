package cn.ljserver.tool.querydslplus.querydsl;

import cn.ljserver.tool.querydslplus.exception.GenExceptCode;
import cn.ljserver.tool.querydslplus.exception.ServiceException;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * common Predicate Builder
 *
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public final class GeneralPredicatesBuilder<T> {
    private final Class<T> clazz;
    // Storage 'and' query conditions
    private final List<SearchCriteria> params;
    /**
     * Used to store query conditions with ‘or’ or ‘two’ conditions
     * 1、a=0 or b=0 or c=0
     * 2、(a=0 or b=0) and (c=0 or d=0)
     * So store it in Map
     */
    private final Map<String, List<SearchCriteria>> orParams;

    public GeneralPredicatesBuilder(Class<T> clazz) {
        this.clazz = clazz;
        params = new ArrayList<>();
        orParams = new HashMap<>();
    }

    public void with(final String key, final String operation, final String value) {
        params.add(new SearchCriteria(key, operation, value));
    }

    public void or(final String key, final List<SearchCriteria> searchList) {
        orParams.put(key, searchList);
    }

    public BooleanExpression build(String search) {
        if (search != null) {
            String[] searchArr = search.split(Operator.paramsSplit);
            int i = 0;
            String key = "key";
            for (String searchStr : searchArr) {
                Pattern pattern = Pattern.compile(Operator.pattern);
                if (searchStr.contains(Operator.or)) {
                    // Query criteria that exist or
                    String[] orSearches = searchStr.split(Operator.or);
                    List<SearchCriteria> searchList = new ArrayList<>();
                    for (String orSearch : orSearches) {
                        Matcher matcher = pattern.matcher(orSearch);
                        if (matcher.find()) {
                            searchList.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
                        }
                    }
                    or(key + i, searchList);
                    i++;
                } else {
                    Matcher matcher = pattern.matcher(searchStr);
                    if (matcher.find()) {
                        with(matcher.group(1), matcher.group(2), matcher.group(3));
                    }
                }
            }
        }

        if (params.isEmpty() && orParams.isEmpty()) {
            return null;
        }

        BooleanExpression andResult = null;
        if (!params.isEmpty()) {
            for (final SearchCriteria param : params) {
                PredictResolver resolver = PredictProducer.generateResolver(clazz, param);
                BooleanExpression exp;

                try {
                    assert resolver != null;
                    exp = resolver.getPredicate();
                } catch (Exception e) {
                    throw new ServiceException(GenExceptCode.Request_Param, e);
                }

                if (exp != null) {
                    if (andResult == null) {
                        andResult = exp;
                    } else {
                        andResult = andResult.and(exp);
                    }
                }
            }
        }

        BooleanExpression orResult = null;
        if (!orParams.isEmpty()) {
            for (final Map.Entry<String, List<SearchCriteria>> entry : orParams.entrySet()) {
                List<SearchCriteria> scList = entry.getValue();
                BooleanExpression tempResult = null;
                if (scList != null && !scList.isEmpty()) {
                    for (final SearchCriteria param : scList) {
                        PredictResolver resolver = PredictProducer.generateResolver(clazz, param);
                        BooleanExpression exp;

                        try {
                            assert resolver != null;
                            exp = resolver.getPredicate();
                        } catch (Exception e) {
                            throw new ServiceException(GenExceptCode.Request_Param, e);
                        }

                        // The data in the List<Search Criteria > is connected with OR
                        if (exp != null) {
                            if (tempResult == null) {
                                tempResult = exp;
                            } else {
                                tempResult = tempResult.or(exp);
                            }
                        }
                    }
                }
                // The data in the Map is connected with AND
                if (tempResult != null) {
                    if (orResult == null) {
                        orResult = tempResult;
                    } else {
                        orResult = orResult.and(tempResult);
                    }
                }

            }
        }

        // Combine AND and AND and OR conditions
        BooleanExpression result = null;
        if (andResult != null) {
            result = andResult;
        }

        if (orResult != null) {
            if (result != null) {
                result = result.and(orResult);
            } else {
                result = orResult;
            }
        }

        return result;
    }

}
