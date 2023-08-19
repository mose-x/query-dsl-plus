package cn.ljserver.tool.querydslplus.querydsl;

import cn.ljserver.tool.querydslplus.util.ArrayUtil;
import cn.ljserver.tool.querydslplus.util.ClassUtil;
import cn.ljserver.tool.querydslplus.util.FieldUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import org.apache.commons.beanutils.ConvertUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * In Query operator parser
 *
 * @param <T>
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class InPredicate<T> extends PredictResolver {

    public InPredicate(Class<T> clazz, SearchCriteria criteria) {
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
            } else {
                entityPath = entityPath.get(prop, field.getType());
            }
        }
        String valueStr = this.getCriteria().getValue();
        String[] values = valueStr.split(Operator.valueSplit);
        if (values.length == 0) {
            throw new Exception("The parameter value cannot be null");
        }
        return entityPath.getString(targetProp).in((List) ConvertUtils.convert(Arrays.asList(values), List.class));
    }
}
