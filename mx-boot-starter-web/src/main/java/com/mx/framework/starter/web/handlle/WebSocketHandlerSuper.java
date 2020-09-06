package com.mx.framework.starter.web.handlle;

import com.mx.framework.core.bean.definition.BeanDefinition;
import com.mx.framework.starter.web.websocket.creater.MacosxWebSocketCreater;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @Desc webSocket处理器
 * @Author Zheng.LiMing
 * @Date 2020/4/3
 */
public class WebSocketHandlerSuper extends WebSocketHandler {


    private static Set<Class> websockets = new HashSet<>();

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.getPolicy().setIdleTimeout(10000);
        webSocketServletFactory.setCreator(new MacosxWebSocketCreater());
    }

    public static void registers(Set<BeanDefinition> set){
        for (BeanDefinition beanDefinition : set){
            websockets.add(beanDefinition.getTarget());
        }
    }
}
