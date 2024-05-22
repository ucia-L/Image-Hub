package com.example.mapper;

import com.example.entity.Painting;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PaintingMapper {
    @Select("select Painting.*,[User].nickname,[User].avatar from Painting, [User] where Painting.uid=[User].uid")
    List<Painting> getAllPaintings();

    @Select("select distinct Painting.*,[User].nickname,[User].avatar from Painting, [User], DHT where Painting.uid=[User].uid and DHT.uid=#{uid} and DHT.pid=Painting.pid and DHT.type=#{type}")
    List<Painting> getAllPaintingByType(@Param("uid") int uid, @Param("type") String type);

    @Select("select * from Painting where pid=#{pid}")
    Painting getPaintingByPid(int pid);

    @Select("select * from Painting where uid=#{uid}")
    List<Painting> getPaintingByUid(int uid);

    @Select("select * from Painting where label=#{label}")
    List<Painting> getPaintingByLabel(String label);

    @Select("select Painting.*,[User].nickname,[User].avatar from Painting, [User] WHERE Painting.uid=[User].uid and title LIKE CONCAT('%', #{title}, '%')")
    List<Painting> getPaintingByTitle(String title);

    @Select("select Painting.*,[User].nickname,[User].avatar from Painting, [User] WHERE Painting.uid=[User].uid and label LIKE CONCAT('%', #{type}, '%')")
    List<Painting> getPaintingByType(String type);

    @Insert(
            "insert into " +
                    "Painting(uid, title, label, size, format, uploadTime, likeCount, favCount, downloadCount,url,description) " +
                    "values(#{uid},#{title},#{label},#{size},#{format},#{uploadTime},#{likeCount},#{favCount},#{downloadCount},#{url},#{description})"
    )
    @Options(useGeneratedKeys = true, keyProperty = "pid")
    int uploadPaintingOfUid(Painting painting);
    @Delete("delete from Painting where pid=#{pid}")
    boolean deletePaintingOfPid(@Param("pid") int pid);
    @Delete("delete from DHT where pid=#{pid}")
    boolean deleteDHTOfPid(@Param("pid") int pid);

    @Update("update Painting set downloadCount=downloadCount+1 where pid=#{pid}")
    int addPaintingDownloadCount(@Param("pid") int pid);

    @Update("update Painting set likeCount=likeCount+1 where pid=#{pid}")
    int addPaintingThumbUpCount(@Param("pid") int pid);

    @Update("update Painting set favCount=favCount+1 where pid=#{pid}")
    int addPaintingHeartCount(@Param("pid") int pid);

}
