package com.yilong.bbs.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yilong.bbs.core.common.validator.GroupCreate;
import com.yilong.bbs.core.common.validator.GroupUpdate;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name="user",uniqueConstraints = {@UniqueConstraint(columnNames="account"),@UniqueConstraint(columnNames = "nickname")})
//通过@GroupSequence指定验证顺序：先验证A分组，如果有错误立即返回而不会验证B分组，GroupSequence只能运用在Type（也就是类）上,
//且对应类的Object.class是GroupSequence列表中的一部分，一般放在最后。
@GroupSequence({GroupCreate.class, GroupUpdate.class, User.class})
public class User extends BaseEntity {

    @NotBlank(message = "帐户必填",groups = {GroupCreate.class, GroupUpdate.class})
    @Length(min=3,max = 15,message = "帐户长度3-15位",groups = {GroupCreate.class, GroupUpdate.class})
//    @Pattern(regexp = "^[a-zA-Z]([_a-zA-Z\\d~`!@#$%^&*()-+=,<.>/?;:'\"[{]}\\|]*)$",message="帐号必须以字母开头，包括下划线,字母，数字,常用字符")
    private String account;

    @NotBlank(message = "密码必填",groups = {GroupCreate.class, GroupUpdate.class})
//    @Pattern(regexp = "^([\\w~`!@#$%^&*()-+=,<.>/?;:'\"[{]}\\|]*)$",message="密码只支持下划线,字母,数字,常用字符")
    @Length(min=6,max = 15,message = "密码长度6-15位",groups = {GroupCreate.class, GroupUpdate.class})
    @Transient  //不建表
    @JsonIgnore   //不回显
    private String password;

    @JsonIgnore
    private String encryptPassword;

    @NotBlank(message = "昵称必填",groups = {GroupCreate.class})
    @Size(min=1,max = 6,message = "昵称长度1-6位",groups = {GroupCreate.class})
    private String nickname;

    private String registerIp;
    private String loginIp;
}
