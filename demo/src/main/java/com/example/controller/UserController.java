package com.example.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.tool.ImageToBase64Converter;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @Resource
    UserService service;

    @PostMapping(value = "/login")
    public String login(@ModelAttribute User user, Model model, HttpSession session){
        user = service.getUserByUsernameAndPwd(user.getUsername(), user.getPassword());
        if(user != null){
            model.addAttribute("user", user);
            session.setAttribute("user", user);
            return "redirect:homepage";
        }else {
            return "redirect:/";
        }
    }

    @PostMapping(value = "/userInfoUpload")
    public String uploadUserInfo(String nickname, int age, String phone, String social, String bio, Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        service.uploadUserInfo(nickname, age, phone, social, bio, user.getUid());
        if(nickname!=null) user.setNickname(nickname);
        if(age>0) user.setAge(age);
        if(social!=null) user.setSocial(social);
        if(bio!=null) user.setBio(bio);
        user.setPhone(phone);session.setAttribute("user", user);
        return "redirect:Personal";
    }
}
