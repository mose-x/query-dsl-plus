package cn.ljserver.tool.querydslplus.querydsl;

import lombok.Data;

/**
 * Search Criteria
 */
@Data
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

}
