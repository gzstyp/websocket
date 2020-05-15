/*先是连接,确认身份标识,返回到指定身份端*/
var stompClient = null;
function setConnected(connected){
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if(connected){
        $("#conversation").show();
    }else{
        $("#conversation").hide();
    }
    $("#userinfo").html("");
}
function connect(){
    var socket = new SockJS('/websocket-open');//连接,建立连接对象（还未发起连接）
    stompClient = Stomp.over(socket);//获取 STOMP 子协议的客户端对象
    stompClient.connect({},function(frame){//向服务器发起websocket连接并发送CONNECT帧
        setConnected(true);
        console.log('Connected: ' + frame);
        showGreeting("连接成功……");
        stompClient.subscribe('/topic/user', function(greeting){//订阅消息
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}
function disconnect(){
    if(stompClient !== null){
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}
function sendName(){
    if(stompClient == null || stompClient == undefined)return;
    stompClient.send("/app/user", {},JSON.stringify({'name': $("#name").val()}));
}
function showGreeting(message){
    $("#userinfo").append("<tr><td>" + message + "</td></tr>");
}
$(function(){
    $("form").on('submit', function(e){
        e.preventDefault();
    });
    $("#connect").click(function(){
        connect();
    });
    $("#disconnect").click(function(){
        disconnect();
    });
    $("#send").click(function(){
        sendName();
    });
});