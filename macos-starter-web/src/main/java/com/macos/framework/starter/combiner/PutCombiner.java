package com.macos.framework.starter.combiner;

import com.macos.core.combiner.bese.BaseCombiner;
import com.macos.framework.mvc.handler.mvc.PutHandler;

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
