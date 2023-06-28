package cn.ljserver.tool.querydslplus.querydsl;

import cn.ljserver.tool.querydslplus.util.CommonUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * not equal operator parser
 *
 * @param <T>
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class NotEqualPredicate<T> extends PredictResolver {

    public NotEqualPredicate(Class<T> clazz, SearchCriteria criteria) {
        super(clazz, criteria);
    }

    public BooleanExpression getPredicate() throws Exception {
        String path = this.getCriteria().getPath();
        PathBuilder entityPath = new PathBuilder(clazz, clazz.getName());
        // Resolve the path
        String[] props = StringUtils.split(path, ".");
        // Target property name
        String targetProp = props[props.length - 1];
        props = ArrayUtils.remove(props, props.length - 1);
        // The attribution class of the target attribute
        Class parentClass = clazz;

        // Resolve attribution classes
        for (String prop : props) {
            // Private properties can be obtained
            Field field = FieldUtils.getField(parentClass, prop, true);
            if (ClassUtils.isAssignable(field.getType(), Collection.class)) {
                entityPath = entityPath.get(prop, field.getType());
                ParameterizedType aType = (ParameterizedType) field.getGenericType();
                // The member type of the list object
                parentClass = (Class) aType.getActualTypeArguments()[0];
            } else {
                entityPath = entityPath.get(prop, field.getType());
                parentClass = field.getType();
            }

        }
        Field targetField = FieldUtils.getField(parentClass, targetProp, true);
        // The type of the target property
        Class valueType = targetField.getType();
        Class valueType4List = null;
        // If it is a collection class, get the member type
        if (ClassUtils.isAssignable(valueType, Collection.class)) {
            ParameterizedType aType = (ParameterizedType) targetField.getGenericType();
            // The member type of the list object
            valueType4List = (Class) aType.getActualTypeArguments()[0];
        }
        String valueStr = this.getCriteria().getValue();
        String[] values = StringUtils.split(valueStr, Operator.valueSplit);
        if (values.length == 0) {
            throw new Exception("The parameter value cannot be null");
        }
        BooleanExpression ex = null;
        if (valueType.isEnum()) {
            for (String value : values) {
                if ("null".equals(value)) {
                    value = null;
                }
                if (value != null) {
                    if (ex == null) {
                        ex = entityPath.get(targetProp, valueType).ne(Objects.requireNonNull(CommonUtil.getEnumObject(value, valueType)));
                    } else {
                        ex = ex.or(entityPath.get(targetProp, valueType).ne(Objects.requireNonNull(CommonUtil.getEnumObject(value, valueType))));
                    }
                } else {
                    if (ex == null) {
                        ex = entityPath.get(targetProp, valueType).isNotNull();
                    } else {
                        ex = ex.or(entityPath.get(targetProp, valueType).isNotNull());
                    }
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
                        if (value != null) {
                            if (ex == null) {
                                ex = entityPath.getCollection(targetProp, valueType).any()
                                        .ne(Objects.requireNonNull(CommonUtil.getEnumObject(value, valueType4List)));
                            } else {
                                ex = ex.or(entityPath.getCollection(targetProp, valueType).any()
                                        .ne(Objects.requireNonNull(CommonUtil.getEnumObject(value, valueType4List))));
                            }
                        } else {
                            if (ex == null) {
                                ex = entityPath.getCollection(targetProp, valueType).any().isNotNull();
                            } else {
                                ex = ex.or(entityPath.getCollection(targetProp, valueType).any().isNotNull());
                            }
                        }
                    }
                    // String type List
                    else if (valueType4List == String.class) {
                        if (value != null) {
                            if (ex == null) {
                                ex = entityPath.getCollection(targetProp, valueType).any().ne(value);
                            } else {
                                ex = ex.or(entityPath.getCollection(targetProp, valueType).any().ne(value));
                            }
                        } else {
                            if (ex == null) {
                                ex = entityPath.getCollection(targetProp, valueType).any().isNotNull();
                            } else {
                                ex = ex.or(entityPath.getCollection(targetProp, valueType).any().isNotNull());
                            }
                        }
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
                        ex = entityPath.get(targetProp, valueType).ne(ConvertUtils.convert(value, valueType));
                    } else {
                        ex = ex.or(entityPath.get(targetProp, valueType).ne(ConvertUtils.convert(value, valueType)));
                    }
                } else {
                    if (ex == null) {
                        ex = entityPath.get(targetProp, valueType).isNotNull();
                    } else {
                        ex = ex.or(entityPath.get(targetProp, valueType).isNotNull());
                    }
                }
            }
        }
        return ex;
    }
}
