package com.duanya.spring.web.mapper;


import com.duanya.spring.web.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Desc TestMapper
 * @Author Zheng.LiMing
 * @Date 2019/9/5
 */
@Mapper
public interface StudentMapper {

    @Select(" select stu_id , stu_name, age from tb_student ")
    List<Student> selectAllStudent();

    @Select(" select stu_id , stu_name, age from tb_student where stu_id = #{id} ")
    Student selectStudentById(@Param("id") String id);

    @Update(" update tb_student set stu_name=#{stu.stuName}, age=#{stu.age} where stu_id = #{stu.stuId} ")
    Integer updateStudent(@Param("stu") Student stu);

    @Delete(" delete from  tb_student  where stu_id = #{stuId} ")
    Integer deleteStudentById(@Param("stuId") String stuId);

    @Insert(" insert into tb_student(stu_id , stu_name, age) value (#{stu.stuId},#{stu.stuName},#{stu.age})")
    Integer insertStudent(@Param("stu") Student stu);
}
