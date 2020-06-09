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

    /**
     * 更新座位状态
     * @param seatId
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    void updateSeatStatus(String seatId);
}
