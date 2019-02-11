package com.yilong.bbs.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

//    @RequestMapping("register")
//    @NoAuthentication
//    JSR提供的校验注解：
//    @Null   被注释的元素必须为 null
//    @NotNull    被注释的元素必须不为 null
//    @AssertTrue     被注释的元素必须为 true
//    @AssertFalse    被注释的元素必须为 false
//    @Min(value)     被注释的元素必须是一个数字，其值必须大于等于指定的最小值
//    @Max(value)     被注释的元素必须是一个数字，其值必须小于等于指定的最大值
//    @DecimalMin(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值
//    @DecimalMax(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值
//    @Size(max=, min=)   被注释的元素的大小必须在指定的范围内
//    @Digits (integer, fraction)     被注释的元素必须是一个数字，其值必须在可接受的范围内
//    @Past   被注释的元素必须是一个过去的日期
//    @Future     被注释的元素必须是一个将来的日期
//    @Pattern(regex=,flag=)  被注释的元素必须符合指定的正则表达式
//
//
//    Hibernate Validator提供的校验注解：
//    @NotBlank(message =)   验证字符串非null，且长度必须大于0
//    @Email  被注释的元素必须是电子邮箱地址
//    @Length(min=,max=)  被注释的字符串的大小必须在指定的范围内
//    @NotEmpty   被注释的字符串的必须非空
//    @Range(min=,max=,message=)  被注释的元素必须在合适的范围内
@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @CreatedDate
    @JsonFormat(pattern="yyyy-MM-dd  HH:mm:ss" ,  timezone="GMT+8")
    private Date createDate;
    @JsonIgnore
    @LastModifiedDate
    @JsonFormat(pattern="yyyy-MM-dd  HH:mm:ss" ,  timezone="GMT+8")
    private Date updateDate;
    @JsonIgnore
    @CreatedBy
    private String createBy;//创建者
    @JsonIgnore
    @LastModifiedBy
    private String updateBy;//最后修改者
}
