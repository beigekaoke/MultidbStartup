package com.flagship.MultidbStartup.controller;

import com.flagship.MultidbStartup.entity.Student;
import com.flagship.MultidbStartup.entity.Teacher;
import com.flagship.MultidbStartup.mapper.master.StudentMapper;
import com.flagship.MultidbStartup.mapper.slave.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class CurdController {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @RequestMapping("/students")
    public List<Student> getStudentList() {
        List<Student> studentList = studentMapper.getList();
        return studentList;
    }

    @RequestMapping("/teachers")
    public List<Teacher> getTeacherList() {
        List<Teacher> teacherList = teacherMapper.getList();
        return teacherList;
    }
}
