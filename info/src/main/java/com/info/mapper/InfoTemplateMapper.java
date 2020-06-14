package com.info.mapper;

import com.info.entity.InfoTemplate;
import com.info.entity.Student;
import com.info.utils.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface InfoTemplateMapper extends BaseMapper<InfoTemplate> {

    @Update("update tb_info_template set state = 0")
    void updateStart();

    @Insert("insert into tb_info_teacher values (#{tid},#{info_id})")
    void infoBindTeacher(@Param("info_id") Long id,@Param("tid") Long tid);

    @Delete("delete from tb_info_teacher")
    void deleteTeacherAll();

    @Select("select sid from tb_info_student where sid=#{sid} And info_id=#{info_id}")
    Student findStudentAndInfo(@Param("sid") Long sid, @Param("info_id") Long info_id);

    @Delete("delete from tb_info_student where info_id=#{id}")
    void deleteInfoAndStudent(Long id);

    @Select("select info_id from tb_info_teacher where tid=#{uid}")
    Long findByUid(Long uid);
}
