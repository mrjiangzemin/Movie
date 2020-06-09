package com.mapper;

import com.entity.NCUUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NCUUserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(NCUUser record);

    int insertSelective(NCUUser record);

    NCUUser selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(NCUUser record);

    int updateByPrimaryKey(NCUUser record);

    List<NCUUser> selectNCUUserByOpenId(@Param("openId")String openId);
}