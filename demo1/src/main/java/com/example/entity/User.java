package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;


// @Component
// @RequestScope
@Data
@AllArgsConstructor
public class User {
    String username;
    String password;
}