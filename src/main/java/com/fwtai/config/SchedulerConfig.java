package com.fwtai.config;

import com.fwtai.dao.DaoHandle;
import com.fwtai.entity.OutMessage;
import com.fwtai.tool.ToolClient;
import com.fwtai.websocket.SocketSessionRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@EnableScheduling
@Configuration
public class SchedulerConfig {

    @Resource
    private SimpMessagingTemplate template;

    @Resource
    private DaoHandle daoHandle;

    /**session操作类*/
    @Resource
    private SocketSessionRegistry webAgentSessionRegistry;

    //@Scheduled(fixedDelay = 10000)
    public void sendMessages(){
        final HashMap<String,Object> map = daoHandle.queryForHashMap("wms.getTaskData");
        if(map == null || map.size() <= 0)return;
        final String kid = String.valueOf(map.get("kid"));
        final List<HashMap<String,Object>> list = daoHandle.queryForListHashMap("wms.getListDetail",kid);
        if(list == null || list.size() <= 0)return;
        daoHandle.execute("wms.updateFlag",kid);
        final HashMap<String,Object> result = new HashMap<>();
        result.put("task",map);
        result.put("list",list);
        final String json = ToolClient.queryJson(result);
        final List<String> keys = webAgentSessionRegistry.getAllSessionIds().entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
        keys.forEach(x -> {
            String sessionId = webAgentSessionRegistry.getSessionIds(x).stream().findFirst().get();
            template.convertAndSendToUser(sessionId,"/topic/greetings",new OutMessage(json),createHeaders(sessionId));
        });
    }

    private MessageHeaders createHeaders(final String sessionId){
        final SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}