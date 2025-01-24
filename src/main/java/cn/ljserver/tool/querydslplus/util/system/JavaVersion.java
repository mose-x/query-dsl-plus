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
    JAVA_1_8(1.8f, "1.8"),

    /**
     * Java 9.
     */
    JAVA_9(9, "9"),

    /**
     * Java 10.
     */
    JAVA_10(10, "10"),

    /**
     * Java 11.
     */
    JAVA_11(11, "11"),

    /**
     * Java 12.
     */
    JAVA_12(12, "12"),

    /**
     * Java 13.
     */
    JAVA_13(13, "13"),

    /**
     * Java 14.
     */
    JAVA_14(14, "14"),

    /**
     * Java 15.
     */
    JAVA_15(15, "15"),

    /**
     * Java 16.
     */
    JAVA_16(16, "16"),

    /**
     * Java 17.
     */
    JAVA_17(17, "17"),

    /**
     * Java 18.
     */
    JAVA_18(18, "18"),

    /**
     * Java 19.
     */
    JAVA_19(19, "19"),

    /**
     * Java 20.
     */
    JAVA_20(20, "20"),

    /**
     * Java 21.
     */
    JAVA_21(21, "21");

    /**
     * The float value.
     */
    private final float value;
    /**
     * The standard name.
     */
    private final String name;

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
        } else if ("9".equals(nom)) {
            return JAVA_9;
        } else if ("10".equals(nom)) {
            return JAVA_10;
        } else if ("11".equals(nom)) {
            return JAVA_11;
        } else if ("12".equals(nom)) {
            return JAVA_12;
        } else if ("13".equals(nom)) {
            return JAVA_13;
        } else if ("14".equals(nom)) {
            return JAVA_14;
        } else if ("15".equals(nom)) {
            return JAVA_15;
        } else if ("16".equals(nom)) {
            return JAVA_16;
        } else if ("17".equals(nom)) {
            return JAVA_17;
        } else if ("18".equals(nom)) {
            return JAVA_18;
        } else if ("19".equals(nom)) {
            return JAVA_19;
        } else if ("20".equals(nom)) {
            return JAVA_20;
        } else if ("21".equals(nom)) {
            return JAVA_21;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return name;
    }

}
