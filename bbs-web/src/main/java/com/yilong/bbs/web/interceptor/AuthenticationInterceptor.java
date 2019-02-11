package com.yilong.bbs.web.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.yilong.bbs.core.common.ProjectUtil;
import com.yilong.bbs.core.common.annotation.NoAuthentication;
import com.yilong.bbs.core.common.response.RespEntity;
import com.yilong.bbs.core.common.response.RespUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证拦截器
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.判断是否存在注解
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod method = (HandlerMethod)handler;
        boolean isNoAuthentication=method.getMethod().isAnnotationPresent(NoAuthentication.class);
        if(isNoAuthentication){//不需要认证，直接放行
            return true;
        }

        //1.登陆认证
        if (ProjectUtil.isLogin()) {
            return true;
        }else{
            RespEntity respEntity = RespUtil.notLogin();
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            //这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
            response.setCharacterEncoding("utf-8");
            response.getWriter().println(JSONObject.toJSONString(respEntity));
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
