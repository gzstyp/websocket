package com.fwtai.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * socket核心配置容器
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry config){
        config.enableSimpleBroker("/topic");// users 默认通知
        config.setApplicationDestinationPrefixes("/app");
        //设置前缀  默认是user 可以修改  点对点时使用
        config.setUserDestinationPrefix("/ricky/");
    }

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry){
        registry.addEndpoint("/initWebsocket").withSockJS();//客户端连接的url地址
    }

    @Bean
    public SocketSessionRegistry SocketSessionRegistry(){
        return new SocketSessionRegistry();
    }

    @Bean
    public STOMPConnectEventListener STOMPConnectEventListener(){
        return new STOMPConnectEventListener();
    }
}