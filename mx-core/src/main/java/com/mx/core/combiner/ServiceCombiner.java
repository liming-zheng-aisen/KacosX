package com.mx.core.combiner;

import com.mx.core.combiner.bese.BaseCombiner;
import com.mx.framework.core.handle.ServiceHandler;

/**
 * @Desc ServiceCombiner
 * @Author Zheng.LiMing
 * @Date 2020/2/20
 */
public class ServiceCombiner extends BaseCombiner {
    private ServiceCombiner(){

        inint(new ServiceHandler());

        addBefore(ConfigurationPropertiesCombiner.getCombiner().getHandler());

        addAfter(ValueCombiner.getCombiner().getHandler());

        addAfter(AutowiredCombiner.getCombiner().getHandler());

    }

    private static final ServiceCombiner serviceCombiner = new ServiceCombiner();

    public static ServiceCombiner getCombiner(){
        return serviceCombiner;
    }
}
