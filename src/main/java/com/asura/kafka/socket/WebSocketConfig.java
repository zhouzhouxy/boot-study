package com.asura.kafka.socket;

 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * @author zzyx 2021/6/4/004
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Resource
    private MyHandshakeInterceptor myHandshakeInterceptor;


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler(), "/ws")
                .setAllowedOrigins("*")
                //将拦截器添加到websocket服务中
                .addInterceptors(this.myHandshakeInterceptor);
    }

    @Bean
    public WebSocketHandler socketHandler() {
        return new SocketHandler();
    }
}
