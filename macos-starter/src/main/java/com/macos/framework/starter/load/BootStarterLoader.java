package com.macos.framework.starter.load;



import com.macos.common.scanner.api.ScannerApi;
import com.macos.common.scanner.impl.ScannerImpl;
import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.MacosApplicationStarter;
import com.macos.framework.core.bean.factory.BeanFactory;
import com.macos.framework.core.load.ApplicationClassLoader;
import com.macos.framework.core.load.BeanLoad;
import com.macos.framework.core.load.ConfigurationLoader;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Desc DyBootStarterLoader
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public class BootStarterLoader extends BeanLoad {

    private static Logger logger= LoggerFactory.getLogger(BootStarterLoader.class);


    private final String STARTER_PATH="com.macos.framework.starter";

    @Override
    public void load(Class c) throws Exception {
        logger.debug("开始加载dyboot-starter，默认包为{}",STARTER_PATH);
        ScannerApi scanner=new ScannerImpl();

        Set<Class> starters= scanner.doScanner(STARTER_PATH, MacosApplicationStarter.class);

        Set<com.duanya.spring.framework.starter.load.StarterBean> starterBeanSet=convertToStarterBean(starters);

        doStarter(starterBeanSet,c);

        logger.debug("dyboot-starter加载结束");

        if (null!=nextLoader){
            nextLoader.load(c);
        }
    }
    private  Set<com.duanya.spring.framework.starter.load.StarterBean> convertToStarterBean(Set<Class> classes){
        Set<com.duanya.spring.framework.starter.load.StarterBean> starterBeanSet=new HashSet<>();
        for (Class c:classes){
            if (c.isAnnotationPresent(MacosApplicationStarter.class)) {
                MacosApplicationStarter starter = (MacosApplicationStarter) c.getAnnotation(MacosApplicationStarter.class);
                Integer order = starter.order();
                String[] path = starter.scannerPath();
                com.duanya.spring.framework.starter.load.StarterBean starterBean=new com.duanya.spring.framework.starter.load.StarterBean(order,c,path);
                starterBeanSet.add(starterBean);
            }
        }
      return starterBeanSet.stream().sorted(Comparator.comparing(com.duanya.spring.framework.starter.load.StarterBean::getOrder)).collect(Collectors.toSet());
    }

    private void doStarter(Set<com.duanya.spring.framework.starter.load.StarterBean> starters, Class main) throws Exception{
        for (com.duanya.spring.framework.starter.load.StarterBean cl :starters){
            invokeStarter(cl.getTarget(),main);
            registerToClassLoader(cl.getScannerPath());
        }
    }

    private void invokeStarter(Class cl,Class main) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class[] ins=cl.getInterfaces();
        for (Class it:ins){
            if (it.getName().equals("com.macos.framework.starter.DyDefaultStarter")){
                Object beans= BeanFactory.createNewBean(cl);
                Class[] params={Properties.class,Class.class};
                Method method = cl.getMethod("doStart",params);
                method.invoke(beans, ConfigurationLoader.getEvn(),main);
                logger.debug("dyboot-starter执行{}的doStart方法",cl.getSimpleName());
                break;
            }
        }
    }

    public void registerToClassLoader(String[] paths) throws Exception {
        ApplicationClassLoader classLoader=new ApplicationClassLoader();
        for (String str:paths){
            if (StringUtils.isNotEmptyPlus(str)) {
                logger.debug("dyboot-starter调用DyClassLoader加载{}包下的类",str);
                classLoader.load(str);
            }
        }
    }

}
