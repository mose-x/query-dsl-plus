package cn.ljserver.tool.querydslplus.querydsl;

import cn.ljserver.tool.querydslplus.util.DateFormatUtil;
import org.apache.commons.beanutils.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Date converter
 */
@SuppressWarnings("unchecked")
public class DateConverter implements Converter {
    @Override
    public Object convert(Class clazz, Object o) {
        if (clazz == LocalDate.class) {
            return DateFormatUtil.parseDate((String) o);
        } else if (clazz == LocalDateTime.class) {
            return DateFormatUtil.parseDateTime((String) o);
        } else if (clazz == LocalTime.class) {
            return DateFormatUtil.parseTime((String) o);
        }
        return null;
    }
}
