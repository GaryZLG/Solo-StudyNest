package com.garyproject.mooc.webSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

@Component
@ServerEndpoint("/webSocket/{username}")
@Slf4j
public class WebSocket {
  private Session session;

  private String username;

  private String toUsername;

  private static Integer userNumber = 0;

  private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

  @OnOpen
  public void onOpen(Session session, @PathParam("username") String username) throws IOException {
    this.session = session;
    webSocketSet.add(this);
    userNumber++;
    this.username = username;

    //获取所有的用户
    Set<String> userList = new TreeSet<>();
    for (WebSocket item : webSocketSet) {
      userList.add(item.username);
    }

    //所有信息给浏览器，所有用户都知道
    Map<String, Object> map = new HashMap<>();
    map.put("onlineUsers", userList);
    //1代表上线 2代表下线 3在线名单 4普通消息
    map.put("messageType", 1);
    map.put("username", username);
    map.put("userNumber", userNumber);
    sendMessageAll(JSON.toJSONString(map), username);
    log.info("总人数: {}",  userNumber);

    //更新在线人数
    Map<String, Object> map2 = new HashMap<>();
    map2.put("messageType", 3);
    map2.put("onlineUsers", userList);
    map2.put("userNumber", userNumber);
    sendMessageAll(JSON.toJSONString(map2), username);
  }

  @OnClose
  public void onClose() throws IOException {
    webSocketSet.remove(this);
    userNumber--;
    //通知下线
    Map<String, Object> map = new HashMap<>();
    map.put("messageType", 2);
    map.put("onlineUsers", webSocketSet);
    map.put("username", username);
    map.put("userNumber", userNumber);
    sendMessageAll(JSON.toJSONString(map), username);

    //更新在线人数
    Map<String, Object> map2 = new HashMap<>();
    Set<String> userList = new TreeSet<>();
    for (WebSocket item : webSocketSet) {
      userList.add(item.username);
    }
    map2.put("messageType", 3);
    map2.put("onlineUsers", userList);
    map2.put("userNumber", userNumber);
    sendMessageAll(JSON.toJSONString(map2), username);
    log.info("有人断开离线，总人数: {}",  webSocketSet.size());
  }

  @OnMessage
  public void onMessage(String message) throws IOException{
    log.info("收到浏览器发来的消息: {}",  message);
    JSONObject jsonObject = JSON.parseObject(message);
    String textMessage = jsonObject.getString("message");
    String username = jsonObject.getString("username");
    String type = jsonObject.getString("type");
    String toUsername = jsonObject.getString("toUsername");

    if (type.equals("群发")) {
      Map<String, Object> map = new HashMap<>();
      map.put("messageType", 4);
      map.put("onlineUsers", webSocketSet);
      map.put("username", username);
      map.put("userNumber", userNumber);
      map.put("message", textMessage);
    } else {
      Map<String, Object> map2 = new HashMap<>();
      map2.put("messageType", 4);
      map2.put("onlineUsers", webSocketSet);
      map2.put("username", username);
      map2.put("userNumber", userNumber);
      map2.put("message", textMessage);
      sendMessageTo(JSON.toJSONString(map2), toUsername);

      //给自己再发个
    }
  }

  /**
   * 发消息给所有人
   * @param message
   * @param FromUsername
   * @throws IOException
   */
  public void sendMessageAll(String message, String FromUsername) throws IOException {
    for (WebSocket item : webSocketSet) {
      item.session.getBasicRemote().sendText(FromUsername + ":" + message);
    }
  }


  public void sendMessageTo(String message, String toUsername) throws IOException {
    for (WebSocket item : webSocketSet) {
      if (item.username.equals(toUsername)) {
        item.session.getBasicRemote().sendText(this.username + ":" + message);
        log.info("【成功发送消息】: {}",  this.username + "向" + toUsername + "发送消息： " + message);
      }
    }
  }

}
