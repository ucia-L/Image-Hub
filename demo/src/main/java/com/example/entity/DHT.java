package com.example.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class DHT {
    int uid;
    int pid;
    String type;
}
