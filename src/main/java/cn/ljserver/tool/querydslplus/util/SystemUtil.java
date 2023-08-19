package cn.ljserver.tool.querydslplus.util;

import cn.ljserver.tool.querydslplus.util.system.JavaVersion;

public class SystemUtil {
    private static String getSystemProperty() {
        try {
            return System.getProperty("java.specification.version");
        } catch (SecurityException ex) {
            System.err.println("Caught a SecurityException reading the system property '" + "java.specification.version"
                    + "'; the SystemUtils property value will default to null.");
            return null;
        }
    }

    public static final String JAVA_SPECIFICATION_VERSION = getSystemProperty();
    private static final JavaVersion JAVA_SPECIFICATION_VERSION_AS_ENUM = JavaVersion.getJavaVersion(JAVA_SPECIFICATION_VERSION);

    public static boolean isJavaVersionAtLeast(JavaVersion requiredVersion) {
        return JAVA_SPECIFICATION_VERSION_AS_ENUM.atLeast(requiredVersion);
    }
}
