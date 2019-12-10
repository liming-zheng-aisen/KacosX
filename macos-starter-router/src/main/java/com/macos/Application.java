package com.macos;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.InetAddress;


/**
 * @author Aisen
 * @mail aisen.zheng.tech@linkkt.one
 * @creater 2019/12/9 16:28:21
 * @desc
 */
@SuppressWarnings("all")
public class Application {
    /**设置服务端端口*/
    private static final int port = 6789;
    /**
     * 通过nio方式来接收连接和处理连接
     */
    private static  EventLoopGroup group = new NioEventLoopGroup();
    private static  ServerBootstrap b = new ServerBootstrap();
    /**
     * Netty创建全部都是实现自AbstractBootstrap。
     * 客户端的是Bootstrap，服务端的则是    ServerBootstrap。
     **/
    public void start() throws InterruptedException {
        try {
            b.group(group);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new NettyServerFilter()); //设置过滤器
            // 服务器绑定端口监听
            ChannelFuture f = b.bind(port).sync();
            System.out.println("服务端启动成功...");
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully(); ////关闭EventLoopGroup，释放掉所有资源包括创建的线程
        }
    }
    public class NettyServerHandler extends ChannelInboundHandlerAdapter {

        private String result="";
        /*
         * 收到消息时，返回信息
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if(! (msg instanceof FullHttpRequest)){
                result="未知请求!";
                send(ctx,result,HttpResponseStatus.BAD_REQUEST);
                return;
            }
            FullHttpRequest httpRequest = (FullHttpRequest)msg;
            try{
                String path=httpRequest.uri();          //获取路径
                String body = getBody(httpRequest);     //获取参数
                HttpMethod method=httpRequest.method();//获取请求方法
                //如果不是这个路径，就直接返回错误
                if(!"/test".equalsIgnoreCase(path)){
                    result="非法请求!";
                    send(ctx,result,HttpResponseStatus.BAD_REQUEST);
                    return;
                }
                System.out.println("接收到:"+method+" 请求");
                //如果是GET请求
                if(HttpMethod.GET.equals(method)){
                    //接受到的消息，做业务逻辑处理...
                    System.out.println("body:"+body);
                    result="GET请求";
                    send(ctx,result,HttpResponseStatus.OK);
                    return;
                }
                //如果是POST请求
                if(HttpMethod.POST.equals(method)){
                    //接受到的消息，做业务逻辑处理...
                    System.out.println("body:"+body);
                    result="POST请求";
                    send(ctx,result,HttpResponseStatus.OK);
                    return;
                }

                //如果是PUT请求
                if(HttpMethod.PUT.equals(method)){
                    //接受到的消息，做业务逻辑处理...
                    System.out.println("body:"+body);
                    result="PUT请求";
                    send(ctx,result,HttpResponseStatus.OK);
                    return;
                }
                //如果是DELETE请求
                if(HttpMethod.DELETE.equals(method)){
                    //接受到的消息，做业务逻辑处理...
                    System.out.println("body:"+body);
                    result="DELETE请求";
                    send(ctx,result,HttpResponseStatus.OK);
                    return;
                }
            }catch(Exception e){
                System.out.println("处理请求失败!");
                e.printStackTrace();
            }finally{
                //释放请求
                httpRequest.release();
            }
        }
        /**
         * 获取body参数
         * @param request
         * @return
         */
        private String getBody(FullHttpRequest request){
            ByteBuf buf = request.content();
            return buf.toString(CharsetUtil.UTF_8);
        }

        /**
         * 发送的返回值
         * @param ctx     返回
         * @param context 消息
         * @param status 状态
         */
        private void send(ChannelHandlerContext ctx, String context,HttpResponseStatus status) {
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer(context, CharsetUtil.UTF_8));
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }

        /*
         * 建立连接时，返回消息
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("连接的客户端地址:" + ctx.channel().remoteAddress());
            ctx.writeAndFlush("客户端"+ InetAddress.getLocalHost().getHostName() + "成功与服务端建立连接！ ");
            super.channelActive(ctx);
        }

    }


    public class NettyServerFilter extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline ph = ch.pipeline();
            ph.addLast("encoder",new HttpResponseEncoder());
            ph.addLast("decoder",new HttpRequestDecoder());
            ph.addLast("aggregator", new HttpObjectAggregator(10*1024*1024));//把单个http请求转为FullHttpReuest或FullHttpResponse
            ph.addLast("handler", new NettyServerHandler());// 服务端业务逻辑
        }
    }
    public static void main(String[] args) {
        Application application = new Application();
        try {
            application.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
