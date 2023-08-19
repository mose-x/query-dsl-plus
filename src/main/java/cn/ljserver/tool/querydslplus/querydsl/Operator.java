package cn.ljserver.tool.querydslplus.querydsl;

public class Operator {

    public static final String eq = ":";
    public static final String gt = ">";
    public static final String lt = "<";
    public static final String ne = "!";
    public static final String li = "*";
    public static final String nl = "^";
    public static final String in = "@";
    public static final String ct = "#";
    public static final String goe = ")";
    public static final String loe = "(";

    public static final String or = "~";

    public static final String paramsSplit = ",";

    public static final String pathSplit = "\\.";

    public static final String valueSplit = "\\|";

    public static final String pattern = "(.+?)([:!*<>()@^#])(.*)";

}
