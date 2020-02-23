package com.macos.core.combiner;

import com.macos.core.combiner.bese.BaseCombiner;
import com.macos.framework.core.handle.PropertySourceHandler;

/**
 * @Desc 配置加载器
 * @Author Zheng.LiMing
 * @Date 2020/2/20
 */
public class PropertySourceCombiner extends BaseCombiner {
    private PropertySourceCombiner(){
        inint(new PropertySourceHandler());
    }

    private static final PropertySourceCombiner propertySourceCombiner = new PropertySourceCombiner();

    public static PropertySourceCombiner getCombiner(){
        return propertySourceCombiner;
    }
}
