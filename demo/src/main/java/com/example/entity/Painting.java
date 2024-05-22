package com.example.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class Painting {
    int pid;
    int uid;
    String title;
    String label = "";
    String size;
    String format;
    Date uploadTime;
    int likeCount = 0;
    int favCount = 0;
    int downloadCount = 0;
    String url;
    String description = "";
    String nickname;
    String avatar;
    String baseImg64;
    boolean thumbUp=false;
    boolean heart=false;
}
