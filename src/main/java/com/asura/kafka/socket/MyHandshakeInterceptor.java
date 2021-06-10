package com.asura.kafka.socket;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 在Spring中提供了websocket拦截器，
 *
 * @author zzyx 2021/6/4/004
 */
@Slf4j
@Component
public class MyHandshakeInterceptor implements HandshakeInterceptor {


    /**
     * 握手之前，若返回false，则不建立连接
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        //将用户id放入socket处理器的会话（webSocketSession）中
        ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
        System.out.println(request.getURI());
        HttpServletRequest servletRequest = request.getServletRequest();
        String uid = servletRequest.getParameter("uid");
        if (StringUtils.isNotBlank(uid)) {
            map.put("uid", uid);
        }
        log.info("开始握手======>");
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        log.info("握手成功======>");
    }
}
