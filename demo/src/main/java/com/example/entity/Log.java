package com.example.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class Log {
    int id;
    String operator;
    String operation;
    String target;
    Date dateTime;
}
