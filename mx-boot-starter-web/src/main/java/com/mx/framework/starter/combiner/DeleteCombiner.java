package com.mx.framework.starter.combiner;

import com.mx.core.combiner.bese.BaseCombiner;
import com.mx.framework.mvc.handler.mvc.DeleteHandler;

/**
 * @Desc DeleteCombiner
 * @Author Zheng.LiMing
 * @Date 2020/2/21
 */
public class DeleteCombiner extends BaseCombiner {

    private DeleteCombiner(){
        inint(new DeleteHandler());
    }
    private static final DeleteCombiner deleteCombiner = new DeleteCombiner();

    public static DeleteCombiner getCombiner(){
        return deleteCombiner;
    }
}