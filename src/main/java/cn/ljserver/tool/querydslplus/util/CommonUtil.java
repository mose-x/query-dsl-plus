package cn.ljserver.tool.querydslplus.util;

import cn.ljserver.tool.querydslplus.exception.SystemException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Generic utility classes
 */
public class CommonUtil {


    /**
     * An enumeration object that gets the current enumeration value by enumeration value
     *
     * @param name  Enumeration value
     * @param clazz EnumClass
     * @return Returns an enumeration object
     */
    public static Object getEnumObject(String name, Class<?> clazz) {
        if (!clazz.isEnum()) {
            throw new SystemException();
        }
        try {
            // Get all the enumeration constants
            Object[] objects = clazz.getEnumConstants();
            Method code = clazz.getMethod("name");
            for (Object obj : objects) {
                if (name.equals(code.invoke(obj))) {
                    return obj;
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new SystemException(e);
        }
        return null;
    }
}
