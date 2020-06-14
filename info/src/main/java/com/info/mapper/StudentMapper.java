package com.info.mapper;

import com.info.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StudentMapper extends Mapper<Student> {

    //根据id查询用户
    Student findById(Long id);

    //分页模糊查询
    List<Student> findStudent(String keyWord);

    //切换状态
    void stateSwitch(String flag, Long uid);

    //保存学生与模版的关系
    @Insert("insert into tb_info_student values (#{sid},#{id})")
    void insertStudentAndInfo(@Param("sid") Long sid,@Param("id") Long info_id);
}
