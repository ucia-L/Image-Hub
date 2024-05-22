package com.example.controller;

import com.example.service.StudentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Resource
    StudentService service;


    @GetMapping("/")
    public String getInfo(@RequestParam int sid, Model model){
        System.out.println("sid: " + service.getStudentBySid(sid));
        model.addAttribute("student", service.getStudentBySid(sid));
        return "index";
    }
}
