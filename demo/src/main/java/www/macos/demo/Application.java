package www.macos.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.ISubscriberStream;
import www.macos.demo.web.RoomState;
import www.macos.demo.web.UserState;

/**
 *
 * @author Allen 2017年4月7日
 *
 */
public class Application extends MultiThreadedApplicationAdapter {


    @Override
    public boolean connect(IConnection conn) {
        System.out.println("connect");
        return super.connect(conn);
    }

    @Override
    public void disconnect(IConnection arg0, IScope arg1) {
        System.out.println("disconnect");
        new UserState().delete(arg0.getSessionId(), arg0.getAttribute(arg0.getSessionId()).toString());
        super.disconnect(arg0, arg1);
    }
    /**
     * 开始发布直播
     */
    @Override
    public void streamPublishStart(IBroadcastStream stream) {
        System.out.println("[streamPublishStart]********** ");
        System.out.println("发布Key: " + stream.getPublishedName());
        RoomState room = new RoomState();
        room.insert(stream.getPublishedName(), "房间" + stream.getPublishedName());
        System.out.println(
                "发布时间:" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(stream.getCreationTime())));
        System.out.println("****************************** ");
    }

    /**
     * 流结束
     */
    @Override
    public void streamBroadcastClose(IBroadcastStream arg0) {
        RoomState room = new RoomState();
        room.delete(arg0.getPublishedName());
        super.streamBroadcastClose(arg0);
    }

    /**
     * 用户断开播放
     */
    @Override
    public void streamSubscriberClose(ISubscriberStream arg0) {
        new UserState().delete(arg0.getConnection().getSessionId(), arg0.getBroadcastStreamPublishName());
        super.streamSubscriberClose(arg0);
    }

    /**
     * 链接rtmp服务器
     */
    @Override
    public boolean appConnect(IConnection arg0, Object[] arg1) {
        // TODO Auto-generated method stub
        System.out.println("[appConnect]********** ");
        System.out.println("请求域:" + arg0.getScope().getContextPath());
        System.out.println("id:" + arg0.getClient().getId());
        System.out.println("name:" + arg0.getClient().getId());
        System.out.println("**************** ");
        return super.appConnect(arg0, arg1);
    }

    /**
     * 加入了rtmp服务器
     */
    @Override
    public boolean join(IClient arg0, IScope arg1) {
        // TODO Auto-generated method stub
        return super.join(arg0, arg1);
    }

    /**
     * 开始播放流
     */
    @Override
    public void streamSubscriberStart(ISubscriberStream stream) {
        System.out.println("[streamSubscriberStart]********** ");
        System.out.println("播放域:" + stream.getScope().getContextPath());
        System.out.println("播放Key:" + stream.getBroadcastStreamPublishName());
        System.out.println("********************************* ");
        String sessionId = stream.getConnection().getSessionId();
        stream.getConnection().setAttribute((String) null, null);
        new UserState().insert(sessionId, sessionId, stream.getCreationTime(), stream.getBroadcastStreamPublishName());
        super.streamSubscriberStart(stream);
    }

    /**
     * 离开了rtmp服务器
     */
    @Override
    public void leave(IClient arg0, IScope arg1) {
        System.out.println("leave");
        super.leave(arg0, arg1);
    }

}