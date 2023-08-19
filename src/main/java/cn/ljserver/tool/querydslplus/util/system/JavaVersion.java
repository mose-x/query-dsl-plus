package cn.ljserver.tool.querydslplus.util.system;

public enum JavaVersion {

    /**
     * The Java version reported by Android. This is not an official Java version number.
     */
    JAVA_0_9(1.5f, "0.9"),

    /**
     * Java 1.1.
     */
    JAVA_1_1(1.1f, "1.1"),

    /**
     * Java 1.2.
     */
    JAVA_1_2(1.2f, "1.2"),

    /**
     * Java 1.3.
     */
    JAVA_1_3(1.3f, "1.3"),

    /**
     * Java 1.4.
     */
    JAVA_1_4(1.4f, "1.4"),

    /**
     * Java 1.5.
     */
    JAVA_1_5(1.5f, "1.5"),

    /**
     * Java 1.6.
     */
    JAVA_1_6(1.6f, "1.6"),

    /**
     * Java 1.7.
     */
    JAVA_1_7(1.7f, "1.7"),

    /**
     * Java 1.8.
     */
    JAVA_1_8(1.8f, "1.8");

    /**
     * The float value.
     */
    private float value;
    /**
     * The standard name.
     */
    private String name;

    /**
     * Constructor.
     *
     * @param value the float value
     * @param name  the standard name, not null
     */
    JavaVersion(final float value, final String name) {
        this.value = value;
        this.name = name;
    }

    //-----------------------------------------------------------------------

    public boolean atLeast(JavaVersion requiredVersion) {
        return this.value >= requiredVersion.value;
    }

    // helper for static importing
    public static JavaVersion getJavaVersion(final String nom) {
        return get(nom);
    }

    static JavaVersion get(final String nom) {
        if ("0.9".equals(nom)) {
            return JAVA_0_9;
        } else if ("1.1".equals(nom)) {
            return JAVA_1_1;
        } else if ("1.2".equals(nom)) {
            return JAVA_1_2;
        } else if ("1.3".equals(nom)) {
            return JAVA_1_3;
        } else if ("1.4".equals(nom)) {
            return JAVA_1_4;
        } else if ("1.5".equals(nom)) {
            return JAVA_1_5;
        } else if ("1.6".equals(nom)) {
            return JAVA_1_6;
        } else if ("1.7".equals(nom)) {
            return JAVA_1_7;
        } else if ("1.8".equals(nom)) {
            return JAVA_1_8;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return name;
    }

}
