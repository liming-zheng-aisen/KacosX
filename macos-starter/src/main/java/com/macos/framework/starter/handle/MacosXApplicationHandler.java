package com.macos.framework.starter.handle;

import com.macos.common.util.ScannerUtil;
import com.macos.framework.annotation.MacosXApplication;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.handle.base.BaseHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Aisen
 * @mail aisen.zheng.tech@linkkt.one
 * @creater 2020/1/10 17:35:37
 * @desc 应用程序启动器
 */
public class MacosXApplicationHandler extends BaseHandler {

    public MacosXApplicationHandler() {
        handleAnnotations = new Class[]{MacosXApplication.class};
    }

    private List<BaseHandler> handlerList = new ArrayList<>();

    @Override
    public boolean doHandle(Class mainClass, Class handleClass, String[] args) throws Exception {

        if (!needToHandle(handleClass)) {
            return true;
        }

        if (doBefore(mainClass, handleClass, args)) {
            //根据主入口的文件加载同级目录或子目录下的class文件
            //获取类全路径
            String basePackage = mainClass.getPackage().getName();
            Set<Class> result = ScannerUtil.scanner(new String[]{basePackage}, new HashSet<>());
            if (result.size() > 0) {
                BeanManager.registerClassBySet(result);
            }

        }
        return doAfter(mainClass, handleClass, args);

    }
}
