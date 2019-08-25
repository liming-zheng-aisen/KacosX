package com.duanya.spring.framework.mvc.dispatcher.staticresouce;

import com.duanya.spring.commont.util.StringUtils;
import com.duanya.spring.framework.mvc.result.ResultData;
import com.duanya.spring.framework.mvc.util.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.Properties;

/**
 * @author zheng.liming
 * @date 2019/8/25
 * @description
 */
public class WebstaticResources {
    private  static Class main;
    private  String staticDir="static";
    private  String regex=".*\\.";
    private  String suffix=".*";

    public WebstaticResources(Properties env,Class main){
        WebstaticResources.main=main;

        String baseDir=env.getProperty("dy.static.dir");
        if (StringUtils.isNotEmptyPlus(baseDir)){
            staticDir=baseDir;
        }
        String suffixArray=env.getProperty("dy.static.suffix");
        if (StringUtils.isNotEmptyPlus(suffixArray)){
            suffix=suffixArray;
        }
    }

    public  boolean isStaticResourcesRequest(String url){
        return url.matches(regex+suffix);
    }

    public  void doResource(HttpServletRequest request, HttpServletResponse response){
        String requestUrl=request.getRequestURI();
        if (isStaticResourcesRequest(requestUrl)){
            URL url =main.getClassLoader().getResource(staticDir+requestUrl);
            if (null==url){
                return;
            }
            String fileType=requestUrl.substring(requestUrl.lastIndexOf(".")+1);
            File file=new File(url.getFile());
            if (file.exists()){
                FileInputStream fis;
                OutputStream out = null;
                try {
                    fis = new FileInputStream(file);
                    long size = file.length();
                    byte[] temp = new byte[(int) size];
                    fis.read(temp, 0, (int) size);
                    fis.close();
                    byte[] data = temp;
                    out = response.getOutputStream();
                    setContentType(response,fileType);
                    out.write(data);
                } catch (Exception e) {
                    PrintWriter printWriter = null;
                    try {
                        printWriter=response.getWriter();
                        printWriter.println(JsonUtil.objectToJson(new ResultData<>(404,null,"Not Found")));
                    } catch (IOException ex) {
                        //....
                    }
                    printWriter.close();
                }finally {
                    try {
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void setContentType(HttpServletResponse response,String fileType){

        String contentType=null;
        switch (fileType){
            case "png":
                contentType="mage/png";
                break;
            case "gif":
                contentType="image/gif";
                break;
            case "jpg":
            case "jpeg":
                contentType="application/jpeg";
                break;
            case "html":
            case "hml":
                contentType="text/html";
                break;
            case "mp4":
                contentType="video/mpeg4";
                break;
            case "mp3":
                contentType="audio/mp3";
                break;
            case "mpeg":
            case "mpg":
            case "mpv":
                contentType="video/mpg";
                break;
            case "mpd":
                contentType="video/vnd.ms-project";
                break;
            case "mpe":
            case "mpv2":
                contentType="video/x-mpeg";
                break;
            case "css":

            case "scss":
            case "less":
                contentType="text/css";
            break;
            case "js":
                contentType="application/x-javascript";
                break;
            case "doc":
                contentType="application/msword";
                break;
                default:
                    contentType="application/octet-stream";
        }
        response.setContentType(contentType);
    }

}
