package com.macos.core.combiner;

import com.macos.core.combiner.bese.BaseCombiner;
import com.macos.framework.core.handle.ComponentHandler;

/**
 * @Desc ComponentCombiner
 * @Author Zheng.LiMing
 * @Date 2020/2/20
 */
public class ComponentCombiner extends BaseCombiner {

    private ComponentCombiner(){

        inint(new ComponentHandler());

        addBefore(ConfigurationPropertiesCombiner.getCombiner().getHandler());

        addAfter(ValueCombiner.getCombiner().getHandler());

        addAfter(AutowiredCombiner.getCombiner().getHandler());

    }

    private static final ComponentCombiner componentCombiner = new ComponentCombiner();

    public static ComponentCombiner getCombiner(){
        return componentCombiner;
    }
}
