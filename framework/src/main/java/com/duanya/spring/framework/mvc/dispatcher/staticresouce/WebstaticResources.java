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
    private  String staticDir="/static";
    private  String regex=".*\\.";
    private  String suffix=".*";

    public WebstaticResources(Properties env){
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
            String fileType=requestUrl.substring(requestUrl.lastIndexOf(".")+1);
            String filePath=staticDir+requestUrl;
            OutputStream out = null;
            InputStream inputStream=null;
            try {
                inputStream=this.getClass().getResourceAsStream(filePath);
                long size = inputStream.available();
                byte[] temp = new byte[(int) size];
                inputStream.read(temp, 0, (int) size);
                inputStream.close();
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
                    if (null!=out) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (null!=inputStream) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
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
