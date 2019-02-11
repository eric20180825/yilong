package com.yilong.bank.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
//@Table(name="page_config",uniqueConstraints=@UniqueConstraint(columnNames="currentMonth"))
@Data
public class Page{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String currentMonth;
    private Integer pageIndex;
}