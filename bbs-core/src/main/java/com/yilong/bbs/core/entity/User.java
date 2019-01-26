package com.yilong.bbs.core.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
public class User extends BaseEntity {

    private String account;
    private String password;
    private String nickname;
    private String registerIp;
    @Column(columnDefinition = "tinyint default 1")
    private int isValid;//是否有效, 默认1 有效  0，无效

}
