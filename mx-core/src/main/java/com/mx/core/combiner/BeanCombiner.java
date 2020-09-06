package com.mx.core.combiner;

import com.mx.core.combiner.bese.BaseCombiner;
import com.mx.framework.core.handle.BeanHandler;

/**
 * @Desc BeanCombiner
 * @Author Zheng.LiMing
 * @Date 2020/2/20
 */
public class BeanCombiner extends BaseCombiner {
    private BeanCombiner(){
        inint(new BeanHandler());
    }

    private static final BeanCombiner beanCombiner = new BeanCombiner();

    public static BeanCombiner getCombiner(){
        return beanCombiner;
    }

}
