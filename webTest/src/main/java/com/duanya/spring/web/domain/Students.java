package com.duanya.spring.web.domain;

import com.duanya.spring.framework.core.annotation.DyComponent;
import com.duanya.spring.framework.core.annotation.DyValue;

/**
 * @author zheng.liming
 * @date 2019/8/24
 * @description
 */
@DyComponent
public class Students {
    @DyValue("${dy.id}")
    private String id;
    @DyValue("${dy.name}")
    private String name;
    @DyValue("${dy.age}")
    private Integer age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Students(String id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Students() {
    }
}
