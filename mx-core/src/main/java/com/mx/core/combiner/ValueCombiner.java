package com.mx.core.combiner;

import com.mx.core.combiner.bese.BaseCombiner;
import com.mx.framework.core.handle.ValueHandler;

/**
 * @Desc ValueCombiner
 * @Author Zheng.LiMing
 * @Date 2020/2/20
 */
public class ValueCombiner extends BaseCombiner {
    private ValueCombiner(){
        inint(new ValueHandler());
    }

    private static final ValueCombiner valueCombiner = new ValueCombiner();

    public static ValueCombiner getCombiner(){
        return valueCombiner;
    }
}
