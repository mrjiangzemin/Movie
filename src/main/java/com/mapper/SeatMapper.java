package com.mapper;

import com.entity.Seat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SeatMapper {
    int deleteByPrimaryKey(Integer seatId);

    int insert(Seat record);

    int insertSelective(Seat record);

    Seat selectByPrimaryKey(Integer seatId);

    int updateByPrimaryKeySelective(Seat record);

    int updateByPrimaryKey(Seat record);

    List<Seat> selectBySchedule(@Param("schedule") String schedule);
}