package com.mx.core.combiner;

import com.mx.core.combiner.bese.BaseCombiner;
import com.mx.framework.core.handle.AutowiredHandler;

/**
 * @Desc AutowiredCombiner
 * @Author Zheng.LiMing
 * @Date 2020/2/20
 */
public class AutowiredCombiner extends BaseCombiner {

    private AutowiredCombiner(){
        inint(new AutowiredHandler());
    }

    private static final AutowiredCombiner autowiredCombiner = new AutowiredCombiner();

    public static AutowiredCombiner getCombiner(){
        return autowiredCombiner;
    }
}
