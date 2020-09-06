package com.mx.framework.starter.combiner;

import com.mx.core.combiner.AutowiredCombiner;
import com.mx.core.combiner.ValueCombiner;
import com.mx.core.combiner.bese.BaseCombiner;
import com.mx.framework.mvc.handler.mvc.RequestMappingHandler;

/**
 * @Desc WebServletCombiner
 * @Author Zheng.LiMing
 * @Date 2020/2/21
 */
public class WebServletCombiner extends BaseCombiner {

    private WebServletCombiner(){
        inint(new RequestMappingHandler());
        addAfter(ValueCombiner.getCombiner().getHandler());
        addAfter(AutowiredCombiner.getCombiner().getHandler());
    }

    private static final WebServletCombiner webServletCombiner = new WebServletCombiner();

    public static WebServletCombiner getCombiner(){
        return webServletCombiner;
    }
}
