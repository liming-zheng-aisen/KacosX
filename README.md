# 什么是DySpring
   DySpring是基于 spring、spring mvc、 spring boot、spring cloud 的原理而设置出来，与spring功能类似，但它却不平凡，它将会更好的支持nacos注册中心，以及实现服务的伸缩特性（动态增减服务器，减的规则：一段时间内流量远远没有达到预期；减的三大约束：1.不能再接受新的请求;2.未完成的事情必须做完；3.删除注册中心的服务信息。增的规则：服务压力达到闸值或智能地根据平时的流量情况预先增加服务），为了更好的治理服务，我将打造出一套服务治理方案，统一管理起来，让DevOPS更为爽的架构，以后不需要整合第三方服务，例如jenkins、k8s等。解决了管理困难的问题。让开发者更关注于业务。
# 如何快速使用DySpring
  ### 1.下载源码
    https://github.com/1308404897/DySpring
  ### 2.使用maven打包安装到本地仓库
    mvn install
  ### 3.在工程pom中加入依赖(目前最新的版本是（1.0-SNAPSHOT）)
    <dependency>
            <groupId>com.duanya</groupId>
            <artifactId>dyboot</artifactId>
            <version>${dyboot-v}</version>
    </dependency>
  ### 4.在程序入口的类上使用@DyBootApplication，并在main方法调用 DyBootApplicationWeb.run(Mian.class);
    例如：
      @DyBootApplication
      public class Mian {
      public static void main(String[] args) {
          DyBootApplicationWeb.run(Mian.class);
     }
     }
 # @DyBootApplication注解的作用
