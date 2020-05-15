package com.fwtai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2020-05-15 18:01
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
 */
@Controller
public class PageController{

    @RequestMapping(value = "/index")
    public String index(){
        return "/index";
    }

    @RequestMapping(value = "/msg/message")
    public String ToMessage(){
        return "/message";
    }

    @RequestMapping(value = "/msg/messaget2")
    public String ToMessaget2(){
        return "/messaget2";
    }
}