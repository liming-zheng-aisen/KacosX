package com.mx.core.combiner;

import com.mx.core.combiner.bese.BaseCombiner;
import com.mx.framework.core.handle.PointCupHandler;

/**
 * @Desc PointCupCombiner
 * @Author Zheng.LiMing
 * @Date 2020/4/2
 */
public class PointCupCombiner extends BaseCombiner {
    private PointCupCombiner() {
        inint(new PointCupHandler());
    }

    private static PointCupCombiner pointCupCombiner = new PointCupCombiner();

    public static PointCupCombiner getPointCupCombiner(){
        return pointCupCombiner;
    }
}
