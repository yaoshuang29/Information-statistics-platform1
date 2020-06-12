package com.info.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.info.advice.InfoException;
import com.info.advice.PageResult;
import com.info.entity.*;
import com.info.enums.ExceptionEnum;
import com.info.mapper.*;
import com.info.service.InfoTemplateService;
import com.info.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.sound.sampled.Line;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InfoTemplateServiceImpl implements InfoTemplateService {
    @Autowired
    private InfoTemplateMapper infoTemplateMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private SuccessInfoMapper successInfoMapper;
    @Autowired
    private ReportMapper reportMapper;

    /**
     * 查询模版信息
     * @return
     * @param page
     * @param size
     */
    @Override
    public PageResult<InfoTemplate> findAll(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        //创建example
        Example example=new Example(InfoTemplate.class);
        //只查询指定字段
        example.selectProperties("id","createTime","state","title");
        //查询
        List<InfoTemplate> infoTemplates = infoTemplateMapper.selectByExample(example);
        //非空校验
        if(CollectionUtils.isEmpty(infoTemplates))
        {
            throw new InfoException(ExceptionEnum.TEMPLATE_NOT_FOUND);
        }
        PageInfo<InfoTemplate> info=new PageInfo<InfoTemplate>(infoTemplates);
        return new PageResult<InfoTemplate>(info.getTotal(),info.getPages(),info.getList());
    }

    /**
     * 保存模版信息
     * @param template
     */
    @Override
    @Transactional
    public void saveTemplate(InfoTemplate template) {
        //封装数据
        template.setId(System.currentTimeMillis());
        template.setState(0);
        template.setCreateTime(DateUtils.getCurrentTime());
        //保存
        int i = infoTemplateMapper.insert(template);
        //校验
        if(i!=1)
        {
            throw new InfoException(ExceptionEnum.TEMPLATE_SAVE_ERROR);
        }
    }

    /**
     * 根据id查询模版信息
     * @param id
     * @return
     */
    @Override
    public InfoTemplate findById(Long id) {
        //封装数据
        InfoTemplate infoTemplate=new InfoTemplate();
        infoTemplate.setId(id);
        //执行查询
        InfoTemplate template = infoTemplateMapper.selectOne(infoTemplate);
        //非空验证
        if(template==null)
        {
            throw new InfoException(ExceptionEnum.TEMPLATE_NOT_FOUND);
        }
        return template;
    }

    /**
     * 根据id删除模版信息
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        //获取提示信息中与模版有绑定关系的信息id集合
        Message message=new Message();
        message.setInfo_id(id);
        List<Message> messageList = messageMapper.select(message);
        // if(CollectionUtils.isEmpty(messageList))
        // {
        //     throw new InfoException(ExceptionEnum.MESSAGE_NOT_FOUND);
        // }
        //将所有messageList中的id抽取成一个集合
        List<Long> messageIds = messageList.stream().map(Message::getId).collect(Collectors.toList());
        //删除中间表中的数据
        for (Long messageId : messageIds) {
            messageMapper.deleteStudentAndMessage(messageId);
        }
        //删除信息表中的指定数据
        messageMapper.deleteByIdList(messageIds);
        //删除 模版与学生的中间表数据
        infoTemplateMapper.deleteInfoAndStudent(id);
        //删除模版与老师的中间表数据
        infoTemplateMapper.deleteTeacherAll();
        //删除老师最终确认数据
        SuccessInfo successInfo=new SuccessInfo();
        successInfo.setInfo_id(id);
        List<SuccessInfo> infoList = successInfoMapper.select(successInfo);
        // if(CollectionUtils.isEmpty(infoList))
        // {
        //     throw new InfoException(ExceptionEnum.SUCCESS_INFO_NOT_FOUND);
        // }
        //将infoList集合收集id
        List<Long> infoIds = infoList.stream().map(SuccessInfo::getId).collect(Collectors.toList());
        reportMapper.deleteByIdList(infoIds);
        //删除学生曾经提交的历史数据
        successInfoMapper.deleteByIdList(infoIds);
        //封装数据
        InfoTemplate infoTemplate=new InfoTemplate();
        infoTemplate.setId(id);
        //执行删除
        int i = infoTemplateMapper.delete(infoTemplate);
        //校验结果
        if(i!=1)
        {
            throw new InfoException(ExceptionEnum.TEMPLATE_DELETE_ERROR);
        }
    }

    /**
     * 根据id更新模版状态
     * @param id
     */
    @Override
    @Transactional
    public void useTemplate(Long id,Boolean flag) {

        //清空教师与模版绑定
        infoTemplateMapper.deleteTeacherAll();
        //封装数据
        InfoTemplate infoTemplate=new InfoTemplate();
        infoTemplate.setId(id);
        if(flag) //判断状态
        {
            //将所有的模版的状态置为0
            infoTemplateMapper.updateStart();
            infoTemplate.setState(1);
            //将辅导员与模版绑定关系
            List<Teacher> teachers = teacherMapper.selectAll();
            if(CollectionUtils.isEmpty(teachers))
            {
                throw new InfoException(ExceptionEnum.USER_NOT_FOUND);
            }
            //获取所有老师的id
            List<Long> idList = teachers.stream().map(Teacher::getId).collect(Collectors.toList());
            //绑定关系
            for (Long tid : idList) {
                infoTemplateMapper.infoBindTeacher(id,tid);
            }
        }
        else{
            infoTemplate.setState(0);

        }
        //执行更新（只更新封装了的字段，其余的不更新）
        int i = infoTemplateMapper.updateByPrimaryKeySelective(infoTemplate);
        //校验
        if(i!=1){
            throw new InfoException(ExceptionEnum.TEMPLATE_UPDATE_ERROR);
        }

    }

    @Override
    public InfoTemplate reportFillOut( Long mid) {
        //获取提示信息，通过提示信息获取模版id
        Message message = messageMapper.selectByPrimaryKey(mid);
        if(message==null)
        {
            throw new InfoException(ExceptionEnum.MESSAGE_NOT_FOUND);
        }
        //通过模版Id查找到模版
        InfoTemplate infoTemplate = infoTemplateMapper.selectByPrimaryKey(message.getInfo_id());

        if(infoTemplate==null)
        {
            throw new InfoException(ExceptionEnum.TEMPLATE_NOT_FOUND);
        }
        return infoTemplate;

    }
}
