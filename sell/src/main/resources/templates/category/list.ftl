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
                                        <th>类目id</th>
                                        <th>名字</th>
                                        <th>type</th>
                                        <th>创建时间</th>
                                        <th>修改时间</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <#list categoryList as category>
                                <tr>
                                    <td>${category.categoryId}</td>
                                    <td>${category.categoryName}</td>
                                    <td>${category.categoryType}</td>
                                    <td>${category.createTime}</td>
                                    <td>${category.updateTime}</td>
                                    <td><a href="/sell/seller/category/index?categoryId=${category.categoryId}">修改</a></td>
                                </tr>
                                </#list>
                                </tbody>
                            </table>
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


<#--
<#list orderDtoPage.content as orderDto>
    ${orderDto.orderId}<br>
</#list>-->
