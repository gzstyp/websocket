package com.fwtai.controller;

import com.alibaba.fastjson.JSONObject;
import com.fwtai.bean.PageFormData;
import com.fwtai.entity.InMessage;
import com.fwtai.entity.OutMessage;
import com.fwtai.service.TaskService;
import com.fwtai.tool.ToolClient;
import com.fwtai.tool.ToolListOrMap;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class TaskController{

    @Autowired
    private TaskService taskService;

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
     * @return
    */
    @GetMapping(value = "/msg/sendcommuser")
    public OutMessage SendToCommUserMessage(){
        final List<String> keys = webAgentSessionRegistry.getAllSessionIds().entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
        final Date date = new Date();
        keys.forEach(x -> {
            String sessionId = webAgentSessionRegistry.getSessionIds(x).stream().findFirst().get();
            template.convertAndSendToUser(sessionId,"/topic/greetings",new OutMessage("全网广播," + "内容:" + date.getTime() + "!"),createHeaders(sessionId));
        });
        return new OutMessage("sendcommuser," + new Date() + "!");
    }

    /**
     * 同样的发送消息 只不过是ws版本 http请求不能访问
     * 根据用户key发送消息
     * @return
     * @throws Exception
    */
    @MessageMapping("/msg/sendcommuser")
    @RequestMapping("/wms/gateway")
    public OutMessage push(final HttpServletRequest request,final HttpServletResponse response){
        final JSONObject jsonObject = new PageFormData().buildInputStream(request);
        final String p_invoices_code = "invoices_code";
        final String p_type = "type";
        final String p_total = "total";
        final String p_status = "status";
        final String p_data = "data";
        final String validateField = ToolClient.validateField(jsonObject,p_invoices_code,p_type,p_total,p_status,p_data);
        if(validateField != null){
            ToolClient.responseJson(validateField,response);
            return null;
        }
        final String validateInteger = ToolClient.validateInteger(jsonObject,p_type,p_total,p_status);
        if(validateInteger != null){
            ToolClient.responseJson(validateInteger,response);
            return null;
        }
        final String data = jsonObject.getString(p_data);
        final ArrayList<HashMap<String,String>> list = ToolString.parseJsonArrayOriginal(data);
        if(list == null || list.size() <= 0){
            final String json = ToolClient.jsonValidateField();
            ToolClient.responseJson(json,response);
            return null;
        }
        boolean b = false;
        for(int i = 0; i < list.size(); i++){
            final HashMap<String,String> map = list.get(i);
            if( !map.containsKey("item_code") || !map.containsKey("item_name") || !map.containsKey("item_total") || !map.containsKey("item_storage_code")){
                b = true;
                break;
            }
        }
        if(b){
            final String json = ToolClient.jsonValidateField();
            ToolClient.responseJson(json,response);
            return null;
        }
        final Integer row = taskService.insert(jsonObject);
        if(row != 0){
            final ArrayList<String> storages = new ArrayList<>();
            for(int i = 0; i < list.size(); i++){
                storages.add(list.get(i).get("item_storage_code"));
            }
            final List<HashMap<String,String>> listCode = taskService.queryStorageCode(storages);
            //替换货位坐标
            final HashMap<String,String> convertMap = ToolListOrMap.listConvertMap(listCode,"item_storage_code","point");
            for(int i = 0; i < list.size(); i++){
                final String storage_code = list.get(i).get("item_storage_code");
                final String value = convertMap.get(storage_code);
                list.get(i).put("storage_point",value);
            }
            jsonObject.put("data",list);
            final List<String> keys = webAgentSessionRegistry.getAllSessionIds().entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
            keys.forEach(x -> {
                String sessionId = webAgentSessionRegistry.getSessionIds(x).stream().findFirst().get();
                template.convertAndSendToUser(sessionId,"/topic/greetings",new OutMessage(String.valueOf(jsonObject)),createHeaders(sessionId));
            });
            final String result = ToolClient.createJsonSuccess("操作成功");
            ToolClient.responseJson(result,response);
            return null;
        }else{
            final String result = ToolClient.createJsonFail("操作失败");
            ToolClient.responseJson(result,response);
            return null;
        }
    }

    public final HashMap<String,String> listConvertMap(final List<HashMap<String,String>> listCode){
        final HashMap<String,String> map = new HashMap<>();
        for(int i = 0; i < listCode.size(); i++){
            final HashMap<String,String> temp = listCode.get(i);
            final String key = temp.get("item_storage_code");
            final String value = temp.get("point");
            map.put(key,value);
        }
        return map;
    }

    //点对点
    @MessageMapping("/msg/hellosingle000")
    @RequestMapping("/wms/gateway000")
    public void gateway(final InMessage message) throws Exception {
        final Map<String,String> params = new HashMap(1);
        params.put("test","test");
        //这里没做校验
        String sessionId=webAgentSessionRegistry.getSessionIds(message.getId()).stream().findFirst().get();
        template.convertAndSendToUser(sessionId,"/topic/greetings",new OutMessage("single send to："+message.getId()+", from:" + message.getName() + "!"),createHeaders(sessionId));
    }

    //添加货位对应的坐标
    @PostMapping("/wms/addPoint")
    public void addPoint(final HttpServletRequest request,final HttpServletResponse response){
        final PageFormData formData = new PageFormData(request);
        final String json = taskService.addStoragePoint(formData);
        ToolClient.responseJson(json,response);
    }

    @RequestMapping("/wms/gateway0")
    public void gateway(final HttpServletRequest request,final HttpServletResponse response){
        final JSONObject jsonObject = new PageFormData().buildInputStream(request);
        final String json = taskService.insert0(jsonObject);
        ToolClient.responseJson(json,response);
    }

    private MessageHeaders createHeaders(final String sessionId){
        final SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}