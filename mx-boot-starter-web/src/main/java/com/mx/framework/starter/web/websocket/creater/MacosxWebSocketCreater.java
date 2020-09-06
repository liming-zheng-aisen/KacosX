package com.mx.framework.starter.web.websocket.creater;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

/**
 * @Desc websocket 创建
 * @Author Zheng.LiMing
 * @Date 2020/4/4
 */
public class MacosxWebSocketCreater implements WebSocketCreator {

    private WebSocketListener webSocketListener = new WebSocketListener() {
        @Override
        public void onWebSocketBinary(byte[] bytes, int i, int i1) {

        }

        @Override
        public void onWebSocketText(String s) {
            System.out.println(s);
        }

        @Override
        public void onWebSocketClose(int i, String s) {
            System.out.println("关闭");
        }

        @Override
        public void onWebSocketConnect(Session session) {
            System.out.println("连接");
        }

        @Override
        public void onWebSocketError(Throwable throwable) {
            System.out.println("异常");
        }
    };

    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
       for (String sub : servletUpgradeRequest.getSubProtocols()){
           servletUpgradeResponse.setAcceptedSubProtocol(sub);
       }

        return webSocketListener;
    }
}
