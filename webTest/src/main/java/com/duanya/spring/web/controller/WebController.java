package com.duanya.spring.web.controller;

import com.duanya.spring.common.http.result.ResultData;
import com.duanya.spring.framework.annotation.*;
import com.duanya.spring.web.entity.Student;
import com.duanya.spring.web.service.IStudentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Desc WebController
 * @Author Zheng.LiMing
 * @Date 2019/9/3
 */
@DyRestController
@DyRequestMapping("/v1/dyboot/student")
public class WebController {

    @DyAutowired
    private IStudentService studentService;


    /**
     * 查询所以学生信息
     * @return
     */
    @DyGet
    public ResultData<PageInfo> queryAllStudent(@DyRequestParameter("pageNum") Integer pageNum,@DyRequestParameter("pageSize")Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Student> allStudents= studentService.selectAllStudent();
        PageInfo<Student> pageInfo =new PageInfo<Student>(allStudents);
        return  new ResultData<>(200,pageInfo,"SUCCESS");
    }


    /**
     * 根据学员id，查询信息
     * @param stuId
     * @return
     */
    @DyGet("/{stuId}")
    public ResultData<Student> queryStudentById(@DyPathVariable String stuId){
        Student student=studentService.selectStudentById(stuId);
        return  new ResultData<>(200,student,"SUCCESS");
    }

    /**
     * 修改学员信息
     * @param student
     * @return
     */
    @DyPost
    public ResultData<?> updateStudent(@DyRequestBody Student student){
        Integer result=studentService.updateStudent(student);
        if (result>0){
            return  new ResultData<>(200,null,"SUCCESS");
        }else {
            return  new ResultData<>(500,null,"FAIL");
        }

    }

    /**
     * 新增学员信息
     * @param student
     * @return
     */
    @DyPut
    public ResultData<?> addStudent(@DyRequestBody Student student){
        Integer result=studentService.insertStudent(student);
        if (result>0){
            return  new ResultData<>(200,null,"SUCCESS");
        }else {
            return  new ResultData<>(500,null,"FAIL");
        }

    }

    /**
     * 根据学员id，删除学员信息（注意：正式项目是逻辑删除，这只是演示使用）
     * @param stuId
     * @return
     */
    @DyDelete("/{id}")
    public ResultData<?> deleteStudentById(@DyPathVariable  String stuId){
        Integer result=studentService.deleteStudentById(stuId);
        if (result>0){
            return  new ResultData<>(200,null,"SUCCESS");
        }else {
            return  new ResultData<>(500,null,"FAIL");
        }
    }


}
