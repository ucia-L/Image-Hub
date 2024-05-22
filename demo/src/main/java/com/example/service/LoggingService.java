package com.example.service;

import com.example.entity.Log;

import java.util.Date;
import java.util.List;

public interface LoggingService {
    boolean addLogging(String operator, String operation, String target, Date dateTime);
    List<Log> getAllLogs();
}
