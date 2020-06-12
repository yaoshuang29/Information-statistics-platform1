package com.info.service.impl;

import com.info.advice.InfoException;
import com.info.entity.InfoTemplate;
import com.info.entity.Message;
import com.info.entity.Student;
import com.info.entity.Teacher;
import com.info.enums.ExceptionEnum;
import com.info.mapper.InfoTemplateMapper;
import com.info.mapper.MessageMapper;
import com.info.mapper.StudentMapper;
import com.info.mapper.TeacherMapper;
import com.info.service.MessageService;
import com.info.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.beans.Transient;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private InfoTemplateMapper infoTemplateMapper;


    @Override
    @Transient
    public void releaseInfo(String msg,Long id) {
        //查询辅导员当前使用的模版id
        Long info_id=teacherMapper.selectInfoByTid(id);
        //保存用户提示信息
        Message message=new Message();
        message.setId(System.currentTimeMillis());
        message.setMsg(msg);
        message.setCreateTime(DateUtils.getCurrentTime());
        message.setTid(id);
        message.setInfo_id(info_id);
        int i = messageMapper.insert(message);
        if(i!=1)
        {
            throw new InfoException(ExceptionEnum.MESSAGE_SAVE_ERROR);
        }
        //保存辅导员下学生与提示信息
        Student student =new Student();
        student.setTid(id);
        List<Student> students = studentMapper.select(student);
        if(CollectionUtils.isEmpty(students)){

            throw new InfoException(ExceptionEnum.USER_NOT_FOUND);
        }
        List<Long> idList = students.stream().map(Student::getId).collect(Collectors.toList());
        for (Long sid : idList) {
            messageMapper.insertStudentAndMessage(sid,message.getId());
            //判断模版与学生id是否已经有了
            Student student1=infoTemplateMapper.findStudentAndInfo(sid,info_id);
            if(student1==null)
            {
                //保存模版与学生id
                studentMapper.insertStudentAndInfo(sid,info_id);
            }

        }


    }

    @Override
    public List<Message> findAllMessageById(Long id) {

       List<Message> messages= messageMapper.findAllByStuId(id);
        if(CollectionUtils.isEmpty(messages))
        {
            throw new InfoException(ExceptionEnum.MESSAGE_NOT_FOUND);
        }
        return messages;
    }

    @Override
    public InfoTemplate getInfoByUid(Long uid) {
        Long info_id=infoTemplateMapper.findByUid(uid);
        if(info_id==null)
        {
            throw new InfoException(ExceptionEnum.TEMPLATE_NOT_FOUND);
        }
        Example example=new Example(InfoTemplate.class);
        example.selectProperties("title");
        example.and().andEqualTo("id",info_id);
        InfoTemplate infoTemplate = infoTemplateMapper.selectOneByExample(example);
        if(infoTemplate==null)
        {
            throw new InfoException(ExceptionEnum.TEMPLATE_NOT_FOUND);
        }
        return infoTemplate;
    }
}
