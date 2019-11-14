![image](https://github.com/1308404897/DySpring/blob/master/img/testWeb.jpg?raw=true)
# 什么是DySpring
   [DySpring](https://github.com/1308404897/DySpring)是类似Spring boot的框架，一键启动。简单的web框架,dyspring代表一个定制版生态，并不是万能贴，针对微服务场景提供一个解决方案吧了，dyspring对比spring更加易懂和简洁，mvc只是一个路由控制框架，不提供ModeAndView的功能，主流的模式是前后端分离，只有把它去掉了才能更有规范性可言，dyboot starter web 提供了静态页面的访问，但是不提倡这么做，除非是swagger或druid组件的页面，因为druid+Mybatis+Mysql是主流的搭配方案，所以我提供了dyboot starter jdbc 就直接集成了，方便快速开发；Nacos未来成为服务注册与发现的主流，集成了它，可以降低微服务集成成本，因为它提供了配置管理和服务发布、订阅。我开源的目的是三点：1.减小微服务带来的成本；2.使微服务更加简便；3.我想得到更多人的认可！
  ## [案例] (https://github.com/1308404897/dyboot-example)
  ### 1.下载源码
  
    https://codeload.github.com/1308404897/DySpring/zip/master
    或克隆
    https://github.com/1308404897/DySpring.git
    
  ### 2.使用maven打包安装到本地仓库
  
    mvn install
    
  ### 3.在工程pom中加入依赖(目前最新的版本是（1.0.1-SNAPSHOT）)
    <dependency>
            <groupId>com.duanya</groupId>
            <artifactId>dyboot-starter-web</artifactId>
            <version>${dyboot-v}</version>
    </dependency>
    
  ### 4.在程序入口的类上使用@DyBootApplication，并在main方法调用 DyBootApplicationWeb.run(Mian.class);
    例如：
      @DyBootApplication
      public class Mian {
      public static void main(String[] args) {
          DyBootApplicationRun.run(Mian.class);
     }
     }
     
 ## @DyBootApplication注解的作用
@DyBootApplication作用是启动dyboot的配置文件加载、类加载、上下文初始化，默认配置等功能，它相当于@DyScanner@DyAutoConfiguration的注解组合，如果有需要的话，手动配置扫描的路径，以及启动配置功能

 ## @DyScanner注解的作用
@DyScanner程序启动时配置程序扫描包的位置，参数 packageNames 的数据类型是String数组，可以传入多个值，例如@DyScanner(packageNames = {com.a,com.b})

 ## @DyAutoConfiguration注解的作用
@DyAutoConfiguration是启动dyboot默认配置功能

 ## @DyConfiguration注解的作用
 @DyConfiguration是声明一个配置类，主要作用是告诉dyboot要执行里面的内容，一般与@DyBean结合使用，例如创建一个bean并注册到dyboot的上下文中：
   @DyConfiguration
   public class DyAppConfig{
      @DyBean
      public Student initStudent(){
       return new Student();
      }
   }
   
 ## @DyBean注解的作用
 声明一个bean，只能作用与方法上面以及必须与@DyConfiguration一起使用，参数只要一个，value代表bean的标识名字，默认情况下是当前类的父类名字，如果父类是Object则value就是当前类的名字

 ## @DyValue注解的作用
 只能作用与类的属性上面，其作用是将配置文件的属性值注入到类的属性中，可以设置默认值，其表达式为@DyValue("${配置文件对应的属性}:默认值")

 ## @DyComponent注解的作用（单例）
 能在类上面使用，它的作用是告诉dyboot创建当前类的对象，并注册到DySpringApplicationContent上下文中
 
 ## @DyService注解的作用（单例）
 与@DyComponent作用一致，只是用来标识业务层
  
 ## @DyRestController注解的作用（多例）
 与@DyComponent作用类似，但不同的是它是prototype以及它并不是注册到DySpringApplicationContent是注册在mvc的DyServletContext上下文中
 
 ## @DyRequestMapping注解的作用
 以作用在类上也可以作用在方法上，主要作用是映射请求的路径，它可以支持多种请求，参数有两个，第一个参数是value代表请求的路径，默认情况下是"/" ，第二个是method枚举类型的参数代表请求的方式，默认为DyMethod.GET，也就是GET请求
  
 ## @DyGet注解的作用
 只能作用于方法上面，代表get请求，相当于@DyRequestMapping(value="/",method=DyMethod.GET),但它只要一个参数value，代表请求的路径
 
 ## @DyPost注解的作用
 与 @DyGet作用一样，代表post请求
 
 ## @DyPut注解的作用
 与 @DyGet作用一样，代表put请求
  
 ## @DyDelete 注解的作用
 与 @DyGet作用一样，代表delete请求
 
 ## @DyPathVariable注解的作用
 只能作用于方法的参数上面，其作用为获取路径最后的"/"的值为参数值，例如：
 
    @DyGet("/{str}")
    public String getStr（@DyPathVariable String str){
     return str;
    }
  
  ## @DyRequestParameter注解的作用
  只能作用于方法的参数上面，其作用是获取请求路径的参数注入到方法的参数列表上面，参数列表如下:
  
  字段名 | 作用 
  ----|-----
  value | 对应的参数名称
  defaultValue | 默认值
  request | 是否必须的，默认为true
  doc | 参数说明
  
 ## 配置文件加载问题
 dyboot会默认从resource目录下加载dy-application.properties文件，如果需要倒入其他配置文件，则需要载dy-application.properties文件内配置其他配置文件的名字(dy.properties.loader.other)，如果有多个文件则用逗号“,”隔开，建议配置文件都放在同个目录下。例如需要加载mysql.properties、redis.properties文件
 则在dy-application.properties文件配置：
 
    dy.properties.loader.other=mysql.properties,redis.properties
 
 ## 静态资源问题
 dyboot虽然不提倡静态资源放在一起，但是还是提供了静态资源访问，默认情况下会在resource/static目录下访问静态资源，例如在resource/static/index.html,在游览器里面访问地址：http://127.0.0.1:8080/index.html. 默认情况下带点“.”的请求都被认为是静态资源请求！
  
 # 《新版本大改动》
 ## 1.0.1新改动
   ### （1）在1.0版本中，模块之间都放在一个工程里面，而且耦合度太高，1.0.1里面采用责任链设计模式将各个模块分离
   ### （2）在1.0.1版本中，新增几个概念监听器（IDyLoadListener）、加载器（DyBeanLoad）、启动器（DyBootApplicationRun）、适配器（DyDefaultStarter和@DyBootApplicationStarter）
   ### （3）在1.0.1版本中，新增三大管理器，加载器管理器（DyLoaderManager），监听器管理器（DyLoaderListerManager），上下文管理器（DyContextManager），三者关系是：加载器管理器执行load加载到的bean信息添加到上下文中，将上下文对象注册到上下文管理器中，并通知监听器管理器，监听器管理器会挨个通知监听器（通知步骤是多线程的，因为它们之间没有联系，而且需要及时通知并且不影响其他监听的执行，所以多线程通知就可以解决这个问题）
   ### （4）在1.0.1版本中，特别关注的是dyboot-starter模块，它是实现各个组件自动化并与主工程无缝组合的必备模块之一。
  
## 如何实现一个监听器，有什么作用？
   ### 概念：监听dyboot运行期间的运行状态分别为：开始初始化、初始化完成之后、更新
   实现一个IDyLoadListener接口.....

