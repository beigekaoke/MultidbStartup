package com.flagship.multidbJpaStartup;

import com.flagship.multidbJpaStartup.entity.master.StudentDao;
import com.flagship.multidbJpaStartup.entity.slave.TeacherDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class})// 指定启动类
public class AppTests {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private TeacherDao teacherDao;

    @Test
    public void mulitdbJpaTest() {
        System.out.println(studentDao.findAll());
        System.out.println(teacherDao.findAll());
    }

}
