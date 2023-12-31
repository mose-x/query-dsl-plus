package cn.ljserver.tool.querydslplus.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.beanutils.ConvertUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Expression parser
 *
 * @param <T>
 */
public abstract class PredictResolver<T> {
    protected Class<T> clazz;
    protected SearchCriteria criteria;

    public PredictResolver(Class<T> clazz, final SearchCriteria criteria) {
        this.clazz = clazz;
        this.criteria = criteria;
        DateConverter dateConverter = new DateConverter();
        ConvertUtils.register(dateConverter, LocalTime.class);
        ConvertUtils.register(dateConverter, LocalDateTime.class);
        ConvertUtils.register(dateConverter, LocalDate.class);
    }


    public abstract BooleanExpression getPredicate() throws Exception;

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public SearchCriteria getCriteria() {
        return criteria;
    }

    public void setCriteria(SearchCriteria criteria) {
        this.criteria = criteria;
    }
}
