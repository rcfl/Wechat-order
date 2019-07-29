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
                                    <th>商品id</th>
                                    <th>名称</th>
                                    <th>图片</th>
                                    <th>单价</th>
                                    <th>库存</th>
                                    <th>描述</th>
                                    <th>类目</th>
                                    <th>创建时间</th>
                                    <th>修改时间</th>
                                    <th colspan="2">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list productInfoPage.content as productInfo>
                                <tr>
                                    <td>${productInfo.productId}</td>
                                    <td>${productInfo.productName}</td>
                                    <td><img height="100" width="100" src="${productInfo.productIcon}" alt=""></td>
                                    <td>${productInfo.productPrice}</td>
                                    <td>${productInfo.productStock}</td>
                                    <td>${productInfo.productDescription}</td>
                                    <td>${productInfo.categoryType}</td>
                                    <td>${productInfo.createTime}</td>
                                    <td>${productInfo.updateTime}</td>
                                    <td><a href="/sell/seller/product/index?productId=${productInfo.productId}">修改</a></td>
                                    <td>
                                    <#if productInfo.getProductStatusEnum().message == "在架">
                                        <a href="/sell/seller/product/off_sale?productId=${productInfo.productId}">下架</a>
                                    <#else>
                                        <a href="/sell/seller/product/on_sale?productId=${productInfo.productId}">上架</a>
                                    </#if>
                                    </td>
                                </tr>
                                </#list>
                                </tbody>

                            </table>
                        </div>
                        <div class="row">

                            <div class="col-md-6">
                                <nav class="d-lg-flex justify-content-lg-end dataTables_paginate paging_simple_numbers">

                                        <ul class="pagination pull-right">
                                            <#if currentPage lte 1>
                                                <li class="disabled"><a  class="page-link" href="#">上一页</a></li>
                                            <#else>
                                                <li><a class="page-link" href="/sell/seller/product/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                                            </#if>

                                            <#list 1..productInfoPage.getTotalPages() as index>
                                                <#if currentPage == index>
                                                    <li class="disabled"><a class="page-link" href="#">${index}</a></li>
                                                <#else>
                                                    <li><a class="page-link" href="/sell/seller/product/list?page=${index}&size=${size}">${index}</a></li>
                                                </#if>
                                            </#list>

                                            <#if currentPage gte productInfoPage.getTotalPages()>
                                                <li class="disabled"><a  class="page-link" href="#">下一页</a></li>
                                            <#else>
                                                <li><a class="page-link" href="/sell/seller/product/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                                            </#if>
                                        </ul>
                                </nav>
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
</body>

</html>


<#--
<#list orderDtoPage.content as orderDto>
    ${orderDto.orderId}<br>
</#list>-->
