package cn.ljserver.tool.querydslplus.querydsl;

import cn.ljserver.tool.querydslplus.util.ArrayUtil;
import cn.ljserver.tool.querydslplus.util.ClassUtil;
import cn.ljserver.tool.querydslplus.util.FieldUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import org.apache.commons.beanutils.ConvertUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

/**
 * Fuzzy query operator parser
 *
 * @param <T>
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class LikePredicate<T> extends PredictResolver {

    public LikePredicate(Class<T> clazz, SearchCriteria criteria) {
        super(clazz, criteria);
    }

    public BooleanExpression getPredicate() throws Exception {
        String path = this.getCriteria().getPath();
        PathBuilder entityPath = new PathBuilder(clazz, clazz.getName());
        // Resolve the path
        String[] props = path.split(Operator.pathSplit);
        // Target property name
        String targetProp = props[props.length - 1];
        props = ArrayUtil.remove(props, props.length - 1);
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
        // If it is a collection class, get the member type
        if (ClassUtil.isAssignable(valueType, Collection.class)) {
            ParameterizedType aType = (ParameterizedType) targetField.getGenericType();
            // The member type of the list object
            valueType = (Class) aType.getActualTypeArguments()[0];
        }
        String valueStr = this.getCriteria().getValue();
        String[] values = valueStr.split(Operator.valueSplit);
        if (values.length == 0) {
            throw new Exception("The parameter value cannot be null");
        }
        BooleanExpression ex = null;
        for (String value : values) {
            if (value != null && !"".equals(value)) {
                value = value.replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)");
            }
            String tempValue = (String) ConvertUtils.convert(value, valueType);
            if (ex == null) {
                ex = entityPath.getString(targetProp).like(tempValue);
            } else {
                ex = ex.or(entityPath.getString(targetProp).like(tempValue));
            }
        }
        return ex;
    }
}
