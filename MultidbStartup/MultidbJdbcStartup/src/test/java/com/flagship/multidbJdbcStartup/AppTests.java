package com.flagship.multidbJdbcStartup;

import com.flagship.multidbJdbcStartup.entity.Student;
import com.flagship.multidbJdbcStartup.entity.Teacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class})// 指定启动类
public class AppTests {

    //master
    @Autowired
    @Qualifier("masterJdbcTemplate")
    protected JdbcTemplate masterTempleate;

    @Autowired
    @Qualifier("masterDataSource")
    protected DataSource masterDataSource;

    //slave
    @Autowired
    @Qualifier("slaveJdbcTemplate")
    protected JdbcTemplate slaveTempleate;

    @Test
    public void mulitdbTest() {
        //insert
        String sql1 = "insert into student(age,grade,name) values (13,7,'武大郎')";
        masterTempleate.update(sql1);
        String sql2 = "insert into teacher(age,course,name) values ('','数学','武二郎')";
        masterTempleate.update(sql2);


        //query
        String studentSql = "select * from student";
        RowMapper<Student> studentRowMapper = new BeanPropertyRowMapper<>(Student.class);
        List<Student> studentList = masterTempleate.query(studentSql, studentRowMapper);

        String teacherSql = "select * from teacher";
        RowMapper<Teacher> teacherRowMapper = new BeanPropertyRowMapper<>(Teacher.class);
        List<Teacher> teacherList = slaveTempleate.query(teacherSql, teacherRowMapper);

        System.out.println(studentList);
        System.out.println(teacherList);
    }


    @Test
    public void datasourceTest() {
        //查看默认数据源 class com.zaxxer.hikari.HikariDataSource
        System.out.println(masterDataSource.getClass());
        //获取数据库连接
        Connection connection = null;
        try {
            connection = masterDataSource.getConnection();
            System.out.println(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try{
                    // 关闭连接
                    connection.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
