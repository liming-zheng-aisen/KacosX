package com.duanya.spring.web.service;

import com.duanya.spring.web.entity.Student;

import java.util.List;

/**
 * @Desc IStudentService
 * @Author Zheng.LiMing
 * @Date 2019/9/7
 */
public interface IStudentService {

    List<Student> selectAllStudent();

    Student selectStudentById(String id);

    Integer updateStudent(Student stu);

    Integer deleteStudentById(String stuId);

    Integer insertStudent(Student stu);

}
