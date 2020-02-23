package com.macos.framework.starter.combiner;

import com.macos.core.combiner.ConfigurationCombiner;
import com.macos.core.combiner.MacosXScannerCombiner;
import com.macos.core.combiner.bese.BaseCombiner;
import com.macos.framework.starter.handle.MacosXApplicationHandler;

/**
 * @Desc
 * @Author Zheng.LiMing
 * @Date 2020/2/20
 */
public class MacosXApplicationCombiner extends BaseCombiner {

    private MacosXApplicationCombiner(){
        inint(new MacosXApplicationHandler());
        addBefore(AutoConfigurationCombiner.getCombiner().getHandler());

        addAfter(ConfigurationCombiner.getCombiner().getHandler());
        addAfter(MacosXScannerCombiner.getCombiner().getHandler());
    }

    private static final MacosXApplicationCombiner macosXApplicationCombiner = new MacosXApplicationCombiner();

    public static MacosXApplicationCombiner getCombiner(){
        return macosXApplicationCombiner;
    }
}
