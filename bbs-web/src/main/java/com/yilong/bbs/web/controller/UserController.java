package com.yilong.bbs.web.controller;

import com.yilong.bbs.core.common.ProjectUtil;
import com.yilong.bbs.core.common.annotation.NoAuthentication;
import com.yilong.bbs.core.common.response.RespCode;
import com.yilong.bbs.core.common.response.RespEntity;
import com.yilong.bbs.core.common.response.RespUtil;
import com.yilong.bbs.core.common.validator.GroupUpdate;
import com.yilong.bbs.core.entity.User;
import com.yilong.bbs.core.service.UserService;
import com.yilong.utils.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("register")
    @NoAuthentication
    public RespEntity register(@Valid User user, BindingResult bindingResult, String kaptcha) throws Exception {

        //请求参数规则校验
        //验证码校验
        if(!ProjectUtil.captchaValid(kaptcha)){
            return new RespEntity(RespCode.CAPTCHA_ERROR);
        }

        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                return RespUtil.fail(error.getDefaultMessage());
            }
        }

        //业务逻辑校验
        List<User> userList = userService.findByAccountOrNickname(user.getAccount(), user.getNickname());
        if (userList != null && userList.size() > 0) {
            User findUser = userList.get(0);//每次只判断一个不符合的条件
            if (findUser.getAccount() != null && findUser.getAccount().equals(user.getAccount())) {
                return new RespEntity(RespCode.USER_ACCOUNT_NOT_REGISTER);
            }
            if (findUser.getNickname() != null && findUser.getNickname().equals(user.getNickname())) {
                return new RespEntity(RespCode.USER_NICKNAME_NOT_REGISTER);
            }
        }

        String password = user.getPassword();
        String encryptPassword = ProjectUtil.getEncryptPassword(password);
        user.setEncryptPassword(encryptPassword);

        String ip = IPUtil.getIp(ProjectUtil.getRequest());
        user.setRegisterIp(ip);

        //数据添加
        User newUser = new User();
        newUser.setAccount(user.getAccount());
        newUser.setNickname(user.getNickname());
        newUser.setEncryptPassword(user.getEncryptPassword());
        newUser.setPassword("111111");//无意义，为了骗jpa的bug
        newUser.setRegisterIp(user.getRegisterIp());

        User result = userService.save(newUser);
        return RespUtil.success(result);
    }

    @RequestMapping("login")
    @NoAuthentication
    public RespEntity login(@Validated({GroupUpdate.class}) User user, BindingResult bindingResult, String kaptcha) throws Exception {

        //请求参数规则校验
        //验证码校验
        if(!ProjectUtil.captchaValid(kaptcha)){
            return new RespEntity(RespCode.CAPTCHA_ERROR);
        }

        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                return RespUtil.fail(error.getDefaultMessage());
            }
        }

            //请求参数规则校验
            if (bindingResult.hasErrors()) {
                for (ObjectError error : bindingResult.getAllErrors()) {
                    return RespUtil.fail(error.getDefaultMessage());
                }
            }

            //1.查询帐户
            User findUser = userService.findByAccount(user.getAccount());
            if (user != null) {
                //2.校验密码
                String password = findUser.getEncryptPassword();
                String encryptPassword = ProjectUtil.getEncryptPassword(user.getPassword());
                if (password.equals(encryptPassword)) {
                    //3.放入session中
                    ProjectUtil.setLoginUser(findUser);
                    return RespUtil.success(null);
                }
            }
            return new RespEntity(RespCode.USER_ACCOUNT_OR_PASSWORD_ERROR);
        }
    }