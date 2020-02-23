package com.macos.core.combiner;

import com.macos.core.combiner.bese.BaseCombiner;
import com.macos.framework.core.handle.ConfigurationPropertiesHandler;

/**
 * @Desc ConfigurationPropertiesCombiner
 * @Author Zheng.LiMing
 * @Date 2020/2/20
 */
public class ConfigurationPropertiesCombiner extends BaseCombiner {
    private ConfigurationPropertiesCombiner(){
        inint(new ConfigurationPropertiesHandler());
    }

    private static final ConfigurationPropertiesCombiner configurationPropertiesCombiner = new ConfigurationPropertiesCombiner();

    public static ConfigurationPropertiesCombiner getCombiner(){
        return configurationPropertiesCombiner;
    }
}
