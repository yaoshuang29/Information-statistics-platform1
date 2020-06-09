package com.info.entity;

import com.sun.javafx.beans.IDProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "tb_info_template")
public class InfoTemplate {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;  //模版id
    private String templateContent; // 模版内容
    private String createTime; //创建时间
    private Integer state;  //模版状态 1为使用   0为不使用
    private String title; //模版标题
    private String Data;
}
