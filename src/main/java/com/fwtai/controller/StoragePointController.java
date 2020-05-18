package com.fwtai.controller;

import com.fwtai.service.StoragePointService;
import com.fwtai.tool.ToolClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
@RequestMapping("wms")
public class StoragePointController{

    @Autowired
    private HttpServletRequest request;

    @Resource
    private StoragePointService service;

    @GetMapping("getListData")
    public final void getListData(final HttpServletResponse response){
        final String json = service.getListData();
        ToolClient.responseJson(json,response);
    }
}