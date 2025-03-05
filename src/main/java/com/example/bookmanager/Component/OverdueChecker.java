package com.example.bookmanager.Component;

import com.example.bookmanager.Mapper.BorrowRecordMapper;
import com.example.bookmanager.Mapper.ReserveRecordMapper;
import com.example.bookmanager.Mapper.UserMapper;
import com.example.bookmanager.Service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OverdueChecker {
    private final BorrowRecordMapper borrowRecordMapper;
    private final ReserveRecordMapper reserveRecordMapper;
    private final UserMapper userMapper;
    private final RedisService redisService;

    @Autowired
    public OverdueChecker(BorrowRecordMapper borrowRecordMapper, ReserveRecordMapper reserveRecordMapper, UserMapper userMapper, RedisService redisService) {
        this.borrowRecordMapper = borrowRecordMapper;
        this.reserveRecordMapper = reserveRecordMapper;
        this.userMapper = userMapper;
        this.redisService = redisService;
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void checkOverdue() {
        System.out.println("start check");
        borrowRecordMapper.getOverdueRecord().forEach(record -> {
            System.out.println("User " + record.getUserId() + " has overdue books");
            userMapper.banUser(record.getUserId());
            borrowRecordMapper.setBorrowStatus(record.getBookId(), record.getUserId(), "OVERDUE");
            redisService.setUserStatus(record.getUserId(), false);
        });
        reserveRecordMapper.getOverdueRecord().forEach(reserveRecord -> {
            userMapper.banUser(reserveRecord.getUserId());
            redisService.setUserStatus(reserveRecord.getUserId(), false);
            reserveRecordMapper.setStatus(reserveRecord.getId(), "OVERDUE");
            reserveRecordMapper.setBorrowDate(reserveRecord.getId(), null);
        });
    }
}
