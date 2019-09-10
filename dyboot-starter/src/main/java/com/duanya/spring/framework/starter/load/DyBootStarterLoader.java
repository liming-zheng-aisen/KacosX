package com.duanya.spring.framework.starter.load;

import com.duanya.spring.common.scanner.api.IDyScanner;
import com.duanya.spring.common.scanner.impl.DyScannerImpl;
import com.duanya.spring.common.util.StringUtils;
import com.duanya.spring.framework.annotation.DyBootApplicationStarter;
import com.duanya.spring.framework.core.bean.factory.DyBeanFactory;
import com.duanya.spring.framework.core.load.DyBeanLoad;
import com.duanya.spring.framework.core.load.DyClassLoader;
import com.duanya.spring.framework.core.load.DyConfigurationLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Set;

/**
 * @Desc DyBootStarterLoader
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public class DyBootStarterLoader extends  DyBeanLoad {

    static Logger logger= LoggerFactory.getLogger(DyBootStarterLoader.class);


    private final String STARTER_PATH="com.duanya.spring.framework.starter";

    @Override
    public void load(Class c) throws Exception {
        logger.info("开始加载dyboot-starter，默认包为{}",STARTER_PATH);
        IDyScanner scanner=new DyScannerImpl();

        Set<Class> starters= scanner.doScanner(STARTER_PATH,DyBootApplicationStarter.class);

        doStarter(starters,c);

        logger.info("dyboot-starter加载结束");

        if (null!=nextLoader){
            nextLoader.load(c);
        }

    }

    private void doStarter(Set<Class> starters,Class main) throws Exception{
        for (Class cl :starters){
            invokeStarter(cl,main);
            DyBootApplicationStarter starter=(DyBootApplicationStarter)cl.getAnnotation(DyBootApplicationStarter.class);
            String[] paths=starter.scannerPath();
            registerToClassLoader(paths);
        }
    }

    private void invokeStarter(Class cl,Class main) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class[] ins=cl.getInterfaces();
        for (Class it:ins){
            if (it.getName().equals("com.duanya.spring.framework.starter.DyDefaultStarter")){
                Object beans=DyBeanFactory.createNewBean(cl);
                Class[] params={Properties.class,Class.class};
                Method method = cl.getMethod("doStart",params);
                method.invoke(beans,DyConfigurationLoader.getEvn(),main);
                logger.info("dyboot-starter执行{}的doStart方法",cl.getSimpleName());
                break;
            }
        }
    }

    public void registerToClassLoader(String[] paths) throws Exception {
        DyClassLoader classLoader=new DyClassLoader();
        for (String str:paths){
            if (StringUtils.isNotEmptyPlus(str)) {
                logger.info("dyboot-starter调用DyClassLoader加载{}包下的类",str);
                classLoader.load(str);
            }
        }
    }

}
