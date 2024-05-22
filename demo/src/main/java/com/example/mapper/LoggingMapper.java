package com.example.mapper;

import com.example.entity.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface LoggingMapper {
    @Insert("insert into logging(operator, operation, target, dateTime) values(#{operator},#{operation},#{target},#{dateTime})")
    boolean addLogging(@Param("operator") String operator, @Param("operation") String operation, @Param("target") String target, @Param("dateTime") Date dateTime);

    @Select("select * from logging")
    List<Log> getAllLogs();
}
