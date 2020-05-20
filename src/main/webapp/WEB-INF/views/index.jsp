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
</head>
<body>
    <div id="main-content" class="container" style="margin-top:20px;">
        <div class="row">
            <div class="col-md-6">
                <a href="/message" target="_blank" class="btn btn-default">ricky用户</a>
                <a href="/messaget2" target="_blank" class="btn btn-primary">rickyt2用户</a>
                <a href="/msg/sendcommuser" target="_blank" class="btn btn-success">全网通知</a>
            </div>
        </div>
    </div>
</body>
</html>