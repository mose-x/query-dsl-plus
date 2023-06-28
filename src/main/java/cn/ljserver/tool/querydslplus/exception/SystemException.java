package cn.ljserver.tool.querydslplus.exception;

/**
 * system error message class
 */
public class SystemException extends BaseException {

    public SystemException() {
        super(GenExceptCode.System_Error);
    }

    public SystemException(Throwable cause) {
        super(GenExceptCode.System_Error, cause);
    }
}
