package com.fwtai.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 任务明细
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2020-05-17 11:23
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
*/
public final class TaskDetail{

    private String kid;
    private String task_id;
    @ApiModelProperty(notes = "任务单号|编号")
    private String invoices_code;
    @ApiModelProperty(notes = "物资编号")
    private String item_code;
    @ApiModelProperty(notes = "物资名称")
    private String item_name;
    @ApiModelProperty(notes = "物资数量")
    private String item_total;
    @ApiModelProperty(notes = "货位号")
    private String item_storage_code;
    private String gmt_create;

    public String getKid(){
        return kid;
    }

    public void setKid(String kid){
        this.kid = kid;
    }

    public String getTask_id(){
        return task_id;
    }

    public void setTask_id(String task_id){
        this.task_id = task_id;
    }

    public String getInvoices_code(){
        return invoices_code;
    }

    public void setInvoices_code(String invoices_code){
        this.invoices_code = invoices_code;
    }

    public String getItem_code(){
        return item_code;
    }

    public void setItem_code(String item_code){
        this.item_code = item_code;
    }

    public String getItem_name(){
        return item_name;
    }

    public void setItem_name(String item_name){
        this.item_name = item_name;
    }

    public String getItem_total(){
        return item_total;
    }

    public void setItem_total(String item_total){
        this.item_total = item_total;
    }

    public String getItem_storage_code(){
        return item_storage_code;
    }

    public void setItem_storage_code(String item_storage_code){
        this.item_storage_code = item_storage_code;
    }

    public String getGmt_create(){
        return gmt_create;
    }

    public void setGmt_create(String gmt_create){
        this.gmt_create = gmt_create;
    }
}