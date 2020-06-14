package com.info.mapper;

import com.info.entity.Message;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MessageMapper extends Mapper<Message>, DeleteByIdListMapper<Message,Long> {

    @Insert("insert into tb_message_student values(#{mid},#{sid},0)")
    void insertStudentAndMessage(@Param("sid") Long sid, @Param("mid") Long id);

    @Select("select m.id,m.msg,ms.state,m.create_time from tb_message_student ms,tb_message m where ms.mid=m.id AND sid=#{id}")
    List<Message> findAllByStuId(Long id);

    @Update("update tb_message_student set state=1 where sid=#{sid} AND mid=#{mid}")
    void updateState(@Param("mid") Long mid,@Param("sid") Long id);

    @Update("update tb_message_student set state=0 where sid=#{sid} AND mid=#{mid}")
    void updateStateZero(@Param("mid") Long mid,@Param("sid") Long id);

    @Delete("delete from tb_message_student where mid=#{mid}")
    void deleteStudentAndMessage(Long mid);
}
