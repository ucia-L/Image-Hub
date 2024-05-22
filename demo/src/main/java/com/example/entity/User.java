package com.example.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class User {
    int uid;
    String username;
    String password;
    String nickname;
    String avatar;
    int downloadCount = 0;
    int heartCount = 0;
    int thumbUpCount = 0;
    String birthday;
    int age;
    String phone;
    String social;
    String bio;
    int type;
}
