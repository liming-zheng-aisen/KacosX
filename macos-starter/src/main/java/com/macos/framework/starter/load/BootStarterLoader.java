package com.macos.framework.starter.load;



import com.macos.common.scanner.api.ScannerApi;
import com.macos.common.scanner.impl.ScannerImpl;
import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.MacosApplicationStarter;
import com.macos.framework.core.bean.factory.BeanFactory;
import com.macos.framework.core.load.clazz.ApplicationClassLoader;
import com.macos.framework.core.load.abs.BeanLoad;
import com.macos.framework.core.load.conf.ConfigurationLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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

    private static Logger logger= (Logger) LoggerFactory.getLogger(BootStarterLoader.class);


    private final String STARTER_PATH="com.macos.framework.starter";

    @Override
    public void load(Class c) throws Exception {
        logger.info("开始加载macos-starter，默认包为{}",STARTER_PATH);
        ScannerApi scanner=new ScannerImpl();

        Set<Class> starters= scanner.doScanner(STARTER_PATH, MacosApplicationStarter.class);

        Set<StarterBean> starterBeanSet=convertToStarterBean(starters);

        doStarter(starterBeanSet,c);

        logger.debug("macos-starter加载结束");

        if (null!=nextLoader){
            nextLoader.load(c);
        }
    }
    private  Set<StarterBean> convertToStarterBean(Set<Class> classes){
        Set<StarterBean> starterBeanSet=new HashSet<>();
        for (Class c:classes){
            if (c.isAnnotationPresent(MacosApplicationStarter.class)) {
                MacosApplicationStarter starter = (MacosApplicationStarter) c.getAnnotation(MacosApplicationStarter.class);
                Integer order = starter.order();
                String[] path = starter.scannerPath();
                StarterBean starterBean=new StarterBean(order,c,path);
                starterBeanSet.add(starterBean);
            }
        }
      return starterBeanSet.stream().sorted(Comparator.comparing(StarterBean::getOrder)).collect(Collectors.toSet());
    }

    private void doStarter(Set<StarterBean> starters, Class main) throws Exception{
        for (StarterBean cl :starters){
            invokeStarter(cl.getTarget(),main);
            registerToClassLoader(cl.getScannerPath());
        }
    }

    private void invokeStarter(Class cl,Class main) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class[] ins=cl.getInterfaces();
        for (Class it:ins){
            if (it.getName().equals("com.macos.framework.starter.DefaultStarter")){
                Object beans= BeanFactory.createNewBean(cl);
                Class[] params={Properties.class,Class.class};
                Method method = cl.getMethod("doStart",params);
                method.invoke(beans, ConfigurationLoader.getEvn(),main);
                logger.info("macos-starter执行{}的doStart方法",cl.getSimpleName());
                break;
            }
        }
    }

    public void registerToClassLoader(String[] paths) throws Exception {
        ApplicationClassLoader classLoader=new ApplicationClassLoader();
        for (String str:paths){
            if (StringUtils.isNotEmptyPlus(str)) {
                logger.info("macos-starter调用ClassLoader加载{}包下的类",str);
                classLoader.load(str);
            }
        }
    }

}
