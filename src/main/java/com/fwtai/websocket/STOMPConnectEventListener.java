package com.fwtai.websocket;

import com.fwtai.dao.DaoHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import javax.annotation.Resource;

/**
 * stomp监听类
 * 用于session注册 以及key值获取
 */
public class STOMPConnectEventListener  implements ApplicationListener<SessionConnectEvent> {

    @Resource
    private SimpMessagingTemplate template;

    @Resource
    private DaoHandle daoHandle;

    /**session操作类*/

    @Autowired
    private SocketSessionRegistry webAgentSessionRegistry;

    @Override
    public void onApplicationEvent(final SessionConnectEvent event) {
        final StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        //browser客户端登录连接
        final String agentId = sha.getNativeHeader("login").get(0);//客户端初始化连接,含认证信息
        final String sessionId = sha.getSessionId();
        webAgentSessionRegistry.registerSessionId(agentId,sessionId);
    }
}