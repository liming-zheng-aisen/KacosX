package com.duanya.spring.web.entity;

/**
 * @Desc Student
 * @Author Zheng.LiMing
 * @Date 2019/9/3
 */
public class Student {
    private String stuId;
    private String  stuName;
    private Integer age;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
