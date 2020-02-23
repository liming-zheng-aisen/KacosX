package com.macos.framework.starter.combiner;

import com.macos.core.combiner.AutowiredCombiner;
import com.macos.core.combiner.ValueCombiner;
import com.macos.core.combiner.bese.BaseCombiner;
import com.macos.framework.mvc.handler.mvc.*;

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
