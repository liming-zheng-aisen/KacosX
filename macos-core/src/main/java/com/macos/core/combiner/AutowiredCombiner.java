package com.macos.core.combiner;

import com.macos.core.combiner.bese.BaseCombiner;
import com.macos.framework.core.handle.AutowiredHandler;

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
