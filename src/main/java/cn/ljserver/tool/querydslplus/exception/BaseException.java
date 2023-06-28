package cn.ljserver.tool.querydslplus.exception;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -4491620812235998645L;
    protected ExceptionCode code;
    protected Object[] values;

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(ExceptionCode code) {
        super(code.getMsg());
        this.code = code;
    }

    public BaseException(ExceptionCode code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause, ExceptionCode code, Object[] values) {
        super(message, cause);
        this.code = code;
        if (values != null) {
            this.values = values.clone();
        } else {
            this.values = new Object[0];
        }
    }

    public BaseException(String _code, String _message) {
        super(_message);
        this.code = new ExCode(_code, _message);
    }

    public Map<String, Object> buildBody() {
        String msg = MessageFormat.format(code.getMsg(), values);
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", code.getCode());
        map.put("message", msg);
        return map;
    }

    public ExceptionCode getCode() {
        return code;
    }

    public Object[] getValues() {
        if (values != null) {
            return values.clone();
        } else {
            return new Object[0];
        }
    }

    public void setCode(ExceptionCode code) {
        this.code = code;
    }

    public void setValues(Object[] values) {
        if (values != null) {
            this.values = values.clone();
        } else {
            this.values = new Object[0];
        }
    }

    /**
     * static inner class
     */
    public static class ExCode implements ExceptionCode {
        private static final long serialVersionUID = -449620812235998645L;
        private final String code;
        private final String msg;

        ExCode(String _code, String _message) {
            this.code = _code;
            this.msg = _message;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getMsg() {
            return msg;
        }
    }

}