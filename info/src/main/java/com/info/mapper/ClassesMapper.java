package com.info.mapper;

import com.info.entity.Classes;
import com.info.entity.Teacher;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.core.parameters.P;
import tk.mybatis.mapper.common.Mapper;

public interface ClassesMapper extends Mapper<Classes> {

    //根据班级名称查询教师信息
    @Select("select id,tid from tb_classes where class_name=#{className}")
    Classes findStudentByStudent(String className);

    //根据教师id删除班级
    @Delete("delete from tb_classes where tid=#{tid}")
    void deleteByTid(Long tid);


}
