package com.duanya.spring.web.controller;

import com.duanya.spring.framework.annotation.*;

/**
 * @Desc WebController
 * @Author Zheng.LiMing
 * @Date 2019/9/3
 */
@DyRestController
@DyRequestMapping("/dyboot")
public class WebController {

    @DyGet("/ss/{str}")
    public String get(@DyPathVariable String str){
        return str;
    }

    @DyPost
    public Student post(@DyRequestBody Student student){
        System.out.println(student);
        return student;
    }

    @DyDelete("/{id}")
    public String delete(@DyPathVariable String id){
        return "删除id为"+id;
    }

    @DyPut
    public Student put(@DyRequestBody Student student){
        System.out.println(student);
        return student;
    }

}
