package com.info.mapper;

import com.info.entity.SuccessInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SuccessInfoMapper extends Mapper<SuccessInfo>, DeleteByIdListMapper<SuccessInfo,Long> {


    List<SuccessInfo> findAllByPaging(@Param("keyword") String keyword, @Param("tid") Long tid,@Param("date") String date);

    @Select("select * from tb_success_info where id=#{suid} AND sid=#{sid}")
    SuccessInfo findDetail(@Param("suid") Long suid, @Param("sid") Long sid);

    @Update("update tb_success_info set state=1 WHERE id=#{info_id} And sid=#{sid}")
    void updateById(@Param("info_id") Long info_id,@Param("sid") Long sid);

    @Update("update tb_success_info set flag=0 where info_id=#{id}")
    void updateSuccessInfoState(Long id);
}
