package cn.ljserver.tool.querydslplus.querydsl;

/**
 * Search Criteria
 */
public class SearchCriteria {

    private String path;
    private String operation;
    private String value;

    public SearchCriteria() {
    }

    public SearchCriteria(final String path, final String operation, final String value) {
        super();
        this.path = path;
        this.operation = operation;
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
