package com.macos.core.combiner;

import com.macos.core.combiner.bese.BaseCombiner;
import com.macos.framework.core.handle.ValueHandler;

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
