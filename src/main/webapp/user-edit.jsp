<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" type="image/png" sizes="16x16" href="plugins/images/favicon.png">
    <title>Pixel Admin - Edit User</title>
    <!-- Bootstrap Core CSS -->
    <link href="bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Menu CSS -->
    <link href="plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.css" rel="stylesheet">
    <!-- animation CSS -->
    <link href="css/animate.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="css/style.css" rel="stylesheet">
    <!-- color CSS -->
    <link href="css/colors/blue-dark.css" id="theme" rel="stylesheet">
    <link rel="stylesheet" href="./css/custom.css">
</head>

<body>
    <!-- Preloader -->
    <div class="preloader">
        <div class="cssload-speeding-wheel"></div>
    </div>
    <div id="wrapper">
        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top m-b-0">
            <div class="navbar-header">
                <a class="navbar-toggle hidden-sm hidden-md hidden-lg " href="javascript:void(0)" data-toggle="collapse" data-target=".navbar-collapse">
                    <i class="fa fa-bars"></i>
                </a>
                <div class="top-left-part">
                    <a class="logo" href="index.html">
                        <b><img src="plugins/images/pixeladmin-logo.png" alt="home" /></b>
                        <span class="hidden-xs"><img src="plugins/images/pixeladmin-text.png" alt="home" /></span>
                    </a>
                </div>
                <ul class="nav navbar-top-links navbar-left m-l-20 hidden-xs">
                    <li>
                        <form role="search" class="app-search hidden-xs">
                            <input type="text" placeholder="Search..." class="form-control">
                            <a href=""><i class="fa fa-search"></i></a>
                        </form>
                    </li>
                </ul>
                <ul class="nav navbar-top-links navbar-right pull-right">
                    <li>
                        <div class="dropdown">
                            <a class="profile-pic dropdown-toggle" data-toggle="dropdown" href="#">
                                <img src="plugins/images/users/varun.jpg" alt="user-img" width="36" class="img-circle" />
                                <b class="hidden-xs">Cybersoft</b>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="profile.html">Thông tin cá nhân</a></li>
                                <li><a href="#">Thống kê công việc</a></li>
                                <li class="divider"></li>
                                <li><a href="${ctx}/logout">Đăng xuất</a></li>
                            </ul>
                        </div>
                    </li>
                </ul>
            </div>
        </nav>

        <!-- Left navbar-header -->
        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse slimscrollsidebar">
                <ul class="nav" id="side-menu">
                    <li style="padding: 10px 0 0;">
                        <a href="${ctx}/dashboard.jsp" class="waves-effect"><i class="fa fa-clock-o fa-fw" aria-hidden="true"></i><span class="hide-menu">Dashboard</span></a>
                    </li>
                    <li>
                        <a href="${ctx}/user" class="waves-effect"><i class="fa fa-user fa-fw" aria-hidden="true"></i><span class="hide-menu">Thành viên</span></a>
                    </li>
                    <li>
                        <a href="${ctx}/roles" class="waves-effect"><i class="fa fa-modx fa-fw" aria-hidden="true"></i><span class="hide-menu">Quyền</span></a>
                    </li>
                    <li>
                        <a href="groupwork.html" class="waves-effect"><i class="fa fa-table fa-fw" aria-hidden="true"></i><span class="hide-menu">Dự án</span></a>
                    </li>
                    <li>
                        <a href="task.html" class="waves-effect"><i class="fa fa-table fa-fw" aria-hidden="true"></i><span class="hide-menu">Công việc</span></a>
                    </li>
                    <li>
                        <a href="blank.html" class="waves-effect"><i class="fa fa-columns fa-fw" aria-hidden="true"></i><span class="hide-menu">Blank Page</span></a>
                    </li>
                    <li>
                        <a href="404.html" class="waves-effect"><i class="fa fa-info-circle fa-fw" aria-hidden="true"></i><span class="hide-menu">Error 404</span></a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- Left navbar-header end -->

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row bg-title">
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                        <h4 class="page-title">Sửa thành viên</h4>
                    </div>
                    <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12 text-right">
                        <a href="${ctx}/user" class="btn btn-sm btn-default">← Quay lại danh sách</a>
                    </div>
                </div>

                <!-- Flash message -->
                <c:if test="${not empty param.msg}">
                    <div class="alert
                        <c:choose>
                            <c:when test='${param.msg eq "updated"}'>alert-success</c:when>
                            <c:otherwise>alert-danger</c:otherwise>
                        </c:choose>
                    " role="alert" style="margin-top:10px;">
                        <c:choose>
                            <c:when test='${param.msg eq "updated"}'>Cập nhật user thành công.</c:when>
                            <c:when test='${param.msg eq "failed"}'>Cập nhật thất bại. Vui lòng kiểm tra lại.</c:when>
                            <c:when test='${param.msg eq "not_found"}'>Không tìm thấy user.</c:when>
                            <c:otherwise>Có lỗi xảy ra.</c:otherwise>
                        </c:choose>
                    </div>
                </c:if>

                <div class="row">
                    <div class="col-sm-8">
                        <div class="white-box">
                            <h3 class="box-title">Thông tin User</h3>

                            <form method="post" action="${ctx}/user-edit" autocomplete="off">
                                <!-- giữ lại id để POST -->
                                <input type="hidden" name="id" value="${user.id}"/>

                                <div class="form-group">
                                    <label for="name">Name</label>
                                    <input type="text" class="form-control" id="fullname" name="fullname"
                                           value="${user.name}" required />
                                </div>

                                <div class="form-group">
                                    <label for="email">Email</label>
                                    <input type="email" class="form-control" id="email" name="email"
                                           value="${user.email}" required />
                                </div>

                                <div class="form-group">
                                    <label for="country">Country</label>
                                    <input type="text" class="form-control" id="country" name="country"
                                           value="${user.country}" />
                                </div>


                                <div class="text-right">
                                    <a href="${ctx}/user" class="btn btn-default">Hủy</a>
                                    <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                                </div>
                            </form>

                        </div>
                    </div>

                    <div class="col-sm-4">
                        <div class="white-box">
                            <h3 class="box-title">Thông tin hệ thống</h3>
                            <p><b>User ID:</b> ${user.id}</p>
                            <p><b>Email:</b> ${user.email}</p>
                            <p><b>Name:</b> ${user.name}</p>
                            <p><b>Role hiện tại:</b> ${user.roleDescription}</p>
                        </div>
                    </div>
                </div>

            </div>
            <footer class="footer text-center">2018 &copy; myclass.com</footer>
        </div>
    </div>

    <!-- jQuery -->
    <script src="plugins/bower_components/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- Menu Plugin JavaScript -->
    <script src="plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.js"></script>
    <!--slimscroll JavaScript -->
    <script src="js/jquery.slimscroll.js"></script>
    <!--Wave Effects -->
    <script src="js/waves.js"></script>
    <!-- Custom Theme JavaScript -->
    <script src="js/custom.min.js"></script>
</body>
</html>
