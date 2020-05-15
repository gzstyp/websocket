package com.fwtai.controller;

import com.fwtai.bean.FormData;
import com.fwtai.tool.ToolClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 接收参数并转发到移动端
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2020-05-14 14:34
 * @QQ号码 444141300
 * @Email service@yinlz.com
 * @官网 <url>http://www.yinlz.com</url>
*/
@RestController
@RequestMapping("api")
public class WmsController{

    @Autowired
    private HttpServletRequest request;

    // http://127.0.0.1:81/api/gateway
    @RequestMapping("gateway")
    public final void gateway(final HttpServletResponse response){
        final FormData formData = FormData.buildInputStream(request);
        final String json = ToolClient.validateField(formData,"invoices_code","type","total","status","data");
        if(json != null){
            ToolClient.responseJson(json,response);
            return;
        };
        final String fieldInteger = ToolClient.validateInteger(formData,"type","total");
        if(fieldInteger != null){
            ToolClient.responseJson(fieldInteger,response);
            return;
        }
        //发送到移动端
        System.out.println(formData);
    }
}