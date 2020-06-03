package com.mapper;

import com.entity.FeeBase;

public interface FeeBaseMapper {
    int insert(FeeBase record);

    int insertSelective(FeeBase record);
}