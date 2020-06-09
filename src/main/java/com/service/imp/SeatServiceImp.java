package com.service.imp;

import com.entity.Seat;
import com.mapper.SeatMapper;
import com.service.ISeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImp implements ISeatService {

    @Autowired
    private SeatMapper seatMapper;

    @Override
    public List<Seat> getSeatsBySchedule(String schedule) {
        return seatMapper.selectBySchedule(schedule);
    }
}
