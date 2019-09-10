package com.duanya.spring.web.service.impl;

import com.duanya.spring.framework.annotation.DyAutowired;
import com.duanya.spring.framework.annotation.DyService;
import com.duanya.spring.web.entity.Student;
import com.duanya.spring.web.mapper.StudentMapper;
import com.duanya.spring.web.service.IStudentService;

import java.util.List;

/**
 * @Desc StudentServiceImpl
 * @Author Zheng.LiMing
 * @Date 2019/9/7
 */

@DyService
public class StudentServiceImpl implements IStudentService {


    @DyAutowired
    private StudentMapper studentMapper;

    @Override
    public List<Student> selectAllStudent() {
        return studentMapper.selectAllStudent();
    }

    @Override
    public Student selectStudentById(String id) {
        return studentMapper.selectStudentById(id);
    }

    @Override
    public Integer updateStudent(Student stu) {
        return studentMapper.updateStudent(stu);
    }

    @Override
    public Integer deleteStudentById(String stuId) {
        return studentMapper.deleteStudentById(stuId);
    }

    @Override
    public Integer insertStudent(Student stu) {
        return studentMapper.insertStudent(stu);
    }

}
