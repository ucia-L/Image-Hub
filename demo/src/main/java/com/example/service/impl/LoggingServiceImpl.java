package com.example.service.impl;

import com.example.entity.Log;
import com.example.mapper.LoggingMapper;
import com.example.service.LoggingService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LoggingServiceImpl implements LoggingService {
    @Resource
    LoggingMapper mapper;
    @Override
    public boolean addLogging(String operator, String operation, String target, Date dateTime) {
        return mapper.addLogging(operator, operation, target, dateTime);
    }

    @Override
    public List<Log> getAllLogs() {
        return mapper.getAllLogs();
    }
}
