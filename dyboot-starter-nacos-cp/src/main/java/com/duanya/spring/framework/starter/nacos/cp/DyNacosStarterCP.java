package com.duanya.spring.framework.starter.nacos.cp;

import com.duanya.spring.framework.annotation.DyBootApplicationStarter;
import com.duanya.spring.framework.starter.DyDefaultStarter;

import java.util.Properties;

/**
 * @Desc DyNacosStarterCP
 * @Author Zheng.LiMing
 * @Date 2019/9/12
 */
@DyBootApplicationStarter(scannerPath = {},order = Integer.MAX_VALUE)
public class DyNacosStarterCP implements DyDefaultStarter {
    @Override
    public void doStart(Properties evn, Class cl) throws Exception {

    }
//    public static  void  main (String [] arges) throws Exception{
//
//        Properties properties = new Properties();
//        properties.setProperty("serverAddr","192.168.16.115:8848");
//        properties.setProperty("namespace", "062e1727-2470-4121-a76f-da2fe946db5e");
//        properties.setProperty("group","hahassss111");
//        NamingService naming2 = NamingFactory.createNamingService(properties);
//
//        naming2.registerInstance("nacos.test.3", "11.11.11.11", 8888, "DEFAULT");
//
//        naming2.registerInstance("nacos.test.3", "2.2.2.2", 9999, "DEFAULT");
//
//        System.out.println(naming2.getAllInstances("nacos.test.3"));
//
//        naming2.deregisterInstance("nacos.test.3", "2.2.2.1", 9999, "DEFAULT");
//
//        System.out.println(naming2.getAllInstances("nacos.test.3"));
//
//        naming2.subscribe("nacos.test.3", new EventListener() {
//            @Override
//            public void onEvent(Event event) {
//                System.out.println(((NamingEvent)event).getServiceName());
//                System.out.println(((NamingEvent)event).getInstances());
//            }
//        });
//
//        // TODO Auto-generated method stub
//        InetAddress ia=null;
//               try {
//                        ia=InetAddress.getLocalHost();
//
//                         String localname=ia.getHostName();
//                         String localip=ia.getHostAddress();
//                         System.out.println("本机名称是："+ localname);
//                         System.out.println("本机的ip是 ："+localip);
//                     } catch (Exception e) {
//                   // TODO Auto-generated catch block
//                         e.printStackTrace();
//                }
//    System.in.read();
//    }

}
