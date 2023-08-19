package cn.ljserver.tool.querydslplus.querydsl;

import cn.ljserver.tool.querydslplus.util.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import org.apache.commons.beanutils.ConvertUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Equality operator parser
 *
 * @param <T>
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class EqualPredicate<T> extends PredictResolver {

    public EqualPredicate(Class<T> clazz, SearchCriteria criteria) {
        super(clazz, criteria);
    }

    public BooleanExpression getPredicate() throws Exception {
        String path = this.getCriteria().getPath();
        PathBuilder entityPath = new PathBuilder(clazz, clazz.getName());
        // Resolve the path
        String[] props = path.split(Operator.pathSplit);
        // Target property name
        String targetProp = props[props.length - 1];
        props = ArrayUtil.remove(props, props.length -1);
        // The attribution class of the target attribute
        Class parentClass = clazz;

        // Resolve attribution classes
        for (String prop : props) {
            // Private properties can be obtained
            Field field = FieldUtil.getField(parentClass, prop, true);
            if (ClassUtil.isAssignable(field.getType(), Collection.class)) {
                entityPath = entityPath.get(prop, field.getType());
                ParameterizedType aType = (ParameterizedType) field.getGenericType();
                // The member type of the list object
                parentClass = (Class) aType.getActualTypeArguments()[0];
            } else {
                entityPath = entityPath.get(prop, field.getType());
                parentClass = field.getType();
            }

        }
        Field targetField = FieldUtil.getField(parentClass, targetProp, true);
        // The type of the target property
        Class valueType = targetField.getType();
        Class valueType4List = null;
        // If it is a collection class, get the member type
        if (ClassUtil.isAssignable(valueType, Collection.class)) {
            ParameterizedType aType = (ParameterizedType) targetField.getGenericType();
            // The member type of the list object
            valueType4List = (Class) aType.getActualTypeArguments()[0];
        }
        String valueStr = this.getCriteria().getValue();
        String[] values = valueStr.split(Operator.valueSplit);
        if (values.length == 0) {
            throw new Exception("The parameter value cannot be null");
        }
        BooleanExpression ex = null;
        if (valueType == LocalDate.class || valueType == LocalDateTime.class) {
            if (values.length > 2) {
                throw new Exception("The time zone is incorrect");
            }
            if (values.length == 1) {
                String value = values[0];
                if ("null".equals(value) || value == null) {
                    ex = entityPath.get(targetProp, valueType).isNull();
                } else {
                    ex = entityPath.get(targetProp, valueType).eq(ConvertUtils.convert(values[0], valueType));
                }
            } else {
                if (valueType == LocalDate.class) {
                    LocalDate date1 = DateFormatUtil.parseDate(values[0]);
                    LocalDate date2 = DateFormatUtil.parseDate(values[1]);
                    ex = entityPath.getDate(targetProp, LocalDate.class).between(date1, date2);
                } else {
                    LocalDateTime date1 = DateFormatUtil.parseDateTime(values[0]);
                    LocalDateTime date2 = DateFormatUtil.parseDateTime(values[1]);
                    ex = entityPath.getDateTime(targetProp, LocalDateTime.class).between(date1, date2);
                }

            }
        } else if (valueType == List.class) {
            for (String value : values) {
                if (valueType4List != null) {
                    if ("null".equals(value)) {
                        value = null;
                    }
                    // Enumerate type List
                    if (valueType4List.isEnum()) {
                        if (value == null) {
                            if (ex == null) {
                                ex = entityPath.getCollection(targetProp, valueType).any().isNull();
                            } else {
                                ex = ex.or(entityPath.getCollection(targetProp, valueType).any().isNull());
                            }
                        } else {
                            if (ex == null) {
                                ex = entityPath.getCollection(targetProp, valueType).any()
                                        .eq(Objects.requireNonNull(CommonUtil.getEnumObject(value, valueType4List)));
                            } else {
                                ex = ex.or(entityPath.getCollection(targetProp, valueType).any()
                                        .eq(Objects.requireNonNull(CommonUtil.getEnumObject(value, valueType4List))));
                            }
                        }
                    }
                    // String type List
                    else if (valueType4List == String.class) {
                        if (value != null) {
                            if (ex == null) {
                                ex = entityPath.getCollection(targetProp, valueType).any().eq(value);
                            } else {
                                ex = ex.or(entityPath.getCollection(targetProp, valueType).any().eq(value));
                            }
                        } else {
                            if (ex == null) {
                                ex = entityPath.getCollection(targetProp, valueType).any().isNull();
                            } else {
                                ex = ex.or(entityPath.getCollection(targetProp, valueType).any().isNull());
                            }
                        }
                    }
                }
            }
        } else {
            if (valueType.isEnum()) {
                for (String value : values) {
                    if ("null".equals(value)) {
                        value = null;
                    }
                    if (value != null) {
                        if (ex == null) {
                            ex = entityPath.get(targetProp, valueType).eq(Objects.requireNonNull(CommonUtil.getEnumObject(value, valueType)));
                        } else {
                            ex = ex.or(entityPath.get(targetProp, valueType).eq(Objects.requireNonNull(CommonUtil.getEnumObject(value, valueType))));
                        }
                    } else {
                        if (ex == null) {
                            ex = entityPath.get(targetProp, valueType).isNull();
                        } else {
                            ex = ex.or(entityPath.get(targetProp, valueType).isNull());
                        }
                    }
                }
            } else {
                for (String value : values) {
                    if ("null".equals(value)) {
                        value = null;
                    }
                    if (value != null) {
                        if (ex == null) {
                            ex = entityPath.get(targetProp, valueType).eq(ConvertUtils.convert(value, valueType));
                        } else {
                            ex = ex.or(entityPath.get(targetProp, valueType).eq(ConvertUtils.convert(value, valueType)));
                        }
                    } else {
                        if (ex == null) {
                            ex = entityPath.get(targetProp, valueType).isNull();
                        } else {
                            ex = ex.or(entityPath.get(targetProp, valueType).isNull());
                        }
                    }
                }
            }
        }
        return ex;
    }
}
