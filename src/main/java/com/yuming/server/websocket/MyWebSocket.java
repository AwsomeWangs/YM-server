package com.yuming.server.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket服务端
 * @author chen jia hao
 */
@Component
@ServerEndpoint(value = "/websocket/{name}")
@RestController
public class MyWebSocket {

//    //用来存放每个客户端对应的MyWebSocket对象
//    private static CopyOnWriteArraySet<MyWebSocket> user = new CopyOnWriteArraySet<MyWebSocket>();
//
//    //与某个客户端的连接会话，需要通过它来给客户端发送数据
//    private Session session;
//
//    @OnMessage
//    public void onMessage(String message,Session session) throws IOException {
//
//        //群发消息
//        for (MyWebSocket myWebSocket : user) {
//            myWebSocket.session.getBasicRemote().sendText(session.getId()+"说："+message);
//            //myWebSocket.session.getBasicRemote().sendText("<img src=''/>");
//        }
//    }
//
//    @OnOpen
//    public void onOpen(Session session){
//        System.out.println(session.getId()+" open...");
//        this.session = session;
//        user.add(this);
//    }
//    @OnClose
//    public void onClose(){
//        System.out.println(this.session.getId()+" close...");
//        user.remove(this);
//    }
//
//    @OnError
//    public void onError(Session session,Throwable error){
//        System.out.println(this.session.getId()+" error...");
//        error.printStackTrace();
//    }


    //存储客户端的连接对象,每个客户端连接都会产生一个连接对象
    private static ConcurrentHashMap<String,MyWebSocket> map=new ConcurrentHashMap();
    //每个连接都会有自己的会话
    private Session session;
    private String name;
    @OnOpen
    public void open(@PathParam("name") String name, Session session){
        map.put(name,this);
        System.out.println(name+"连接服务器成功");
        System.out.println("客户端连接个数:"+getConnetNum());

        this.session=session;
        this.name=name;
    }
    @OnClose
    public void close(){
        map.remove(name);
        System.out.println(name+"断开了服务器连接");
    }
    @OnError
    public void error(Throwable error){
        error.printStackTrace();
        System.out.println(name+"出现了异常");
    }
    @OnMessage
    public void getMessage(String message) throws IOException {
        System.out.println("收到"+name+":"+message);
        System.out.println("客户端连接个数:"+getConnetNum());

        Set<Map.Entry<String, MyWebSocket>> entries = map.entrySet();
        for (Map.Entry<String, MyWebSocket> entry : entries) {
            if(!entry.getKey().equals(name)){//将消息转发到其他非自身客户端
                entry.getValue().send(message);

            }
        }
    }

    public void send(String message) throws IOException {
        if(session.isOpen()){
            session.getBasicRemote().sendText(message);
        }
    }

    public int  getConnetNum(){
        return map.size();
    }
}
