package com.macos.core.combiner;

import com.macos.core.combiner.bese.BaseCombiner;
import com.macos.framework.core.handle.ConfigurationHandler;

/**
 * @Desc ConfigurationCombiner
 * @Author Zheng.LiMing
 * @Date 2020/2/20
 */
public class ConfigurationCombiner extends BaseCombiner {
    private ConfigurationCombiner(){
        inint(new ConfigurationHandler());

        addBefore(PropertySourceCombiner.getCombiner().getHandler());
        addBefore(ConfigurationPropertiesCombiner.getCombiner().getHandler());
        addBefore(MacosXScannerCombiner.getCombiner().getHandler());

        addAfter(ValueCombiner.getCombiner().getHandler());
        addAfter(AutowiredCombiner.getCombiner().getHandler());
        addAfter(BeanCombiner.getCombiner().getHandler());
    }

    private static final ConfigurationCombiner configurationCombiner = new ConfigurationCombiner();

    public static ConfigurationCombiner getCombiner(){
        return configurationCombiner;
    }

}
