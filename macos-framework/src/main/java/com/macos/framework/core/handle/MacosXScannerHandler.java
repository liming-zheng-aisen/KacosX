package com.macos.framework.core.handle;
import com.macos.framework.annotation.MacosXApplication;
import com.macos.framework.annotation.MacosXScanner;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.handle.base.BaseHandler;
import com.macos.framework.core.util.ScannerUtil;
import lombok.extern.slf4j.Slf4j;
import java.util.HashSet;
import java.util.Set;


/**
 * @Desc 扫描处理器，用于加载Class
 * @Author Zheng.LiMing
 * @Date 2020/1/1
 */
@Slf4j
@SuppressWarnings("all")
public class MacosXScannerHandler extends BaseHandler {

    static {
        handleAnnotations = new Class[]{MacosXScanner.class, MacosXApplication.class};
    }

    /**缓存已经执行的class，避免死循环*/
    private static Set<Class> cache = new HashSet<>();

    /**
     * 扫描class，并注册到BeanManager中
     * @param mainClass 程序入口对象
     * @param handleClass 当前处理对象
     * @param args
     * @return
     * @throws Exception
     */
    @Override
    public boolean doHandle(Class mainClass,Class handleClass,String[] args) throws Exception {
        //如果处理对象已经被处理过，则跳过
        if (cache.contains(handleClass)){
            return true;
        }
        cache.add(handleClass);
        //获取处理对象的扫描路径
        String[] basePath = getScannerPath(handleClass);
        if (basePath==null){
            return true;
        }
        doBefore(mainClass,handleClass,args);
        Set<Class> result = ScannerUtil.scanner(basePath,null);
        if (result.size()>0) {
            BeanManager.registerClassBySet(result);
        }
        doAfter(mainClass,handleClass,args);
        return true;
    }

    /**
     * 获取扫描路径
     * @param c
     * @return
     * @throws Exception
     */
    private String[] getScannerPath(Class c) throws Exception {
        if (c.isAnnotationPresent(MacosXScanner.class)) {
            MacosXScanner macosXScanner = (MacosXScanner) c.getAnnotation(MacosXScanner.class);
            String[] packageNames = macosXScanner.packageNames();
            if (packageNames.length == 0) {
                log.error("扫描路径不允许为空或\"  \"");
                throw new Exception("扫描路径不允许为空或\"  \"");
            }
            return packageNames;
        } else if (c.isAnnotationPresent(MacosXApplication.class)){
            //根据主入口的文件加载同级目录或子目录下的class文件
            //获取类全路径
            String basePackage = c.getPackage().getName();
            return new String[]{basePackage};
        }
        return null;
    }

}
