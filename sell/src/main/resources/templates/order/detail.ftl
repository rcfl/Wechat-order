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
                        <tr>
                            <th>订单id</th>
                            <th>订单总金额</th>
                        </tr>
                        </thead>
                                <tbody>
                        <tr>
                            <td>${orderDTO.orderId}</td>
                            <td>${orderDTO.orderAmount}</td>
                        </tr>
                        </tbody>
                            </table>
                        </div>

                        <#--订单详情表数据-->
                        <div class="table-responsive table mt-2" id="dataTable" role="grid" aria-describedby="dataTable_info">
                            <table class="table dataTable my-0" id="dataTable">
                                <thead>
                                <tr>
                                    <th>商品id</th>
                                    <th>商品名称</th>
                                    <th>价格</th>
                                    <th>数量</th>
                                    <th>总额</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list orderDTO.orderDetailList as orderDetail>
                                <tr>
                                    <td>${orderDetail.productId}</td>
                                    <td>${orderDetail.productName}</td>
                                    <td>${orderDetail.productPrice}</td>
                                    <td>${orderDetail.productQuantity}</td>
                                    <td>${orderDetail.productQuantity * orderDetail.productPrice}</td>
                                </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>

                        <#--操作-->
                        <div class="col-md-12 column">
                <#if orderDTO.getOrderStatusEnum().message == "新订单">
                    <a href="/sell/seller/order/finish?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-primary">完结订单</a>
                    <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-danger">取消订单</a>
                </#if>
                </div>
                    </div>
                </div>
         </div>
        </div>
    </div>

    <#include "../common/footer.ftl">
<#--</div><a class="border rounded d-inline scroll-to-top" href="#page-top"><i class="fas fa-angle-up"></i></a></div>-->
    <script src="/sell/assets/js/jquery.min.js"></script>
    <script src="/sell/assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.js"></script>
    <script src="/sell/assets/js/theme.js"></script>
</div>

</body>
</html>