package com.macos.framework.starter.combiner;

import com.macos.core.combiner.bese.BaseCombiner;
import com.macos.framework.starter.handle.MacosXApplicationStarterHandler;

/**
 * @Desc MacosXApplicationStarterCombiner
 * @Author Zheng.LiMing
 * @Date 2020/2/20
 */
public class MacosXApplicationStarterCombiner extends BaseCombiner {
    private MacosXApplicationStarterCombiner(){
        inint(new MacosXApplicationStarterHandler());
    }

    private static final MacosXApplicationStarterCombiner macosXApplicationStarterCombiner = new MacosXApplicationStarterCombiner();

    public static MacosXApplicationStarterCombiner getCombiner(){
        return macosXApplicationStarterCombiner;
    }
}
