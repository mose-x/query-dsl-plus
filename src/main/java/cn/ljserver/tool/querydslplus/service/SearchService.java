package cn.ljserver.tool.querydslplus.service;

import cn.ljserver.tool.querydslplus.querydsl.GeneralPredicatesBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

/**
 * Abstract service class
 */
@SuppressWarnings(value = {"unchecked", "rawtypes"})
public abstract class SearchService {
    /**
     * case ":"  eq, example: search=name:jack, like sql >> name="jack" <br><br>
     * case ">"  gt, example: search=age>18, like sql >> age>18 <br><br>
     * case "<"  lt, example: search=age<18, like sql >> age<18 <br><br>
     * case ")"  goe, example: search=age)18, like sql >> age>=18 <br><br>
     * case "("  loe, example: search=age(18, like sql >> age<=18 <br><br>
     * case "!"  né, example: search=age!18, like sql >> age!=18 or age<>18 </> <br><br>
     * case "*"  like, example: search=name*%abc%, like sql >> name like "%abc%"<br><br>
     * case "^"  not like, example: search=name^%abc%, like sql >> name not like "%abc%" <br><br>
     * case "@"  in, example: search=name@a|b|c, like sql >> name in ("a","b","c")  <br><br>
     * case "#"  contains, example: search=name#abc, like sql >> contains (name,"abc")  <br><br>
     * case "~"  or, example: search=name:abc~age:18, like sql >> name="abc" and age=18  <br><br>
     * case "|"  list separator, example: search=name@a|b|c, like sql >> name in ("a","b","c") <br><br>
     * other "," params split,more than 2 params, use the "," to separated <br>
     */
    protected BooleanExpression buildPredicate(String search, Class clazz) {
        return new GeneralPredicatesBuilder(clazz).build(search);
    }
}
