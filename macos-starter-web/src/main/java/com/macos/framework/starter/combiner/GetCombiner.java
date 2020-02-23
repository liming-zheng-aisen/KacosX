package com.macos.framework.starter.combiner;

import com.macos.core.combiner.bese.BaseCombiner;
import com.macos.framework.mvc.handler.mvc.GetHandler;

/**
 * @Desc get请求包装器
 * @Author Zheng.LiMing
 * @Date 2020/2/21
 */
public class GetCombiner extends BaseCombiner {

    private GetCombiner(){
        inint(new GetHandler());
    }
    private static final GetCombiner getCombiner = new GetCombiner();

    public static GetCombiner getCombiner(){
        return getCombiner;
    }
}
