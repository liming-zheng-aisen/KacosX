package com.duanya.start.web.jetty.handlle;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ResourceHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Desc 静态资源处理器
 * @Author Zheng.LiMing
 * @Date 2019/9/11
 */

public class DyStaticSourceHandle extends ResourceHandler{

    private String prefixRegex="\\/.*";

    private String suffixRegex="\\..*";

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (target.matches(prefixRegex+suffixRegex)) {
            super.handle(target, baseRequest, request, response);
        }
    }

    public String getPrefixRegex() {
        return prefixRegex;
    }

    public void setPrefixRegex(String prefixRegex) {

        this.prefixRegex =formatRegex(prefixRegex,false);
    }

    public String getSuffixRegex() {
        return suffixRegex;
    }

    public void setSuffixRegex(String suffixRegex) {
        this.suffixRegex = formatRegex(suffixRegex,true);
    }

    private  String formatRegex(String regex,boolean isSuffix){
        regex=regex.replace(".","\\.");
        regex= regex.replace("*",".*");
        regex=regex.replace("/","\\/");
        if (isSuffix){
            regex=".*\\."+regex.replace(",","|");
        }
        return  regex;
    }
}
