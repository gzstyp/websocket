package com.fwtai.controller;

import com.fwtai.entity.InMessage;
import com.fwtai.entity.OutMessage;
import com.fwtai.tool.ToolClient;
import com.fwtai.tool.ToolString;
import com.fwtai.websocket.SocketSessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
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

    @MessageMapping("/app/user")//接收请求的地址
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
    public OutMessage SendToCommUserMessage(final HttpServletRequest request){
        final List<String> keys = webAgentSessionRegistry.getAllSessionIds().entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
        final Date date = new Date();
        keys.forEach(x -> {
            String sessionId = webAgentSessionRegistry.getSessionIds(x).stream().findFirst().get().toString();
            template.convertAndSendToUser(sessionId,"/topic/greetings",new OutMessage("全网广播," + "内容:" + date.getTime() + "!"),createHeaders(sessionId));
        });
        return new OutMessage("sendcommuser," + new Date() + "!");
    }

    /**
     * 同样的发送消息 只不过是ws版本 http请求不能访问
     * 根据用户key发送消息
     * @param message json 参数为json格式
     * @return
     * @throws Exception
     */
    @MessageMapping("/msg/hellosingle")
    @RequestMapping("/wms/gateway")
    public void greeting2(final InMessage message) throws Exception{
        final Map<String,String> params = new HashMap(1);
        params.put("test","test");
        final HashMap<String,String> object = ToolString.parseJsonObject(message.getJson());
        //这里没做校验
        final Optional<String> target = webAgentSessionRegistry.getSessionIds(message.getId()).stream().findFirst();
        if(target.isPresent()){
            //指定的userId在线
            final String targetId = target.get();
            final String getId = object.get("id");//target.get();
            final String sessionId = targetId == null ? getId : targetId;
            template.convertAndSendToUser(sessionId,"/topic/greetings",new OutMessage("single send to：" + message.getId() + ", from:" + message.getName() + "!"),createHeaders(sessionId));
            final String json = ToolClient.createJsonSuccess("操作成功");
            ToolClient.responseJson(json);
        }else{
            //指定的userId不在线
            final String sessionId = message.getSelfId();
            template.convertAndSendToUser(sessionId,"/topic/greetings",new OutMessage("对方不在线!"),createHeaders(sessionId));
        }
    }

    private MessageHeaders createHeaders(final String sessionId){
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}