<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    final String path = request.getContextPath();
    final String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <base href="<%=basePath%>">
    <title>WebSocket应用</title>
    <link href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="/webjars/sockjs-client/1.1.2/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/2.3.3/stomp.min.js"></script>
</head>
<body>
<noscript><h4 style="color: #ff0000">你的浏览器不支持Websocket或请启用浏览器Javascript功能后请重新加载页面</h4></noscript>
    <div id="main-content" class="container" style="margin-top:20px;">
        <div class="row">
            <div class="col-md-6">
                <form class="form-inline">
                    <div class="form-group">
                        <label for="connect">操作WebSocket</label>
                        <button id="connect" class="btn btn-default" type="submit">连接服务</button>
                        <button id="disconnect" class="btn btn-default" type="submit" disabled="disabled">断开连接</button>
                    </div>
                </form>
            </div>
            <div class="col-md-6">
                <form class="form-inline">
                    <div class="form-group">
                        <label for="name">发送一个消息试试?</label>
                        <input type="text" id="name" class="form-control" placeholder="输入发送内容">
                    </div>
                    <button id="send" class="btn btn-success" type="submit">发送</button>
                </form>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <table id="conversation" class="table table-striped">
                    <thead>
                    <tr>
                        <th>消息信息</th>
                    </tr>
                    </thead>
                    <tbody id="greetings">
                    </tbody>
                </table>
            </div>
        </div>
        </form>
    </div>

    <script>
        //  /msg/sendcommuser
        var stompClient = null;
        //传递用户key值
        var keyId = "ricky";
        function setConnected(connected) {
            $("#connect").prop("disabled", connected);
            $("#disconnect").prop("disabled", !connected);
            if (connected) {
                $("#conversation").show();
            }
            else {
                $("#conversation").hide();
            }
            $("#greetings").html("");
        }

        function connect() {
            var socket = new SockJS('/ricky-websocket');//
            stompClient = Stomp.over(socket);
            stompClient.connect({login:keyId}, function (frame) {//可用的有: connect、send、subscribe、unsubscribe、begin、commit、abort、ack、nack、disconnect
                setConnected(true);
                console.log('已连接: ' + frame);
                showGreeting("连接成功……");
                stompClient.subscribe('/ricky/topic/greetings', function (greeting){// subscribe是订阅消息
                    showGreeting(JSON.parse(greeting.body).content);
                });
            });
        }

        function disconnect() {
            if (stompClient != null) {
                stompClient.disconnect();
            }
            setConnected(false);
        }

        function sendName() {
            if(stompClient == null || stompClient == undefined)return;//提示先连接服务器
            stompClient.send("/app/msg/hellosingle", {}, JSON.stringify({'name': $("#name").val(),'id':'rickyt2',selfId : "ricky"}));// send是发送消息
        }

        function showGreeting(message) {
            $("#greetings").append("<tr><td>" + message + "</td></tr>");
        }

        $(function () {
            $("form").on('submit', function (e) {
                e.preventDefault();
            });
            $( "#connect" ).click(function() { connect(); });
            $( "#disconnect" ).click(function() { disconnect(); });
            $( "#send" ).click(function() { sendName(); });
            connect();
        });
    </script>
</body>
</html>