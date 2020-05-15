#SpringbootWebSocket

SpringBoot整合Websocket，实现广播式和给特定用户发送消息，不需要登录即可发送消息。

# 示例
>  演示的发送消息需要两个用户同时开启，因为写的是相互发送消息。切记

## 主页

> 访问http://localhost:82进入主页,主要提供两个不同的用户ricky和rickyt2,以及发送公共消息功能。

 ![](/src/main/resources/static/image/index.png) 
 
## ricky用户 
> 进入之后，先点击连接connect，输入sendName即可发送消息 

 ![](/src/main/resources/static/image/ricky.png)
 
  
## rickyt2用户 
> 进入之后，先点击连接connect，输入sendName即可发送消息 

 ![](/src/main/resources/static/image/rickyt2.png)
 
## 发送公共消息
 
 点击主页的公共消息按钮，即可发送公共消息，如果想重新发送，刷新公共消息页面即可。
 
#说明
 > 用户的key是从页面传递过来的，发送给特殊用户的key也是从页面传递的，如果想给特定用户发送消息，可以实现相关功能，这里只做演示，所以写死了。