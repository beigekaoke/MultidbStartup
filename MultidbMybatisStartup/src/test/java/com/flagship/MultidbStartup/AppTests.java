package com.flagship.MultidbStartup;

import com.flagship.MultidbStartup.entity.Student;
import com.flagship.MultidbStartup.entity.Teacher;
import com.flagship.MultidbStartup.mapper.master.StudentMapper;
import com.flagship.MultidbStartup.mapper.slave.TeacherMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class})// 指定启动类
public class AppTests {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Test
    public void multidbTest(){
        Student st1 = new Student("潘金莲",18,66);
        studentMapper.insert(st1);
        Teacher te1 = new Teacher("大神",33,777);
        teacherMapper.insert(te1);
        System.out.println(studentMapper.getList());
        System.out.println(teacherMapper.getList());
    }
}
