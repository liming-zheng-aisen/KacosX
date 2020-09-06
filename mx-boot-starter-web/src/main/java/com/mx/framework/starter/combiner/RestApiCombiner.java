package com.mx.framework.starter.combiner;

import com.mx.core.combiner.AutowiredCombiner;
import com.mx.core.combiner.ValueCombiner;
import com.mx.core.combiner.bese.BaseCombiner;
import com.mx.framework.mvc.handler.mvc.RestApiHandler;

/**
 * @Desc RestApiCombiner
 * @Author Zheng.LiMing
 * @Date 2020/2/21
 */
public class RestApiCombiner extends BaseCombiner {

    private RestApiCombiner(){
        inint(new RestApiHandler());
        addAfter(ValueCombiner.getCombiner().getHandler());
        addAfter(AutowiredCombiner.getCombiner().getHandler());
        addAfter(RequestMappingCombiner.getCombiner().getHandler());
        addAfter(GetCombiner.getCombiner().getHandler());
        addAfter(PostCombiner.getCombiner().getHandler());
        addAfter(PutCombiner.getCombiner().getHandler());
        addAfter(DeleteCombiner.getCombiner().getHandler());
    }

    private static final RestApiCombiner restApiCombiner = new RestApiCombiner();

    public static RestApiCombiner getCombiner(){
        return restApiCombiner;
    }
}
