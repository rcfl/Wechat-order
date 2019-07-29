<#--<nav class="navbar navbar-dark align-items-start sidebar sidebar-dark accordion bg-gradient-primary p-0">-->
    <#--<div class="container-fluid d-flex flex-column p-0">-->
        <#--<a class="navbar-brand d-flex justify-content-center align-items-center sidebar-brand m-0" href="#">-->
            <#--<div class="sidebar-brand-icon rotate-n-15"><i class="fas fa-laugh-wink"></i></div>-->
            <#--<div class="sidebar-brand-text mx-3"><span>Brand</span></div>-->
        <#--</a>-->
        <#--<hr class="sidebar-divider my-0">-->
        <#--<ul class="nav navbar-nav text-light" id="accordionSidebar">-->
            <#--<li class="sidebar-brand">-->
                <#--<a href="#">-->
                    <#--卖家管理系统-->
                <#--</a>-->
            <#--</li>-->
            <#--<li>-->
                <#--<a href="/sell/seller/order/list"><i class="fa fa-fw fa-list-alt"></i> 订单</a>-->
            <#--</li>-->
            <#--<li class="dropdown open">-->
                <#--<a href="#" class="dropdown-toggle navbar-brand" data-toggle="dropdown" aria-expanded="true">-->
                    <#--<i class="fa fa-fw fa-plus"></i> 商品 <span class="caret"></span>-->
                <#--</a>-->
                <#--<ul class="dropdown-menu" role="menu">-->
                    <#--<li class="dropdown-header">操作</li>-->
                    <#--<li><a href="/sell/seller/product/list">列表</a></li>-->
                    <#--<li><a href="/sell/seller/product/index">新增</a></li>-->
                <#--</ul>-->
            <#--</li>-->
            <#--<li class="dropdown open">-->
                <#--<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true"><i class="fa fa-fw fa-plus"></i> 类目 <span class="caret"></span></a>-->
                <#--<ul class="dropdown-menu" role="menu">-->
                    <#--<li class="dropdown-header">操作</li>-->
                    <#--<li><a href="/sell/seller/category/list">列表</a></li>-->
                    <#--<li><a href="/sell/seller/category/index">新增</a></li>-->
                <#--</ul>-->
            <#--</li>-->

            <#--<li>-->
                <#--<a href="/sell/seller/user/logout"><i class="fa fa-fw fa-list-alt"></i> 登出</a>-->
            <#--</li>-->

        <#--</ul>-->

        <ul class="nav flex-column shadow d-flex sidebar mobile-hid">
            <li class="nav-item logo-holder">
                <div class="text-center text-white logo py-4 mx-4">
                    <a class="text-white text-decoration-none" id="title" href="#"><strong>卖家管理系统</strong></a>
                    <a class="text-white float-right" id="sidebarToggleHolder" href="#"><i class="fas fa-bars" id="sidebarToggle"></i>
                    </a>
                </div>
            </li>
            <li class="nav-item">
                <a class="nav-link active text-left text-white py-1 px-0" href="/sell/seller/order/list">
                    <i class="fas fa-tachometer-alt mx-3"></i><span class="text-nowrap mx-2">订单</span>
                </a>
            </li>
            <li class="nav-item dropdown">
                <a class="dropdown-toggle nav-link text-left text-white py-1 px-0 position-relative" data-toggle="dropdown" aria-expanded="false" href="#">
                    <i class="fas fa-sliders-h mx-3"></i><span class="text-nowrap mx-2">商品</span>
                    <i class="fas fa-caret-down float-none float-lg-right fa-sm"></i>
                    <i class="fas fa-caret-down float-none float-lg-right fa-sm"></i>
                </a>
                <div class="dropdown-menu border-0 animated fadeIn" role="menu">
                    <a class="dropdown-item text-white" role="presentation" href="#"><span>操作</span></a>
                    <a class="dropdown-item text-white" role="presentation" href="/sell/seller/product/list"><span>列表</span></a>
                    <a class="dropdown-item text-white" role="presentation" href="/sell/seller/product/index"><span>新增</span></a>
                </div>
            </li>
            <li class="nav-item dropdown">
                <a class="dropdown-toggle nav-link text-left text-white py-1 px-0 position-relative" data-toggle="dropdown" aria-expanded="false" href="#">
                    <i class="fas fa-sliders-h mx-3"></i><span class="text-nowrap mx-2">类目</span>
                    <i class="fas fa-caret-down float-none float-lg-right fa-sm"></i>
                    <i class="fas fa-caret-down float-none float-lg-right fa-sm"></i>
                </a>
                <div class="dropdown-menu border-0 animated fadeIn" role="menu">
                    <a class="dropdown-item text-white" role="presentation" href="#"><span>操作</span></a>
                    <a class="dropdown-item text-white" role="presentation" href="/sell/seller/category/list"><span>列表</span></a>
                    <a class="dropdown-item text-white" role="presentation" href="/sell/seller/category/index"><span>新增</span></a>
                </div>
            </li>
            <li class="nav-item">
                <a class="nav-link text-left text-white py-1 px-0" href="/sell/seller/user/logout">
                    <i class="fas fa-archive mx-3"></i><span class="text-nowrap mx-2">登出</span>
                </a>
            </li>
        </ul>
        <#--<div class="text-center d-none d-md-inline"><button class="btn rounded-circle border-0" id="sidebarToggle" type="button"></button></div>-->
    <#--</div>-->
<#--</nav>-->