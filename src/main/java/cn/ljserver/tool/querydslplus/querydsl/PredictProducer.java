package cn.ljserver.tool.querydslplus.querydsl;

/**
 * Predicate Producer
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class PredictProducer {

    public static PredictResolver generateResolver(Class clazz, SearchCriteria criteria) {
        String operator = criteria.getOperation();
        switch (operator) {
            case Operator.eq:
                return new EqualPredicate(clazz, criteria);
            case Operator.gt:
                return new GreaterThanPredicate(clazz, criteria);
            case Operator.lt:
                return new LessThanPredicate(clazz, criteria);
            case Operator.goe:
                return new GreaterThanOrEqualPredicate(clazz, criteria);
            case Operator.loe:
                return new LessThanOrEqualPredicate(clazz, criteria);
            case Operator.ne:
                return new NotEqualPredicate(clazz, criteria);
            case Operator.li:
                return new LikePredicate(clazz, criteria);
            case Operator.nl:
                return new NotLikePredicate(clazz, criteria);
            case Operator.in:
                return new InPredicate(clazz, criteria);
            case Operator.ct:
                return new ContainsPredicate(clazz, criteria);
            default:
                break;
        }
        return null;
    }
}
