package com.service;

import com.entity.Seat;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ISeatService {
    /**
     * 根据schedule查询相关座位表
     * @param schedule
     * @return
     */
    @Transactional(readOnly = true)
    List<Seat> getSeatsBySchedule(String schedule);
}
