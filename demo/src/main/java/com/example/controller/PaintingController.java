package com.example.controller;

import com.example.entity.DHT;
import com.example.entity.Painting;
import com.example.entity.User;
import com.example.service.LoggingService;
import com.example.service.PaintingService;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;


@Controller
public class PaintingController {
    @Resource
    PaintingService paintingService;
    @Resource
    UserService userService;
    @Resource
    LoggingService loggingService;
    @GetMapping("/painting{pid}")
    public String scanPainting(@PathVariable("pid")  String pid, Model model, HttpSession session){
        Painting painting = paintingService.getPaintingByPid(Integer.parseInt(pid));
        User user = (User) session.getAttribute("user");
        // 当前用户有三者操作的进行记录boolean->true
        List<DHT> dhtList = userService.getDHTByUid(user.getUid());
        checkDHT(painting, dhtList, user.getUid());
        model.addAttribute("label", getLabels(painting.getLabel()));
        model.addAttribute("painting", painting);
        model.addAttribute("user", user);
        model.addAttribute("imgUser", userService.getUserByUid(painting.getUid()));
        if(session.getAttribute("newPainting")!=null){
            List<Painting> paintingList = (List<Painting>) session.getAttribute("newPainting");
            for (Painting newPainting : paintingList) {
                if (painting.getPid() == newPainting.getPid()) {
                    painting = newPainting; // 用 newPainting 替换掉 paintings 中相同 pid 的元素
                    session.setAttribute("imgP", painting.getBaseImg64());
                    break; // 已经找到并替换了元素，跳出内层循环
                }
            }
        }
        if(session.getAttribute("imgP")!=null) {
            model.addAttribute("imgP", session.getAttribute("imgP"));
            session.removeAttribute("imgP");
        }
        return "PaintingP";
    }
    @GetMapping("/t")
    public String paintingT(@RequestParam int pid, HttpSession session){
        return "redirect:painting" + pid;
    }
    @GetMapping("/uploadPaintingPage")
    public String uploadPainting(Model model, HttpSession session){
        model.addAttribute("user", session.getAttribute("user"));
        return "uploadPainting";
    }

    @PostMapping("/uploadPainting")
    public String upload(@ModelAttribute Painting painting,
                         @RequestParam(value = "labels", required = false) List<String> labels,
                         @RequestParam(value = "fileName") String fileName,
                         HttpSession session, Model model
    ) throws IOException {
        String labelsString = String.join(",", labels);
        painting.setLabel(labelsString);

        String tempUrl = "D:\\uploadFromOther\\temp\\" + fileName + painting.getFormat();
        // 读取图片文件
        File file = new File(tempUrl);
        byte[] fileContent = FileUtils.readFileToByteArray(file);
        String base64Image = Base64.getEncoder().encodeToString(fileContent);


        String sourceUrl = "E:\\person\\java\\mvc\\demo\\src\\main\\resources\\static\\upload\\" + "source_" + fileName.substring(5) + painting.getFormat();
        painting.setUrl("upload\\" + "source_" + fileName.substring(5) + painting.getFormat());

        User user = (User) session.getAttribute("user");
        painting.setUid(user.getUid());

        painting.setUploadTime(new Date());

        System.out.println(painting);

        if (moveFile(tempUrl, sourceUrl)) {
            // 移动成功，执行其他操作
            model.addAttribute("message", "submit success");
        } else {
            // 处理移动失败的情况
            model.addAttribute("message", "submit fail");
        }

        paintingService.uploadPaintingOfUid(painting);

        painting.setNickname(user.getNickname());
        painting.setAvatar(user.getAvatar());
        painting.setBaseImg64(base64Image);
        List<Painting> newPainting = (List<Painting>) session.getAttribute("newPainting");

        if (newPainting == null) {
            newPainting = new ArrayList<>();
        }

        newPainting.add(painting);
        session.setAttribute("newPainting", newPainting);

        session.setAttribute("imgP", base64Image);
        loggingService.addLogging(user.getNickname(), "upload", String.valueOf(painting.getPid()), new Date());
        return "redirect:painting" + painting.getPid();
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response, String url, String pid, HttpSession session){
        // 文件所在的路径
        String filePath = "E:\\person\\java\\mvc\\demo\\src\\main\\resources\\static\\upload\\" + url;
        try (OutputStream stream = response.getOutputStream();
             InputStream inputStream = new FileInputStream(filePath)) {

            User user = (User) session.getAttribute("user");
            userService.addDHT(user.getUid(), Integer.parseInt(pid), "download");
            userService.addDownloadCount(user.getUid());
            paintingService.addPaintingDownloadCount(Integer.parseInt(pid));

            // 读取文件并将其写入 HTTP 响应流中
            IOUtils.copy(inputStream, stream);
            loggingService.addLogging(user.getNickname(), "download", pid, new Date());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @PostMapping("/heart")
    public void heart(String pid, HttpSession session){
        User user = (User) session.getAttribute("user");
        userService.addDHT(user.getUid(), Integer.parseInt(pid), "heart");
        userService.addHeartCount(user.getUid());
        paintingService.addPaintingHeartCount(Integer.parseInt(pid));
    }

    @ResponseBody
    @PostMapping("/thumbUp")
    public void thumbUp(String pid, HttpSession session){
        User user = (User) session.getAttribute("user");
        userService.addDHT(user.getUid(), Integer.parseInt(pid), "thumbUp");
        userService.addThumbUpCount(user.getUid());
        paintingService.addPaintingThumbUpCount(Integer.parseInt(pid));
    }

    public static boolean moveFile(String sourcePath, String destinationPath) {
        try {
            Path source = Paths.get(sourcePath);
            Path destination = Paths.get(destinationPath);

            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            System.out.println("move fail: " + e.getMessage());
            return false;
        }
    }

    public static List<String> getLabels(String keys) {
        String values = "VWP,KAF,RIM,HARUSARUHI,ISEKAIJOUCHO,KOKO,CIEL,COMIC";

        List<String> keysList = Arrays.asList(keys.split(","));
        List<String> valuesList = Arrays.asList(values.split(","));

        List<String> result = new ArrayList<>();
        for (String key : keysList) {
            int index = Integer.parseInt(key);
            result.add(valuesList.get(index));
        }

        return result;
    }

    public Painting checkDHT(Painting painting, List<DHT> dhtList, int uid){
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
        return painting;
    }
}
