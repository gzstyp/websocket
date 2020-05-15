package com.fwtai.controller;

import com.fwtai.entity.InMessage;
import com.fwtai.entity.OutMessage;
import com.fwtai.websocket.SocketSessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**聊天控制器*/
@Controller
@CrossOrigin
public class GreetingController{

    /**
     * session操作类
     */
    @Autowired
    private SocketSessionRegistry webAgentSessionRegistry;

    /**
     * 消息发送工具
     */
    @Autowired
    private SimpMessagingTemplate template;

    @RequestMapping(value = "/index")
    public String index(){
        return "/index";
    }

    @RequestMapping(value = "/msg/message")
    public String ToMessage(){
        return "/message";
    }

    @RequestMapping(value = "/msg/messaget2")
    public String ToMessaget2(){
        return "/messaget2";
    }

    @MessageMapping("/app/user")//接收请求的地址
    @RequestMapping("/app/user")
    @SendTo("/topic/user")//发送的url地址
    public OutMessage getUser(final String userName){
        System.out.println("*********************************************");
        return new OutMessage("sendcommuser, " + userName + "!");
    }

    /**
     * 用户广播
     * 发送消息广播  用于内部发送使用
     * @param request
     * @return
     */
    @GetMapping(value = "/msg/sendcommuser")
    public @ResponseBody
    OutMessage SendToCommUserMessage(final HttpServletRequest request){
        List<String> keys = webAgentSessionRegistry.getAllSessionIds().entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
        Date date = new Date();
        keys.forEach(x -> {
            String sessionId = webAgentSessionRegistry.getSessionIds(x).stream().findFirst().get().toString();
            template.convertAndSendToUser(sessionId,"/topic/greetings",new OutMessage("commmsg：allsend, " + "send  comm" + date.getTime() + "!"),createHeaders(sessionId));
        });
        return new OutMessage("sendcommuser, " + new Date() + "!");
    }

    /**
     * 同样的发送消息   只不过是ws版本 http请求不能访问
     * 根据用户key发送消息
     * @param message
     * @return
     * @throws Exception
     */
    @MessageMapping("/msg/hellosingle")
    public void greeting2(InMessage message) throws Exception{
        Map<String,String> params = new HashMap(1);
        params.put("test","test");
        //这里没做校验
        final String sessionId = webAgentSessionRegistry.getSessionIds(message.getId()).stream().findFirst().get();
        template.convertAndSendToUser(sessionId,"/topic/greetings",new OutMessage("single send to：" + message.getId() + ", from:" + message.getName() + "!"),createHeaders(sessionId));
    }

    private MessageHeaders createHeaders(String sessionId){
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}