package com.info.mapper;

import com.info.entity.Teacher;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TeacherMapper extends Mapper<Teacher> {

    Teacher findById(Long id);

    //分页模糊查询用户的信息
    List<Teacher> findTeacherByKeyword(String keyword);

    void stateSwitch(String flag, Long uid);

    @Select("select info_id from tb_info_teacher where tid=#{id}")
    Long selectInfoByTid(Long id);

}
