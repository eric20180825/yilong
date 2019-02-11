package com.yilong.bank.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
//如果索引未自动建立，是因为索引字段长度加起来太长，设置length长度解决
@Table(name = "bank_zhaoshang",uniqueConstraints=@UniqueConstraint(columnNames={"tradeDate", "tradeMoney","remark"}))
@Data
public class ZhaoshangBank extends BaseEntity{

    @Column(length = 20)
    private String tradeDate;//交易时间
    @Column(length = 20)
    private String tradeMoney;
    private String balance;
    private String type;
    @Column(length = 50)
    private String remark;

    private String accountUid;
    private String subAccountNo;
}
