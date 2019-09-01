package com.duanya.spring.web.controller;

import com.duanya.spring.framework.core.annotation.*;
import com.duanya.spring.framework.mvc.enums.DyMethod;
import com.duanya.spring.web.domain.Students;
import com.duanya.spring.web.service.HelloService;

/**
 * @author zheng.liming
 * @date 2019/8/24
 * @description
 */
@DyRequestMapping
@DyRestController
public class HelloWorldController {

    @DyAutowired
    private HelloService helloService;

    @DyAutowired("jiahua")
    private Students jiahua;

    @DyGet("/getStu")
    public Students getStu(){
        return jiahua;
    }

    @DyPost("/helloP")
    public Students helloP(@DyRequestBody Students students){
        return students;
    }
    @DyPut
    public String put(){
        return "put";
    }
    @DyDelete
    public String delete(){
        return "delete";
    }
    @DyRequestMapping(value = "/det",method = DyMethod.HEAD)
    public String head(){
        return "head";
    }
}
