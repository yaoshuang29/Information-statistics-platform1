package com.info.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Table(name = "tb_classes")
public class Classes {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String className;
    private Long tid;

}
