package com.fwtai.entity;

/**
 * 消息接收实体
 */
public class InMessage{

    private String name;

    private String id;

    private String selfId;

    private String json;

    public InMessage(){
    }

    public InMessage(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getSelfId(){
        return selfId;
    }

    public void setSelfId(String selfId){
        this.selfId = selfId;
    }

    public String getJson(){
        return json;
    }

    public void setJson(String json){
        this.json = json;
    }
}
