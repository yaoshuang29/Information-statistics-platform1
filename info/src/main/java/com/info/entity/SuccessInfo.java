package com.info.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Table(name = "tb_success_info")
public class SuccessInfo {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String templateContent;
    private String title;
    private String result;
    private String createTime;
    private Long sid;
    private Integer state;
    private Long info_id;


    @Transient
    private Classes classes;
    @Transient
    private Student student;

    @Transient
    private Message message;

}
