package com.info.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Table(name = "tb_message")
public class Message {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String msg;
    private String createTime;
    private Long tid;
    private Long info_id;

    @Transient
    private Integer state;
}
