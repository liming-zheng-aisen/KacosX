package com.mx.framework.starter.combiner;

import com.mx.core.combiner.ConfigurationCombiner;
import com.mx.core.combiner.MacosXScannerCombiner;
import com.mx.core.combiner.PointCupCombiner;
import com.mx.core.combiner.bese.BaseCombiner;
import com.mx.framework.starter.handle.MacosXApplicationHandler;

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
        addAfter(PointCupCombiner.getPointCupCombiner().getHandler());
    }

    private static final MacosXApplicationCombiner macosXApplicationCombiner = new MacosXApplicationCombiner();

    public static MacosXApplicationCombiner getCombiner(){
        return macosXApplicationCombiner;
    }
}
