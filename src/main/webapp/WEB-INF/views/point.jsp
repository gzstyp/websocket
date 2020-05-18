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
    <script src="/layer/layer.js"></script>
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
            <input type="text" class="form-control" id="x1" placeholder="坐标x1的值">
        </div>
        <div class="col-md-3">
            <input type="text" class="form-control" id="y1" placeholder="坐标y1的值">
        </div>
        <div class="col-md-3">
            <input type="text" class="form-control" id="x2" placeholder="坐标x2的值">
        </div>
        <div class="col-md-3">
            <input type="text" class="form-control" id="y2" placeholder="坐标y2的值">
        </div>
    </div>
    <div class="row" style="margin-top:10px;">
        <div class="col-md-3">
            <input type="text" class="form-control" id="x3" placeholder="坐标x3的值">
        </div>
        <div class="col-md-3">
            <input type="text" class="form-control" id="y3" placeholder="坐标y3的值">
        </div>
        <div class="col-md-3">
            <input type="text" class="form-control" id="x4" placeholder="坐标x4的值">
        </div>
        <div class="col-md-3">
            <input type="text" class="form-control" id="y4" placeholder="坐标y4的值">
        </div>
    </div>
    <div class="row" style="margin-top:10px;">
        <div class="col-md-12">
            <button id="btnAdd" class=" col-md-12 btn btn-primary">添加</button>
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
                $("#btnAdd").on('click',function(){
                    thisPage.addSubmit();
                });
            },
            initDom:function(){
                thisPage.getListData();
            },
            getListData : function(){
                ajaxGet('/wms/getListData',{},function(data){
                    if(data.code === 200){
                        thisPage.initHtml(data.data);
                    }
                },function(error){
                    $('#total').html('连接服务器失败');
                });
            },
            initHtml : function(list){
                var html = '';
                $("#storagePoint").html(html);
                $.each(list,function(index,data){
                    var point = data.point.replace(/"/g,"");
                    html += "<tr><td>" + (index+1) + "</td><td>" + data.item_storage_code + "</td><td>" + point + "</td><td>" + data.gmt_create + "</td></tr>";
                });
                $("#storagePoint").html(html);
                $('#total').html('共计:'+list.length+'条');
            },
            addSubmit : function(){
                var storage_code = $('#item_storage_code').val();
                var x1 = $('#x1').val();
                var y1 = $('#y1').val();
                var x2 = $('#x2').val();
                var y2 = $('#y2').val();
                var x3 = $('#x3').val();
                var y3 = $('#y3').val();
                var x4 = $('#x4').val();
                var y4 = $('#y4').val();
                if(storage_code == null || storage_code.length == 0){
                    layer.alert('请输入货位号');
                    return;
                }
                if(x1 == null || x1.length == 0){
                    layer.alert('请输入坐标x1的值');
                    return;
                }
                if(y1 == null || y1.length == 0){
                    layer.alert('请输入坐标y1的值');
                    return;
                }
                if(x2 == null || x2.length == 0){
                    layer.alert('请输入坐标x2的值');
                    return;
                }
                if(y2 == null || y2.length == 0){
                    layer.alert('请输入坐标y2的值');
                    return;
                }
                if(x3 == null || x3.length == 0){
                    layer.alert('请输入坐标x3的值');
                    return;
                }
                if(y3 == null || y3.length == 0){
                    layer.alert('请输入坐标y3的值');
                    return;
                }
                if(x4 == null || x4.length == 0){
                    layer.alert('请输入坐标x4的值');
                    return;
                }
                if(y4 == null || y4.length == 0){
                    layer.alert('请输入坐标y4的值');
                    return;
                }
                var params = {};
                params['x1']=x1;
                params['y1']=y1;
                params['x2']=x2;
                params['y2']=y2;
                params['x3']=x3;
                params['y3']=y3;
                params['x4']=x4;
                params['y4']=y4;
                var json = JSON.stringify(params);
                var param = {
                    item_storage_code : storage_code,
                    point : json
                };
                thisPage.btnText("正在提交……",false);
                ajaxPost('/wms/addPoint',param,function(data){
                    if(data.code === 200){
                        result(data.msg);
                        thisPage.getListData();
                        thisPage.btnText("添加",true);
                    }else{
                        layer.alert(data.msg);
                    }
                },function(err){
                    thisPage.btnText("添加",true);
                });
            },
            btnText : function(text,bl){
                if(bl){
                    $("#btnAdd").removeAttr("disabled");
                }else{
                    $("#btnAdd").prop("disabled",bl);
                }
                $("#btnAdd").text(text);
            },
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
        function result(content){
            layer.open({
                title : '系统提示',
                content : content,//此处可以是任意代码
                shade : 0,
                offset : 'rb',
                anim : 2,
                btn:false,
                time:1500
            });
        }
    })(jQuery);
</script>
</body>
</html>