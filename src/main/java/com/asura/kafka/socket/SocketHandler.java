package com.asura.kafka.socket;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 只处理文本形式数据
 *
 * @author zzyx 2021/6/4/004
 */
@Slf4j
public class SocketHandler extends TextWebSocketHandler {
    private final static Map<String, WebSocketSession> SPEC_SESSION_MAP = new ConcurrentHashMap<>();
    private final static Map<String, WebSocketSession> ALL_SESSION_MAP = new ConcurrentHashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //建立连接
        String uid = (String) session.getAttributes().get("uid");
        if (StringUtils.isNotBlank(uid)) {
            //存到map中方便后续处理
            SPEC_SESSION_MAP.put(uid, session);
        }
        ALL_SESSION_MAP.put(session.getId(), session);
        session.sendMessage(new TextMessage("连接成功"));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info(message.getPayload());
        session.sendMessage(new TextMessage("接收消息成功"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        //关闭连接以后将当前session从map中移除
        SPEC_SESSION_MAP.remove((String) session.getAttributes().get("uid"));
        ALL_SESSION_MAP.remove(session.getId());
        log.info("关闭会话");
    }

    /**
     * 发送消息给所有的客户端
     *
     * @param msg 消息
     */
    public void sendMessageForAllClient(String msg) {
        for (Map.Entry<String, WebSocketSession> session : ALL_SESSION_MAP.entrySet()) {
            try {

                session.getValue().sendMessage(new TextMessage(msg));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送消息给部分的客户端
     *
     * @param msg 消息
     */
    public void sendMessageForSpecClient(String msg) {
        for (Map.Entry<String, WebSocketSession> session : SPEC_SESSION_MAP.entrySet()) {
            try {
                session.getValue().sendMessage(new TextMessage(msg));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送消息给部分的客户端
     *
     * @param uid 用户id
     * @param msg 消息
     */
    public void sendMessageForSpecClient(String uid, String msg) {
        WebSocketSession socketSession = SPEC_SESSION_MAP.get(uid);
        if (socketSession != null) {
            try {
                socketSession.sendMessage(new TextMessage(msg));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
