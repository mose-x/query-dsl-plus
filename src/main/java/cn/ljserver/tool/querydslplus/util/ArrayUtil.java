package cn.ljserver.tool.querydslplus.util;

import java.lang.reflect.Array;

@SuppressWarnings({"unchecked", "SuspiciousSystemArraycopy"})
public class ArrayUtil {

    public static int getLength(Object array) {
        return array == null ? 0 : Array.getLength(array);
    }

    private static Object remove(Object array, int index) {
        int length = getLength(array);
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }

        Object result = Array.newInstance(array.getClass().getComponentType(), length - 1);
        System.arraycopy(array, 0, result, 0, index);
        if (index < length - 1) {
            System.arraycopy(array, index + 1, result, index, length - index - 1);
        }

        return result;
    }

    public static <T> T[] remove(T[] array, int index) {
        return (T[]) remove((Object) array, index);
    }
}
