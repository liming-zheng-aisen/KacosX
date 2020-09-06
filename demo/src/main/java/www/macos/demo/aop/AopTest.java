package www.macos.demo.aop;

import com.mx.aop.core.even.bean.EvenBeanReturn;
import com.mx.framework.annotation.aop.*;

/**
 * @Desc AopTest
 * @Author Zheng.LiMing
 * @Date 2020/4/2
 */
@Aspect("测试")
public class AopTest {

    @Pointcut(value = "public java.lang.String www.macos.demo.web.DemoWeb*.*(*)")
    public void point(){

    }

    @Before(pointcup = "point()")
    public void test(){
        System.out.println("来了哦-------1-----");
    }

    @AfterFinal(pointcup = "point()")
    public void test2(){
        System.out.println("来了哦-----123212-------");
    }

    @Around(pointcup = "point()")
    public Object test3(EvenBeanReturn evenBeanReturn) throws Throwable {
        System.out.println("123");
        return evenBeanReturn.invoke();
    }
}
