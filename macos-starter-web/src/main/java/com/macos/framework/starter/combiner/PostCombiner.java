package com.macos.framework.starter.combiner;

import com.macos.core.combiner.bese.BaseCombiner;
import com.macos.framework.mvc.handler.mvc.PostHandler;

/**
 * @Desc PostCombiner
 * @Author Zheng.LiMing
 * @Date 2020/2/21
 */
public class PostCombiner extends BaseCombiner {

    private PostCombiner(){
        inint(new PostHandler());
    }
    private static final PostCombiner postCombiner = new PostCombiner();

    public static PostCombiner getCombiner(){
        return postCombiner;
    }
}
