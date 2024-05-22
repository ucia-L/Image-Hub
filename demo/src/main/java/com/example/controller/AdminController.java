package com.example.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.entity.User;
import com.example.service.LoggingService;
import com.example.service.PaintingService;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class AdminController {
    @Resource
    PaintingService paintingService;
    @Resource
    LoggingService loggingService;
    @Resource
    UserService userService;

    @ResponseBody
    @PostMapping(value = "/delete", produces = "application/json")
    public String deletePainting(@RequestParam("pid") int pid, HttpSession session){
        JSONObject object = new JSONObject();
        User user = (User) session.getAttribute("user");
        if (paintingService.deletePaintingOfPid(pid)){
            loggingService.addLogging(user.getNickname(), "delete", String.valueOf(pid), new Date());
            if(paintingService.deleteDHTOfPid(pid)) object.put("success", true);
            else object.put("success", false);
        }else {
            object.put("success", false);
        }
        return object.toString();
    }

    @GetMapping("/userManagement")
    public String userManagement(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        List<User> userList = userService.getAllUsersExceptMe(user.getUid());
        model.addAttribute("userList", userList);
        model.addAttribute("user", user);
        return "usersManagement";
    }

    @ResponseBody
    @PostMapping(value = "/deleteUser", produces = "application/json")
    public String deleteUserByUid(@RequestParam("uid")int uid, HttpSession session){
        User user = (User) session.getAttribute("user");
        JSONObject object = new JSONObject();
        object.put("success", false);
        if(userService.deleteUserByUid(uid)){
            loggingService.addLogging(user.getNickname(), "delete", String.valueOf(uid), new Date());
            object.put("success", true);
        }
        return object.toString();
    }

    @PostMapping("/uploadUserType")
    public String uploadUserType(String uids, HttpSession session){
        User user = (User) session.getAttribute("user");
        List<String> target = List.of(uids.split(","));
        List<Integer> tar = new ArrayList<>();
        for(String t : target){
            tar.add(Integer.parseInt(t));
            userService.uploadUserType(Integer.parseInt(t));
            loggingService.addLogging(user.getNickname(), "upload", t, new Date());
        }
        return "redirect:userManagement";
    }
}
