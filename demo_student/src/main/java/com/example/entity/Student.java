package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Student {
    int sid;
    String s_name;
    String sex;
}
