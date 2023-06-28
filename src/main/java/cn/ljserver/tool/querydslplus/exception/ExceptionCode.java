package cn.ljserver.tool.querydslplus.exception;

import java.io.Serializable;

/**
 * interface to get error code and message
 */
public interface ExceptionCode extends Serializable {
    String getCode();

    String getMsg();
}
