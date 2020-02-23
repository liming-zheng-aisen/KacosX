package com.macos.framework.starter.handle;

import com.macos.common.scanner.api.ScannerApi;
import com.macos.common.scanner.impl.ScannerImpl;
import com.macos.common.util.ReflectionsUtil;
import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.MacosXApplicationStarter;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.env.ApplicationENV;
import com.macos.framework.core.handle.base.BaseHandler;
import com.macos.framework.starter.DefaultStarter;
import com.macos.framework.starter.load.StarterBean;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Desc 启动器处理器
 * @Author Zheng.LiMing
 * @Date 2020/1/14
 */
@Slf4j
@SuppressWarnings("all")
public class MacosXApplicationStarterHandler extends BaseHandler {

    public MacosXApplicationStarterHandler(){
        handleAnnotations = new Class[]{MacosXApplicationStarter.class};
    }

    private final String STARTER_PATH = "com.macos.framework.starter";

     private ScannerApi scanner = new ScannerImpl();

    @Override
    public boolean doHandle(Class mainClass, Class handleClass, String[] args) throws Exception {
        if (doBefore(mainClass, handleClass, args)) {


            Set<Class> starters = scanner.doScanner(STARTER_PATH, MacosXApplicationStarter.class);

            Set<StarterBean> starterBeanSet = convertToStarterBean(starters);

            doStarter(starterBeanSet, mainClass, args);

        }
        return doAfter(mainClass, handleClass, args);
    }

    private Set<StarterBean> convertToStarterBean(Set<Class> classes) {
        Set<StarterBean> starterBeanSet = new HashSet<>();
        for (Class c : classes) {
            if (c.isAnnotationPresent(MacosXApplicationStarter.class)) {
                MacosXApplicationStarter starter = (MacosXApplicationStarter) c.getAnnotation(MacosXApplicationStarter.class);
                Integer order = starter.order();
                String[] path = starter.scannerPath();
                StarterBean starterBean = new StarterBean(order, c, path);
                starterBeanSet.add(starterBean);
            }
        }
        return starterBeanSet.stream().sorted(Comparator.comparing(StarterBean::getOrder)).collect(Collectors.toSet());
    }

    private void doStarter(Set<StarterBean> starters, Class main, String[] args) throws Exception {
        for (StarterBean cl : starters) {
            invokeStarter(cl.getTarget(), main, args);
            registerToClassLoader(cl.getScannerPath());
        }
    }

    private void invokeStarter(Class cl, Class main, String[] args) throws Exception {
        Class[] ins = cl.getInterfaces();
        ApplicationENV env = new ApplicationENV();

        BeanDefinition beanDefinition = BeanManager.getBeanDefinition(null, ApplicationENV.class, true);
        if (beanDefinition != null) {
            env = (ApplicationENV) beanDefinition.getContextApi().getBean(null, ApplicationENV.class);
        }

        for (Class it : ins) {
            if (it.getName().equals("com.macos.framework.starter.DefaultStarter")) {
                DefaultStarter defaultStarter = (DefaultStarter) ReflectionsUtil.createNewBean(cl);
                defaultStarter.doStart( env, main , args);
                log.info("macos-starter执行{}的doStart方法", cl.getSimpleName());
                break;
            }
        }
    }

    public void registerToClassLoader(String[] paths) throws Exception {
        for (String str : paths) {
            if (StringUtils.isNotEmptyPlus(str)) {
                log.info("macos-starter调用ApplicationClassLoader加载{}包下的类", str);
                BeanManager.registerClassBySet(scanner.doScanner(str));
            }
        }
    }
}
