package com.example.service.impl;

import com.example.entity.DHT;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper mapper;
    @Override
    public User getUserByUsernameAndPwd(String username, String password) {
        return mapper.getUserByUsernameAndPwd(username, password);
    }

    @Override
    public User getUserByUid(int uid) {
        return mapper.getUserByUid(uid);
    }

    @Override
    public int addDownloadCount(int uid) {
        return mapper.addDownloadCount(uid);
    }

    @Override
    public int addDHT(int uid, int pid, String type) {
        return mapper.addDHT(uid, pid, type);
    }

    @Override
    public int addHeartCount(int uid) {
        return mapper.addHeartCount(uid);
    }

    @Override
    public int addThumbUpCount(int uid) {
        return mapper.addThumbUpCount(uid);
    }

    @Override
    public List<DHT> getDHTByUid(int uid) {
        return mapper.getDHTByUid(uid);
    }

    @Override
    public int uploadUserInfo(String nickname, int age, String phone, String social, String bio, int uid) {
        return mapper.uploadUserInfo(nickname, age, phone, social, bio, uid);
    }

    @Override
    public List<User> getAllUsersExceptMe(int uid) {
        return mapper.getAllUsersExceptMe(uid);
    }

    @Override
    public boolean deleteUserByUid(int uid) {
        return mapper.deleteUserByUid(uid);
    }

    @Override
    public int uploadUserType(int uid) {
        return mapper.uploadUserType(uid);
    }
}
