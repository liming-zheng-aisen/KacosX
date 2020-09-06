package com.mx.framework.starter.combiner;

import com.mx.core.combiner.bese.BaseCombiner;
import com.mx.framework.mvc.handler.mvc.PutHandler;

/**
 * @Desc PutCombiner
 * @Author Zheng.LiMing
 * @Date 2020/2/21
 */
public class PutCombiner extends BaseCombiner {

    private PutCombiner(){
        inint(new PutHandler());
    }
    private static final PutCombiner putCombiner = new PutCombiner();

    public static PutCombiner getCombiner(){
        return putCombiner;
    }


}
