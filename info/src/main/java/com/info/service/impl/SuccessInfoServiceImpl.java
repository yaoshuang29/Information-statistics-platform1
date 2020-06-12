package com.info.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.info.advice.InfoException;
import com.info.advice.PageResult;
import com.info.entity.Message;
import com.info.entity.Report;
import com.info.entity.Student;
import com.info.entity.SuccessInfo;
import com.info.enums.ExceptionEnum;
import com.info.mapper.MessageMapper;
import com.info.mapper.ReportMapper;
import com.info.mapper.StudentMapper;
import com.info.mapper.SuccessInfoMapper;
import com.info.service.SuccessInfoService;
import com.info.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


@Service
public class SuccessInfoServiceImpl implements SuccessInfoService {

    @Autowired
    private SuccessInfoMapper successInfoMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private StudentMapper studentMapper;


    @Override
    @Transactional
    public void addSuccessInfo(SuccessInfo info,Long id,Long mid) {
        Message message = messageMapper.selectByPrimaryKey(mid);
        info.setCreateTime(DateUtils.getCurrentTime());
        info.setId(System.currentTimeMillis());
        info.setSid(id);
        info.setState(0);
        info.setInfo_id(message.getInfo_id());
        int i = successInfoMapper.insert(info);
        if(i!=1)
        {
            throw new InfoException(ExceptionEnum.SUCCESS_INFO_SAVE_ERROR);
        }

        //改变信息状态
        messageMapper.updateState(mid,id);

    }

    @Override
    public PageResult<SuccessInfo> findAll(Integer page, Integer size, String keyword, Long tid, String date) {
        PageHelper.startPage(page,size);
        if(StringUtils.isEmpty(keyword))
        {
            keyword="%%";
        }
        else{
            keyword="%"+keyword+"%";
        }
        if(StringUtils.isEmpty(date))
        {
            date="%%";
        }
        else{
            date="%"+date+"%";
        }
        List<SuccessInfo> successInfos= successInfoMapper.findAllByPaging(keyword,tid,date);
        PageInfo<SuccessInfo> pageInfo=new PageInfo<SuccessInfo>(successInfos);

        return new PageResult<>(pageInfo.getTotal(),pageInfo.getPages(),pageInfo.getList());
    }

    @Override
    public SuccessInfo findDetailById(Long suid, Long sid) {
        SuccessInfo successInfo=successInfoMapper.findDetail(suid,sid);
        if(successInfo==null)
        {
            throw new InfoException(ExceptionEnum.SUCCESS_INFO_NOT_FOUND);
        }
        return successInfo;
    }

    @Override
    public void taskIntoStudent(Long sid, Long mid, Long info_id) {
        SuccessInfo successInfo=new SuccessInfo();
        successInfo.setSid(sid);
        successInfo.setId(info_id);
        messageMapper.updateStateZero(mid,sid);
        int i = successInfoMapper.delete(successInfo);
        if(i!=1)
        {
            throw new InfoException(ExceptionEnum.SUCCESS_INFO_DELETE_ERROR);
        }
    }

    @Override
    @Transactional
    public void confirmInfo(Long info_id, Long sid) {
        //改变状态
        successInfoMapper.updateById(info_id,sid);
        //根据id 和sid查询详情
        SuccessInfo detail = successInfoMapper.findDetail(info_id, sid);
        if(detail==null)
        {
            throw new InfoException(ExceptionEnum.SUCCESS_INFO_NOT_FOUND);
        }
        //将详情信息保存
        Report report=new Report();
        BeanUtils.copyProperties(detail,report);
        //查询学生的辅导员id和班级id
        Example example=new Example(Student.class);
        example.selectProperties("tid","cid");
        example.and().andEqualTo("id",sid);
        Student student1 = studentMapper.selectOneByExample(example);
        if(student1==null)
        {
            throw new InfoException(ExceptionEnum.USER_NOT_FOUND);

        }        report.setTid(student1.getTid());
        report.setCid(student1.getCid());
        //将数据封装完的数据保存
        reportMapper.insert(report);
    }

}
