package com.yilong.bbs.core.service;

import com.yilong.bbs.core.entity.User;
import com.yilong.bbs.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserService  {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByAccount(String account) {
        return userRepository.findByAccount(account);
    }

    public int register(User newUser) {
        //原生SQL要自己写时间，操作人
//        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Date today = new Date();

        return userRepository.register(newUser.getAccount(),newUser.getPassword(),newUser.getNickname(),today,newUser.getRegisterIp());
    }

    public List<User> findByAccountOrNickname(String account, String nickname) {
        return userRepository.findByAccountOrNickname(account, nickname);
    }
}
