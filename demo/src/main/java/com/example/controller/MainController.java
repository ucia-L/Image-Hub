package com.example.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.entity.DHT;
import com.example.entity.Painting;
import com.example.entity.User;
import com.example.service.LoggingService;
import com.example.service.PaintingService;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Controller
public class MainController {
    @Resource
    PaintingService paintingService;
    @Resource
    UserService userService;
    @Resource
    LoggingService loggingService;

    @GetMapping ("/")
    public String index(HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user != null) {
            session.removeAttribute("user");
        }
        return "login";
    }

    @GetMapping("/homepage")
    public String homepage(Model model, HttpSession session){
        List<Painting> paintings;
        if(session.getAttribute("paintings")!=null) {
            paintings = (List<Painting>) session.getAttribute("paintings");
        }
        else paintings = paintingService.getAllPaintings();
        User user = (User) session.getAttribute("user");
        // 当前用户有三者操作的进行记录boolean->true
        List<DHT> dhtList = userService.getDHTByUid(user.getUid());
        if(session.getAttribute("newPainting")!=null){
            List<Painting> paintingList = (List<Painting>) session.getAttribute("newPainting");
            for (Painting newPainting : paintingList) {
                for (int i = 0; i < paintings.size(); i++) {
                    Painting oldPainting = paintings.get(i);
                    if (oldPainting.getPid() == newPainting.getPid()) {
                        paintings.set(i, newPainting); // 用 newPainting 替换掉 paintings 中相同 pid 的元素
                        break; // 已经找到并替换了元素，跳出内层循环
                    }
                }
            }
            // session.removeAttribute("newPainting");
            session.removeAttribute("paintings");
        }
        checkDHT(paintings, dhtList, user.getUid());
        model.addAttribute("user", user);
        model.addAttribute("paintings", paintings);
        if(user.getType() == 1) return "adminIndex";
        else return "index";
    }

    @PostMapping("/PHomepage")
    public String mainSearch(@RequestParam("title") String title, HttpSession session, Model model){
        List<Painting> paintings = paintingService.getPaintingByTitle(title);
        User user = (User) session.getAttribute("user");
        // 当前用户有三者操作的进行记录boolean->true
        List<DHT> dhtList = userService.getDHTByUid(user.getUid());

        checkDHT(paintings, dhtList, user.getUid());
        model.addAttribute("user", user);
        model.addAttribute("paintings", paintings);
        session.setAttribute("paintings", paintings);
        return "redirect:homepage";
    }
    @GetMapping("/OneHomepage")
    public String oneSearch(@RequestParam("type") String type, HttpSession session, Model model){
        List<Painting> paintings = paintingService.getPaintingByType(type);
        User user = (User) session.getAttribute("user");
        // 当前用户有三者操作的进行记录boolean->true
        List<DHT> dhtList = userService.getDHTByUid(user.getUid());

        checkDHT(paintings, dhtList, user.getUid());
        model.addAttribute("user", user);
        model.addAttribute("paintings", paintings);
        session.setAttribute("paintings", paintings);
        return "redirect:homepage";
    }

    @GetMapping("/thumbUp")
    public String thumbUp(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Painting> paintings = paintingService.getAllPaintingByType(user.getUid(), "thumbUp");
        if(session.getAttribute("newPainting")!=null){
            List<Painting> paintingList = (List<Painting>) session.getAttribute("newPainting");
            for (Painting newPainting : paintingList) {
                for (int i = 0; i < paintings.size(); i++) {
                    Painting oldPainting = paintings.get(i);
                    if (oldPainting.getPid() == newPainting.getPid()) {
                        paintings.set(i, newPainting); // 用 newPainting 替换掉 paintings 中相同 pid 的元素
                        break; // 已经找到并替换了元素，跳出内层循环
                    }
                }
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("paintings", paintings);
        return "thumbUp";
    }
    @GetMapping("/heart")
    public String heart(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Painting> paintings = paintingService.getAllPaintingByType(user.getUid(), "heart");
        if(session.getAttribute("newPainting")!=null){
            List<Painting> paintingList = (List<Painting>) session.getAttribute("newPainting");
            for (Painting newPainting : paintingList) {
                for (int i = 0; i < paintings.size(); i++) {
                    Painting oldPainting = paintings.get(i);
                    if (oldPainting.getPid() == newPainting.getPid()) {
                        paintings.set(i, newPainting); // 用 newPainting 替换掉 paintings 中相同 pid 的元素
                        break; // 已经找到并替换了元素，跳出内层循环
                    }
                }
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("paintings", paintings);
        return "heart";
    }
    @GetMapping("/PDownload")
    public String PDownload(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Painting> paintings = paintingService.getAllPaintingByType(user.getUid(), "download");
        if(session.getAttribute("newPainting")!=null){
            List<Painting> paintingList = (List<Painting>) session.getAttribute("newPainting");
            for (Painting newPainting : paintingList) {
                for (int i = 0; i < paintings.size(); i++) {
                    Painting oldPainting = paintings.get(i);
                    if (oldPainting.getPid() == newPainting.getPid()) {
                        paintings.set(i, newPainting); // 用 newPainting 替换掉 paintings 中相同 pid 的元素
                        break; // 已经找到并替换了元素，跳出内层循环
                    }
                }
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("paintings", paintings);
        return "PDownload";
    }
    @GetMapping("/working")
    public String working(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Painting> paintings = paintingService.getPaintingByUid(user.getUid());
        if(session.getAttribute("newPainting")!=null){
            List<Painting> paintingList = (List<Painting>) session.getAttribute("newPainting");
            for (Painting newPainting : paintingList) {
                for (int i = 0; i < paintings.size(); i++) {
                    Painting oldPainting = paintings.get(i);
                    if (oldPainting.getPid() == newPainting.getPid()) {
                        paintings.set(i, newPainting); // 用 newPainting 替换掉 paintings 中相同 pid 的元素
                        break; // 已经找到并替换了元素，跳出内层循环
                    }
                }
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("paintings", paintings);

        return "working";
    }

    @GetMapping("/Personal")
    public String Person(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "personal";
    }

    @GetMapping("/LoggingInfo")
    public String LoggingInfo(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("logging", loggingService.getAllLogs());
        return "logging";
    }

    @ResponseBody
    @PostMapping(value = "/saveTemp", produces = "application/json")
    public String saveImgInTemp(@RequestParam MultipartFile file, ModelAndView modelAndView) throws IOException {
        String[] allowedExtensions = { ".jpg", ".jpeg", ".png", ".gif", ".bmp", ".tiff", ".tif", ".webp", ".heic", ".heif" };

        JSONObject object = new JSONObject();
        // 生成唯一的文件名，确保在临时目录中不会发生文件名冲突
        String originalFilename = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);
        String uniqueFileName = generateUniqueFileName(originalFilename);

        boolean isImage = false;
        for (String extension : allowedExtensions) {
            if (fileExtension.equalsIgnoreCase(extension)) {
                isImage = true;
                break;
            }
        }
        if (!isImage) {
            // 文件不是图片格式，返回错误信息
            object.put("success", false);
            object.put("message", "不支持的图片格式");
            return object.toString();
        }

        // 获取临时目录路径
        String tempDirectoryPath = "D:\\uploadFromOther\\temp";

        // 创建临时文件
        File tempFile = new File(tempDirectoryPath, uniqueFileName + fileExtension);
        file.transferTo(tempFile);
        // 检查文件是否存在并且大小大于0来判断是否上传成功
        if (tempFile.exists() && tempFile.length() > 0) {
            object.put("success", true);
            try {
                // 将图片转换为 Base64 格式
                byte[] fileContent = FileUtils.readFileToByteArray(tempFile);
                String base64Image = Base64.getEncoder().encodeToString(fileContent);

                // 获取文件名（不包含后缀）
                String fileNameWithoutExtension = tempFile.getName().replaceFirst("[.][^.]+$", "");

                // 获取文件大小
                long fileSizeInBytes = tempFile.length();
                // 获取格式化后的文件大小
                String formattedFileSize = formatFileSize(fileSizeInBytes);

                // 将信息放入 JSON 对象
                object.put("image", base64Image);
                object.put("fileName", fileNameWithoutExtension);
                object.put("fileSize", formattedFileSize);
                object.put("fileFormat", fileExtension);
            } catch (IOException e) {
                // 处理异常
                object.put("success", false);
                object.put("message", "Failed to read image file");
                return object.toString();
            }
        } else {
            object.put("success", false);
            object.put("message", "文件上传失败，请稍后再试！");
        }
        return object.toString();
    }

    // 获取文件扩展名（后缀）
    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    // 生成唯一的文件名
    private String generateUniqueFileName(String originalFilename) {
        // 在文件名中使用时间戳和文件名的哈希值以确保唯一性
        String uniqueID = UUID.randomUUID().toString();
        String fileHash = DigestUtils.sha256Hex(originalFilename + uniqueID);
        return "temp_" + fileHash;
    }

    public String formatFileSize(long fileSizeInBytes) {
        double fileSize = fileSizeInBytes;
        String[] units = new String[]{"B", "KB", "MB"};

        int unitIndex = 0;
        while (fileSize >= 1024 && unitIndex < units.length - 1) {
            fileSize /= 1024;
            unitIndex++;
        }

        return String.format("%.2f %s", fileSize, units[unitIndex]);
    }

    public List<Painting> checkDHT(List<Painting> paintings, List<DHT> dhtList, int uid){
        for (Painting painting: paintings){
            if(painting.getUid()==uid){
                // 判断dhtList里是否有pid是等于painting的pid同时dhtList的type是" "
                boolean isThumbUp = dhtList.stream()
                        .anyMatch(dht -> dht.getPid() == painting.getPid() && dht.getType().equals("thumbUp"));

                if (isThumbUp) {
                    painting.setThumbUp(true);
                }
                boolean isHeart = dhtList.stream()
                        .anyMatch(dht -> dht.getPid() == painting.getPid() && dht.getType().equals("heart"));

                if (isHeart) {
                    painting.setHeart(true);
                }
            }
        }
        return paintings;
    }
}
