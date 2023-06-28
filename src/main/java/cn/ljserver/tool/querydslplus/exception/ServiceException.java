package cn.ljserver.tool.querydslplus.exception;

/**
 * Service error message class
 */
public class ServiceException extends BaseException {

    public ServiceException(ExceptionCode code) {
        super(code);
    }

    public ServiceException(ExceptionCode code, Throwable ex) {
        super(code, ex);
    }

    public ServiceException(String code, String message) {
        super(code, message);
    }
}
