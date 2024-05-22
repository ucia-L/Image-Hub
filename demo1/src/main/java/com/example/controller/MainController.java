package com.example.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.entity.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;

@Controller
public class MainController {
    /*@Resource
    User user;
    @GetMapping("/test{str}")
    public String hello(@PathVariable String str){
        return user.toString() + " " + str;
    }*/
    @RequestMapping(value = "/")
    public String index(@RequestParam(value = "username", required = false, defaultValue = "321") String username){
        System.out.println(username + "123");
        if(true) throw new RuntimeException("您的氪金力度不足，无法访问！");
        return "index";
    }
    @RequestMapping(value = "/post", method = RequestMethod.POST, params = {"username", "password"})
    public String postHtml(Model model){
        return "post_html";
    }
    @RequestMapping("/index1")
    public String index1(@CookieValue(value = "Idea-3758fd14", required = false) String test, User user){
        System.out.println(user);
        System.out.println("cookie: " + test);
        return "forward:/";
    }
    @RequestMapping("/index2")
    public String index2(){
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("name", "张三");
        object.put("sex", "男");
        array.add(object);
        return array.toString();
    }
    @GetMapping(value = "/tbc", produces = "application/json")
    public User hello(){
        /*User user1 = new User("123", "54135");
        return JSONObject.toJSONString(user1);*/
        return new User("123", "54135");
    }

    @ResponseBody
    @GetMapping("/getData")
    public User login(){
        return new User("username", "312313");
    }

    @RequestMapping("/index3")
    public String index3(){
        return "index3";
    }

    @ResponseBody
    @PostMapping(value = "/login", produces = "application/json")
    public String hello(@RequestBody User user){
        boolean success = "test".equals(user.getUsername()) && "123456".equals(user.getPassword());
        JSONObject object = new JSONObject();
        object.put("success", success);
        return object.toString();
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam MultipartFile file) throws IOException {
        File fileObj = new File("test.png");
        file.transferTo(fileObj);
        System.out.println("用户上传的文件已保存到："+fileObj.getAbsolutePath());
        return "文件上传成功！";
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public void download(HttpServletResponse response){
        response.setContentType("multipart/form-data");
        try(OutputStream stream = response.getOutputStream();
            InputStream inputStream = new FileInputStream("D:\\uploadFromOther\\test.png")){
            IOUtils.copy(inputStream, stream);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
