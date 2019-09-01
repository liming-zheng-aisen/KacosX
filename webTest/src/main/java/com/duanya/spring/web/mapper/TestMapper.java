package com.duanya.spring.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author zheng.liming
 * @date 2019/8/26
 * @description
 */
@Mapper
public interface TestMapper {

    @Select(" select 10 ")
    Integer getInter();
}
