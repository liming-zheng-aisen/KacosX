package com.macos;

import org.reflections.Reflections;
import org.reflections.scanners.*;
import org.reflections.util.ConfigurationBuilder;
import java.util.Set;

/**
 * @author Aisen
 * @mail aisen.zheng.tech@linkkt.one
 * @creater 2020/1/6 14:15:57
 * @desc
 */
public class Test {
    public static void main(String[] args) {
        // 扫包
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages("com.macos") // 指定路径URL
                .addScanners(new SubTypesScanner()) // 添加子类扫描工具
                .addScanners(new FieldAnnotationsScanner()) // 添加 属性注解扫描工具
                .addScanners(new MethodAnnotationsScanner() ) // 添加 方法注解扫描工具
                .addScanners(new MethodParameterScanner() ) // 添加方法参数扫描工具
        );

        // 反射出子类
//        Set<String> set = reflections.getSubTypesOf();
//        System.out.println("getSubTypesOf:" + set);

        // 反射出带有指定注解的类
        //Set<Class<?>> ss = reflections.getTypesAnnotatedWith( MyAnnotation.class );
        //System.out.println("getTypesAnnotatedWith:" + ss);

        // 获取带有特定注解对应的方法
        //Set<Method> methods = reflections.getMethodsAnnotatedWith( MyMethodAnnotation.class ) ;
        //System.out.println("getMethodsAnnotatedWith:" + methods);

        // 获取带有特定注解对应的字段
       // Set<Field> fields = reflections.getFieldsAnnotatedWith( Autowired.class ) ;
       // System.out.println("getFieldsAnnotatedWith:" + fields);

        // 获取特定参数对应的方法
       // Set<Method> someMethods = reflections.getMethodsMatchParams(long.class, int.class);
        //Set<Method> voidMethods = reflections.getMethodsReturn(void.class);
       // System.out.println( "getMethodsReturn:" + voidMethods);

       // Set<Method> pathParamMethods =reflections.getMethodsWithAnyParamAnnotated( PathParam.class);
       // System.out.println("getMethodsWithAnyParamAnnotated:" + pathParamMethods);
    }


}
