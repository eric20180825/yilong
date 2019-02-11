package com.yilong.bbs.core.common;

import com.yilong.bbs.core.entity.User;
import com.yilong.utils.MD5Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ProjectUtil {

    @Value("${platform.securitySalt}")
    private static String SALT;

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取当前登陆用户信息
     * @return
     */
    public static User getLoginUser(){
        return (User)getSession().getAttribute("user");
    }

    /**
     * 设置当前登陆用户信息
     * @param findUser
     */
    public static void setLoginUser(User findUser) {
        getSession().setAttribute("user",findUser);
    }

    public static boolean isLogin() {
        User user=getLoginUser();
        if (user != null&&user.getAccount()!=null) {
            return true;
        }
        return false;
    }

    //获取加密密码
    public static String getEncryptPassword(String password) {
        password = password + SALT;
        String result = MD5Util.encode(password);
        return result;
    }

    /**
     * 设置验证码到session中
     * @param createText
     */
    public static void setKaptcha(String createText) {
        getSession().setAttribute("verifyCode",createText);
    }

    public static String getKaptcha(){
        return (String) getSession().getAttribute("verifyCode");
    }

    public static void removeKaptcha() {
        getSession().removeAttribute("verifyCode");
    }

    /**
     * kaptcha验证码是否有效
     * @return
     */
    public static boolean captchaValid(String kaptcha) {

        String verifyCode=getKaptcha();
        if (kaptcha == null || kaptcha.length() != 4 || !kaptcha.equalsIgnoreCase(verifyCode)) {
            return false;
        }
        //验证通过后，该验证码即不可再用，让其移除
        removeKaptcha();
        return true;
    }
}
