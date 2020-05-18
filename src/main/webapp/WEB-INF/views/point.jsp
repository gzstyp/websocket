<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    final String path = request.getContextPath();
    final String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8"/>
    <base href="<%=basePath%>">
    <title>货位号管理</title>
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="/webjars/sockjs-client/1.1.2/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/2.3.3/stomp.min.js"></script>
</head>
<body>
<div id="main-content" class="container">
    <div class="row"  style="margin-top:22px;">
        <div class="col-md-1">
            <label for="item_storage_code" style="margin-top:6px;">货位号</label>
        </div>
        <div class="col-md-11">
            <input type="text" id="item_storage_code" class="form-control" placeholder="输入货位号">
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <label>坐标</label>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">
            <input type="text" class="form-control" id="x1" placeholder="位置x1的值">
        </div>
        <div class="col-md-3">
            <input type="text" class="form-control" id="y1" placeholder="位置y1的值">
        </div>
        <div class="col-md-3">
            <input type="text" class="form-control" id="x2" placeholder="位置x2的值">
        </div>
        <div class="col-md-3">
            <input type="text" class="form-control" id="yx" placeholder="位置y2的值">
        </div>
    </div>
    <div class="row" style="margin-top:10px;">
        <div class="col-md-3">
            <input type="text" class="form-control" id="x3" placeholder="位置x3的值">
        </div>
        <div class="col-md-3">
            <input type="text" class="form-control" id="y3" placeholder="位置y3的值">
        </div>
        <div class="col-md-3">
            <input type="text" class="form-control" id="x4" placeholder="位置x4的值">
        </div>
        <div class="col-md-3">
            <input type="text" class="form-control" id="y4" placeholder="位置y4的值">
        </div>
    </div>
    <div class="row" style="margin-top:10px;">
        <div class="col-md-12">
            <button id="send" class=" col-md-12 btn btn-primary">提交</button>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table id="conversation" class="table table-striped">
                <thead>
                <tr>
                    <th>编号</th>
                    <th>货位信息</th>
                    <th>坐标信息</th>
                    <th>添加时间</th>
                </tr>
                </thead>
                <tbody id="storagePoint">
                </tbody>
            </table>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <label id="total">共计:0</label>
        </div>
    </div>
    </form>
</div>

<script type="text/javascript">
    ;(function($){
        var thisPage = {
            init:function(){
                this.initDom();
            },
            initDom:function(){
                ajaxGet('/wms/getListData',{},function(data){
                    if(data.code === 200){
                        $.each(data.data,function(index,data){
                            var point = data.point.replace(/"/g,"")
                            $("#storagePoint").append("<tr><td>" + (index+1) + "</td><td>" + data.item_storage_code + "</td><td>" + point + "</td><td>" + data.gmt_create + "</td></tr>");
                        });
                        $('#total').html('共计:'+data.data.length+'条');
                    }
                },function(error){
                    $('#total').html('连接服务器失败');
                });
            }
        };
        thisPage.init();
        function ajaxPost(url,params,succeed,failure){
            $.ajax({
                type : "POST",
                url : url,
                dataType : "json",
                data : params,
                success : function(result){
                    succeed(result);
                },
                error : function(response,err){
                    if (failure != null && failure != ''){
                        failure(err);
                    }
                }
            });
        }
        function ajaxGet(url,params,succeed,failure){
            $.ajax({
                type : "GET",
                url : url,
                dataType : "json",
                data : params,
                success : function(result){
                    succeed(result);
                },
                error : function(response,err){
                    if (failure != null && failure != ''){
                        failure(err);
                    }
                }
            });
        }
    })(jQuery);
</script>
</body>
</html>