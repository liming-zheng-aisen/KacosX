package com.mx.core.combiner;

import com.mx.core.combiner.bese.BaseCombiner;
import com.mx.framework.core.handle.*;

/**
 * @Desc 切面
 * @Author Zheng.LiMing
 * @Date 2020/4/2
 */
public class AspectCombiner extends BaseCombiner {
    private AspectCombiner() {
        inint(new AspectHandler());
        addAfter(new BeforeHandler());
        addAfter(new AfterFinalHandler());
        addAfter(new AfterReturnHandler());
        addAfter(new AfterThrowsHandler());
        addAfter(new AroundHandler());
    }
    private static final AspectCombiner aspectCombiner = new AspectCombiner();

    public static AspectCombiner getCombiner(){
        return aspectCombiner;
    }
}
