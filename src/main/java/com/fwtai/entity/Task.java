package com.fwtai.entity;

import java.util.List;

/**
 * 任务指令
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2020-05-17 22:23
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
 */
public final class Task{

    private String id;

    private String kid;

    private String invoices_code;

    private Integer type;

    private Integer total;

    private Integer status;

    private List<TaskDetail> data;

    public Task(){}

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getKid(){
        return kid;
    }

    public void setKid(String kid){
        this.kid = kid;
    }

    public String getInvoices_code(){
        return invoices_code;
    }

    public void setInvoices_code(String invoices_code){
        this.invoices_code = invoices_code;
    }

    public Integer getType(){
        return type;
    }

    public void setType(Integer type){
        this.type = type;
    }

    public Integer getTotal(){
        return total;
    }

    public void setTotal(Integer total){
        this.total = total;
    }

    public Integer getStatus(){
        return status;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public List<TaskDetail> getData(){
        return data;
    }

    public void setData(List<TaskDetail> data){
        this.data = data;
    }
}