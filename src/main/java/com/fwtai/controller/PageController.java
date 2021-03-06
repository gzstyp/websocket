package com.fwtai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 页面跳转
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2020-05-15 18:01
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
*/
@Controller
@ApiIgnore
public class PageController{

    @GetMapping(value = "/index")
    public String index(){
        return "index";
    }

    @GetMapping(value = "/message")
    public String ToMessage(){
        return "message";
    }

    @GetMapping(value = "/messaget2")
    public String ToMessaget2(){
        return "messaget2";
    }

    //货位号管理
    @GetMapping(value = "/point")
    public String point(){
        return "point";
    }

    //货位号管理
    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }
}