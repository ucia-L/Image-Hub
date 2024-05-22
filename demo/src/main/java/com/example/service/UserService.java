package com.example.service;


import com.example.entity.DHT;
import com.example.entity.User;

import java.util.List;

public interface UserService {
    User getUserByUsernameAndPwd(String username, String password);
    User getUserByUid(int uid);
    int addDownloadCount(int uid);
    int addDHT(int uid, int pid, String type);
    int addHeartCount(int uid);
    int addThumbUpCount(int uid);
    List<DHT> getDHTByUid(int uid);
    int uploadUserInfo(String nickname, int age, String phone, String social, String bio, int uid);
    List<User> getAllUsersExceptMe(int uid);
    boolean deleteUserByUid(int uid);
    int uploadUserType(int uid);
}
