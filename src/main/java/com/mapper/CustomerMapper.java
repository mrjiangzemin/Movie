package com.mapper;

import com.entity.Customer;

public interface CustomerMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);
}