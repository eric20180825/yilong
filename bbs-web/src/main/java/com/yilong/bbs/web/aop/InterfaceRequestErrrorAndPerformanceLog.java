package com.yilong.bbs.web.aop;

import com.alibaba.fastjson.JSONObject;
import com.yilong.bbs.core.common.response.RespEntity;
import com.yilong.bbs.core.common.response.RespUtil;
import com.yilong.bbs.core.entity.Performance;
import com.yilong.bbs.core.service.PerformanceService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class InterfaceRequestErrrorAndPerformanceLog {

    @Value("${project.log.bad.value}")
    private int performanceBadValue;

    @Autowired
    private PerformanceService performanceService;

    @Pointcut("execution(* com.yilong.bbs.web.controller.*.*(..))")
    public void pointcut() {

    }

    @Around("pointcut()")
    public RespEntity handleControllerMethod(ProceedingJoinPoint pjp)throws Throwable{
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        RespEntity respEntity;
        try {

            log.info("执行controller开始："+pjp.getSignature()+" 参数: "+ Arrays.toString(pjp.getArgs()));

            //处理入参特殊字符和SQL注入攻击
            checkRequestParam(pjp);
            respEntity= (RespEntity) pjp.proceed(pjp.getArgs());

            try {
                log.info("执行controller结束：" + pjp.getSignature() + " 返回值:" + JSONObject.toJSONString(respEntity));
                //此处将日志打印放入try-catch是因为项目中有些对象实体bean过于复杂，导致序列化为json的时候报错，但是此处报错并不影响主要功能使用，只是返回结果日志没有打印，所以catch中也不做抛出异常处理
            } catch (Exception e1) {
                log.error(pjp.getSignature()+"返回记录日志失败,原因为："+ e1.getMessage());
            }

            stopWatch.stop();
            long consumeTime=stopWatch.getTotalTimeMillis() ;
            log.info("耗时："+ consumeTime+"毫秒");
            //当接口请求时间大于performanceBadValue秒时，标记为异常调用时间，并记录入库
            if(consumeTime>performanceBadValue){

                Performance performance=new Performance();
                performance.setApiname(Arrays.toString(pjp.getArgs()));
                performance.setConsumeTime(consumeTime);
                performanceService.save(performance);
            }
        } catch (Exception e) {
            respEntity=handlerException(pjp, e);
        }
        return respEntity;
    }

    public RespEntity handlerException(ProceedingJoinPoint pjp, Throwable e) {
        RespEntity respEntity;
        if (e instanceof RuntimeException) {
            log.error("RuntimeException{方法：" + pjp.getSignature() + "， 参数：" + pjp.getArgs() + ",异常：" + e.getMessage() + "}", e);
            respEntity = RespUtil.systemRuntimeException();
        } else {
            log.error("异常方法：" + pjp.getSignature() + "， 参数：" + pjp.getArgs() + ",异常：" + e.getMessage() + "}", e);
            respEntity = RespUtil.systemException();
        }

        return respEntity;
    }

    public void checkRequestParam(ProceedingJoinPoint pjp) throws Exception{
        String args = String.valueOf(pjp.getArgs());
        if (!IllegalStrFilterUtil.sqlStrFilter(args)) {
            log.warn("访问接口:" + pjp.getSignature() + " 输入参数存在注入风险！参数为:" + args);
            throw new Exception("风险操作1");
        }
        if (!IllegalStrFilterUtil.isIllegalStr(args)) {
            log.warn("访问接口:" + pjp.getSignature() + " 输入参数含有非法字符！参数为:" + args);
            throw new Exception("风险操作2");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        StopWatch sw = new StopWatch();

        sw.start("起床");
        Thread.sleep(1000);
        sw.stop();

        sw.start("睡觉");
        Thread.sleep(1000);
        sw.stop();

        sw.start("玩");
        Thread.sleep(1000);
        sw.stop();


        System.out.println(sw.prettyPrint());
        System.out.println(sw.getTotalTimeMillis());
        System.out.println(sw.getLastTaskName());
        System.out.println(sw.getLastTaskInfo());
        System.out.println(sw.getTaskCount());
    }
}
