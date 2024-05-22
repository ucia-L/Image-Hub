package com.example.service.impl;

import com.example.entity.Student;
import com.example.mapper.StudentMapper;
import com.example.service.StudentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    StudentMapper mapper;
    @Override
    public Student getStudentBySid(int sid) {
        return mapper.getStudentBySid(sid);
    }
}
