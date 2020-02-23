package com.macos.core.combiner;

import com.macos.core.combiner.bese.BaseCombiner;
import com.macos.framework.core.handle.MacosXScannerHandler;

/**
 * @Desc 扫描类加载组装器
 * @Author Zheng.LiMing
 * @Date 2020/2/20
 */
public class MacosXScannerCombiner extends BaseCombiner {
    private MacosXScannerCombiner(){
        inint(new MacosXScannerHandler());
    }

    private static final MacosXScannerCombiner macosXScannerCombiner = new MacosXScannerCombiner();

    public static MacosXScannerCombiner getCombiner(){
        return macosXScannerCombiner;
    }
}
