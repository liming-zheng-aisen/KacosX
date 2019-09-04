package com.duanya.spring.framework.starter.load;

import com.duanya.spring.common.scanner.api.IDyScanner;
import com.duanya.spring.common.scanner.impl.DyScannerImpl;
import com.duanya.spring.framework.annotation.DyBootApplicationStarter;
import com.duanya.spring.framework.context.spring.DySpringApplicationContext;
import com.duanya.spring.framework.core.bean.factory.DyBeanFactory;
import com.duanya.spring.framework.core.load.DyBeanLoad;
import com.duanya.spring.framework.core.load.DyClassLoader;
import com.duanya.spring.framework.core.load.DyConfigurationLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Desc DyBootStarterLoader
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public class DyBootStarterLoader extends  DyBeanLoad {

    private final String STARTER_PATH="com.duanya.spring.framework.starter";

    @Override
    public void load(Class c) throws Exception {

        IDyScanner scanner=new DyScannerImpl(STARTER_PATH,c.getClassLoader());

        List<String> ls= scanner.doScanner(true);

        List<Class> starters=getStarterClass(ls);

        doStarter(starters,c);

        if (null!=nextLoader){
            nextLoader.load(c);
        }

    }

    private List<Class> getStarterClass( List<String> ls) throws ClassNotFoundException {
        List<Class> classes=new ArrayList<>();
        for (String str:ls){
          Class cl=DyBeanFactory.classLoad(str);
          if (cl.isAnnotationPresent(DyBootApplicationStarter.class)){
              classes.add(cl);
          }
        }
        return classes;
    }

    private void doStarter(List<Class> starters,Class main) throws Exception{
        for (Class cl :starters){
            invokeStarter(cl,main);
            DyBootApplicationStarter starter=(DyBootApplicationStarter)cl.getAnnotation(DyBootApplicationStarter.class);
            String[] paths=starter.scannerPath();
            registerToClassLoader(paths,main);
        }
    }

    private void invokeStarter(Class cl,Class main) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class[] ins=cl.getInterfaces();
        for (Class it:ins){
            if (it.getName().equals("com.duanya.spring.framework.starter.DyDefaultStarter")){
                Object beans=DyBeanFactory.createNewBean(cl);
                Class[] params={Properties.class,Class.class,DySpringApplicationContext.class};
                Method method = cl.getMethod("doStart",params);
                method.invoke(beans,DyConfigurationLoader.getEvn(),main,new DySpringApplicationContext());
                break;
            }
        }
    }

    public void registerToClassLoader(String[] paths,Class main) throws Exception {
        DyClassLoader classLoader=new DyClassLoader();
        for (String str:paths){
            classLoader.load(str);
        }
    }

}
