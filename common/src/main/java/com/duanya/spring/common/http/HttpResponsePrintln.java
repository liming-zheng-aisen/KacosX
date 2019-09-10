package com.duanya.spring.common.http;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Desc HttpResponsePrintln
 * @Author Zheng.LiMing
 * @Date 2019/9/5
 */
public class HttpResponsePrintln {

    public static void writer(HttpServletResponse response,Object data){
        PrintWriter out=null;
        try {
            out = response.getWriter();
            out.print(data);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null!=out) {
                out.close();
            }
        }
    }
}
