<html>
<#include "../common/head.ftl">


<body id="page-top">
<div id="wrapper">
    <#include "../common/nav.ftl">
    <div class="d-flex flex-column" id="content-wrapper">
        <div id="content">
            <#include "../common/header.ftl">
            <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/sell/seller/category/save">
                        <div class="form-group">
                            <label>名字</label>
                            <input name="categoryName" type="text" class="form-control" value="${(productCategory.categoryName)!''}" />
                        </div>
                        <div class="form-group">
                            <label>type</label>
                            <input name="categoryType" type="text" class="form-control" value="${(productCategory.categoryType)!''}" />
                        </div>
                        <input hidden type="text" name="categoryId" value="${(productCategory.categoryId)!''}">
                         <button type="submit" class="btn btn-primary">提交</button>
                    </form>
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