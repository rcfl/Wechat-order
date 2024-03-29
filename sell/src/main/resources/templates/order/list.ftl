<html>
<#include "../common/head.ftl">

<body id="page-top">
<div id="wrapper">
    <#include "../common/nav.ftl">
    <div class="d-flex flex-column" id="content-wrapper">
        <div id="content">
        <#include "../common/header.ftl">
            <div class="container-fluid">
                <div class="card shadow">
                    <div class="card-body">

                        <div class="table-responsive table mt-2" id="dataTable" role="grid" aria-describedby="dataTable_info">
                            <table class="table dataTable my-0" id="dataTable">
                                <thead>
                                <tr class="bg-primary">
                                    <th>订单id</th>
                                    <th>姓名</th>
                                    <th>手机号</th>
                                    <th>地址</th>
                                    <th>金额</th>
                                    <th>订单状态</th>
                                    <th>支付状态</th>
                                    <th>创建时间</th>
                                    <th colspan="2">操作</th>
                                </tr>
                                </thead>
                                <tbody>

                                <#list orderDTOPage.content as orderDTO>
                                <tr>
                                    <td>${orderDTO.orderId}</td>
                                    <td>${orderDTO.buyerName}</td>
                                    <td>${orderDTO.buyerPhone}</td>
                                    <td>${orderDTO.buyerAddress}</td>
                                    <td>${orderDTO.orderAmount}</td>
                                    <td>${orderDTO.getOrderStatusEnum().message}</td>
                                    <td>${orderDTO.getPayStatusEnum().message}</td>
                                    <td>${orderDTO.createTime}</td>
                                    <td colspan="2">

                                        <a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}"><button class="btn btn-primary">详情</button></a>
                                        <#if orderDTO.getOrderStatusEnum().message == "新订单">
                                        <#--<a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>-->
                                            <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}"><button class="btn btn-warning">取消</button></a>
                                        <#else >
                                            <a href="#"><button class="btn btn-warning disabled">取消</button></a>
                                        </#if>


                                    </td>
                                    <#--<td>-->
                                        <#--<#if orderDTO.getOrderStatusEnum().message == "新订单">-->
                                            <#--&lt;#&ndash;<a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>&ndash;&gt;-->
                                            <#--<button class="btn btn-warning">取消</button>-->
                                        <#--</#if>-->
                                    <#--</td>-->
                                </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>

            <#--分页-->
                        <div class="row">

                            <div class="col-md-6">
                                <nav class="d-lg-flex justify-content-lg-end dataTables_paginate paging_simple_numbers">

                                    <ul class="pagination pull-right">
                                        <#if currentPage lte 1>
                                            <li class="disabled"><a class="page-link" href="#">上一页</a></li>
                                        <#else>
                                            <li><a class="page-link" href="/sell/seller/order/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                                        </#if>

                                        <#list 1..orderDTOPage.getTotalPages() as index>
                                            <#if currentPage == index>
                                                <li class="disabled"><a class="page-link" href="#">${index}</a></li>
                                            <#else>
                                                <li><a class="page-link" href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                                            </#if>
                                        </#list>

                                        <#if currentPage gte orderDTOPage.getTotalPages()>
                                            <li class="disabled"><a class="page-link" href="#">下一页</a></li>
                                        <#else>
                                            <li><a class="page-link" href="/sell/seller/order/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                                        </#if>
                                    </ul>
                                </nav>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <#--弹窗-->
    <div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="myModalLabel">
                        提醒
                    </h4>
                </div>
                <div class="modal-body">
                    你有新的订单
                </div>
                <div class="modal-footer">
                    <button onclick="javascript:document.getElementById('notice').pause()" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button onclick="location.reload()" type="button" class="btn btn-primary">查看新的订单</button>
                </div>
            </div>
        </div>
    </div>

    <#--播放音乐-->
    <audio id="notice" loop="loop">
        <source src="/sell/mp3/song.mp3" type="audio/mpeg" />
    </audio>
</div>

<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script>
    var websocket = null;
    if('WebSocket' in window) {
        websocket = new WebSocket('ws://sellayucn.free.idcfengye.com/sell/webSocket');
    }else {
        alert('该浏览器不支持websocket!');
    }

    websocket.onopen = function (event) {
        console.log('建立连接');
    }

    websocket.onclose = function (event) {
        console.log('连接关闭');
    }

    websocket.onmessage = function (event) {
        console.log('收到消息:' + event.data)
        //弹窗提醒, 播放音乐
        $('#myModal').modal('show');

        document.getElementById('notice').play();
    }

    websocket.onerror = function () {
        alert('websocket通信发生错误！');
    }

    window.onbeforeunload = function () {
        websocket.close();
    }

</script>
<#include "../common/footer.ftl">
<#--</div><a class="border rounded d-inline scroll-to-top" href="#page-top"><i class="fas fa-angle-up"></i></a></div>-->
<script src="/sell/assets/js/jquery.min.js"></script>
<script src="/sell/assets/bootstrap/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.js"></script>
<script src="/sell/assets/js/theme.js"></script>
</body>
</html>