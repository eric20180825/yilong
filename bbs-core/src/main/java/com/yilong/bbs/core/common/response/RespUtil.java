package com.yilong.bbs.core.common.response;

public class RespUtil {

    public static RespEntity success(Object object){
        return new RespEntity(RespCode.SUCCESS,object);
    }

    public static RespEntity fail(Object object) {
        return new RespEntity(RespCode.FAIL, object);
    }

    public static RespEntity notLogin(){
        return new RespEntity(RespCode.NOT_LOGIN);
    }

    public static RespEntity systemRuntimeException() {
        return new RespEntity(RespCode.SYSTEM_RUNTIMEEXCEPTION);
    }

    public static RespEntity systemException() {
        return new RespEntity(RespCode.SYSTEM_EXCEPTION);
    }

    public static RespEntity systemRisk() {
        return new RespEntity(RespCode.SYSTEM_RISK);
    }
}
