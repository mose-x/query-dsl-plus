package cn.ljserver.tool.querydslplus.exception;

/**
 * common error message enum
 */
public enum GenExceptCode implements ExceptionCode {
    Request_Param("request param error"),
    Verify_Code("verify code error"),
    System_Error("system error"),
    Token_Invalid("token invalid");

    private final String msg;

    GenExceptCode(String _msg) {
        this.msg = _msg;
    }

    public String getCode() {
        return this.name();
    }

    public String getMsg() {
        return this.msg;
    }
}
