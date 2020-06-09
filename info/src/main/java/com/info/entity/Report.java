package com.info.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Table(name ="tb_report")
public class Report {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String templateContent;
    private String title;
    private String result;
    private String createTime;
    private Long sid;
    private Long tid;
    private Long cid;

    @Transient
    private Student student;

    @Transient
    private Teacher teacher;

    @Transient
    private Classes classes;

}
