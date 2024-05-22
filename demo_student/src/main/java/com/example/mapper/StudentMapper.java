package com.example.mapper;

import com.example.entity.Student;
import org.apache.ibatis.annotations.Select;


public interface StudentMapper {
    @Select("select * from student where sid = #{sid}")
    Student getStudentBySid(int sid);
}
