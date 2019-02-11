package com.yilong.bbs.core.repository;

import com.yilong.bbs.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByAccount(String account);

    @Query(nativeQuery = true, value ="insert into user(account,encrypt_password,nickname,create_date,update_date,register_ip) values (:account,:encryptPassword,:nickname,:today,:today,:registerIp)")
    @Modifying
    int register(@Param("account") String account, @Param("encryptPassword") String encryptPassword, @Param("nickname") String nickname, @Param("today") Date createDate, @Param("registerIp") String ip);

    List<User> findByAccountOrNickname(String account, String nickname);
}
