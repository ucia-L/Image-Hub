package com.example.mapper;

import com.example.entity.DHT;
import com.example.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
    @Select("select * from [User] where username=#{username} and password=#{password}")
    User getUserByUsernameAndPwd(@Param("username") String username, @Param("password") String password);
    @Select("select * from [User] where uid=#{uid}")
    User getUserByUid(@Param("uid") int uid);
    @Update("update [User] set downloadCount=downloadCount+1 where uid=#{uid}")
    int addDownloadCount(@Param("uid") int uid);
    @Update("update [User] set heartCount=heartCount+1 where uid=#{uid}")
    int addHeartCount(@Param("uid") int uid);
    @Update("update [User] set thumbUpCount=thumbUpCount+1 where uid=#{uid}")
    int addThumbUpCount(@Param("uid") int uid);
    @Insert("insert into DHT(uid,pid,type) values(#{uid},#{pid},#{type})")
    int addDHT(@Param("uid") int uid, @Param("pid") int pid, @Param("type") String type);
    @Select("select * from DHT where uid=#{uid}")
    List<DHT> getDHTByUid(@Param("uid") int uid);
    @Update("update [User] set nickname=#{nickname},age=#{age},phone=#{phone},social=#{social},bio=#{bio} WHERE uid=#{uid}")
    int uploadUserInfo(@Param("nickname") String nickname, @Param("age")int age, @Param("phone")String phone, @Param("social")String social, @Param("bio")String bio, @Param("uid")int uid);
    @Select("select * from [User] where uid!=#{uid}")
    List<User> getAllUsersExceptMe(@Param("uid") int uid);
    @Delete("delete from [User] where uid=#{uid}")
    boolean deleteUserByUid(@Param("uid") int uid);
    @Update("update [User] set type=1 where uid=#{uid}")
    int uploadUserType(@Param("uid") int uid);
}
