package cn.ljserver.tool.querydslplus.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FieldUtil {

    public static Field getField(final Class<?> cls, String fieldName, boolean forceAccess) {
        if (cls == null) {
            throw new IllegalArgumentException("The class must not be null");
        }
        if (fieldName == null) {
            throw new IllegalArgumentException("The field name must not be null");
        }
        for (Class<?> acls = cls; acls != null; acls = acls.getSuperclass()) {
            try {
                Field field = acls.getDeclaredField(fieldName);
                if (!Modifier.isPublic(field.getModifiers())) {
                    if (forceAccess) {
                        field.setAccessible(true);
                    } else {
                        continue;
                    }
                }
                return field;
            } catch (NoSuchFieldException ex) {
                // ignore
            }
        }
        Field match = null;
        for (Class<?> class1 : ClassUtil.getAllInterfaces(cls)) {
            try {
                Field test = (class1).getField(fieldName);
                if (match != null) {
                    throw new IllegalArgumentException("Reference to field " + fieldName + " is ambiguous relative to " + cls
                            + "; a matching field exists on two or more implemented interfaces.");
                }
                match = test;
            } catch (NoSuchFieldException ex) {
                // ignore
            }
        }
        return match;
    }

}
