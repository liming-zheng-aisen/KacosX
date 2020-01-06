package com.macos.framework.core.handle;
import com.macos.framework.annotation.MacosXApplication;
import com.macos.framework.annotation.MacosXScanner;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.handle.base.BaseHandle;
import com.macos.framework.core.util.ScannerUtil;
import lombok.extern.slf4j.Slf4j;
import java.util.HashSet;
import java.util.Set;


/**
 * @Desc MaocsXScannerHandle
 * @Author Zheng.LiMing
 * @Date 2020/1/1
 */
@Slf4j
public class MacosXScannerHandle extends BaseHandle {

    /**缓存已经执行的class，避免死循环*/
    private static Set<Class> cache = new HashSet<>();

    /**
     * 扫描class，并注册到BeanManager中
     * @param c
     * @throws Exception
     */
    @Override
    public void doHandle(Class c) throws Exception {
        if (cache.contains(c)){
            return;
        }
        cache.add(c);
        String[] basePath = getScannerPath(c);
        if (basePath==null){
            return;
        }
        Set<Class> result = ScannerUtil.doHandle(basePath,null);
        BeanManager.registerClassBySet(result);
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
