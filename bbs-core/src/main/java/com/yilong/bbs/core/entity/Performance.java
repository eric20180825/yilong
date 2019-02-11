package com.yilong.bbs.core.entity;

import lombok.Data;

import javax.persistence.Entity;

/**
 * 接口调用超时记录表
 */
@Entity
@Data
public class Performance extends BaseEntity {

    private String apiname;
    private String args;
    private Long consumeTime;//毫秒
}
