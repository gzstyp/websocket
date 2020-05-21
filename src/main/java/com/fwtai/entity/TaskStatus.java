package com.fwtai.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 更新任务对象
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2020-05-19 19:48
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
*/
public final class TaskStatus{

    @ApiModelProperty(notes = "任务单号|编号")
    private String invoices_code;

    @ApiModelProperty(notes = "物资编号")
    private String item_code;

    @ApiModelProperty(notes = "任务状态(1待执行;2进行中;3完成;)")
    private Integer status;

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

    public Integer getStatus(){
        return status;
    }

    public void setStatus(Integer status){
        this.status = status;
    }
}