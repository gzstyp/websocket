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
    <div id="main-content" class="container">
        <div class="row">
            <a href="/message" target="_blank" class="btn btn-default">ricky用户</a>
            <a href="/messaget2" target="_blank" class="btn btn-primary">rickyt2用户</a>
            <a href="/msg/sendcommuser" target="_blank" class="btn btn-success">发送公共消息</a>
        </div>
    </div>
</body>
</html>