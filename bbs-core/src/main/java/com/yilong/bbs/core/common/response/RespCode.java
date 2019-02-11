package com.yilong.bbs.core.common.response;

public enum RespCode {
    //系统代码
    SUCCESS(0, "success"),
    FAIL(1, "fail"),
    NOT_LOGIN(2,"未登陆"),


    //业务属性（100-200）
    USER_ACCOUNT_NOT_REGISTER(100,"该帐号已注册"),
    USER_NICKNAME_NOT_REGISTER(101,"该昵称已注册"),
    USER_ACCOUNT_OR_PASSWORD_ERROR(102,"帐号或密码错误"),
    CAPTCHA_ERROR(103, "验证码错误"),


    SYSTEM_EXCEPTION(500, "系统异常，请联系技术处理"),
    SYSTEM_RUNTIMEEXCEPTION(501, "系统运行时异常，请联系技术处理"),
    SYSTEM_RISK(502,"风控操作"),
    SYSTEM_CUSTOM(999,"系统最大值,不用");


    private int code;
    private String msg;

    RespCode(int code) {
        this.code=code;
    }

    RespCode(int code, String msg) {
        this.code=code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}
