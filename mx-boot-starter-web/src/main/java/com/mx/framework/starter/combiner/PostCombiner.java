package com.mx.framework.starter.combiner;

import com.mx.core.combiner.bese.BaseCombiner;
import com.mx.framework.mvc.handler.mvc.PostHandler;

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
