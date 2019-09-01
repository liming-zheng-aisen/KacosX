package com.duanya.spring.web.service.impl;

import com.duanya.spring.framework.core.annotation.DyAutowired;
import com.duanya.spring.framework.core.annotation.DyService;
import com.duanya.spring.web.domain.Students;
import com.duanya.spring.web.mapper.TestMapper;
import com.duanya.spring.web.service.HelloService;

/**
 * @author zheng.liming
 * @date 2019/8/24
 * @description
 */
@DyService
public class HelloServiceImpl implements HelloService {

    @DyAutowired
    private Students stu;

    @DyAutowired
    private TestMapper mapper;

    @Override
    public String getName() {
        return "haha";
    }

    @Override
    public Students getStu() {
        return stu;
    }

    @Override
    public Integer getTen() {
        return mapper.getInter();
    }


}
