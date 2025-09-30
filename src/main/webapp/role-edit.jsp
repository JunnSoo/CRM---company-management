<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" type="image/png" sizes="16x16" href="${ctx}/plugins/images/favicon.png">
    <title>Pixel Admin - Sửa quyền</title>
    <!-- Bootstrap Core CSS -->
    <link href="${ctx}/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Menu CSS -->
    <link href="${ctx}/plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.css" rel="stylesheet">
    <!-- animation CSS -->
    <link href="${ctx}/css/animate.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="${ctx}/css/style.css" rel="stylesheet">
    <!-- color CSS -->
    <link href="${ctx}/css/colors/blue-dark.css" id="theme" rel="stylesheet">
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
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
                <a class="navbar-toggle hidden-sm hidden-md hidden-lg" href="javascript:void(0)" data-toggle="collapse" data-target=".navbar-collapse">
                    <i class="fa fa-bars"></i>
                </a>
                <div class="top-left-part">
                    <a class="logo" href="${ctx}/index.jsp">
                        <b><img src="${ctx}/plugins/images/pixeladmin-logo.png" alt="home" /></b>
                        <span class="hidden-xs"><img src="${ctx}/plugins/images/pixeladmin-text.png" alt="home" /></span>
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
                                <img src="${ctx}/plugins/images/users/varun.jpg" alt="user-img" width="36" class="img-circle" />
                                <b class="hidden-xs">Cybersoft</b>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="${ctx}/profile">Thông tin cá nhân</a></li>
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
                        <a href="${ctx}/groupwork.jsp" class="waves-effect"><i class="fa fa-table fa-fw" aria-hidden="true"></i><span class="hide-menu">Dự án</span></a>
                    </li>
                    <li>
                        <a href="${ctx}/task.jsp" class="waves-effect"><i class="fa fa-table fa-fw" aria-hidden="true"></i><span class="hide-menu">Công việc</span></a>
                    </li>
                    <li>
                        <a href="${ctx}/blank.html" class="waves-effect"><i class="fa fa-columns fa-fw" aria-hidden="true"></i><span class="hide-menu">Blank Page</span></a>
                    </li>
                    <li>
                        <a href="${ctx}/404.html" class="waves-effect"><i class="fa fa-info-circle fa-fw" aria-hidden="true"></i><span class="hide-menu">Error 404</span></a>
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
                        <h4 class="page-title">Sửa quyền</h4>
                    </div>
                </div>

                <!-- Alerts -->
                <c:if test="${param.msg == 'updated'}">
                    <div class="alert alert-success">Cập nhật quyền thành công.</div>
                </c:if>
                <c:if test="${param.msg == 'failed'}">
                    <div class="alert alert-warning">Cập nhật thất bại. Vui lòng kiểm tra dữ liệu nhập.</div>
                </c:if>
                <c:if test="${param.msg == 'notfound'}">
                    <div class="alert alert-danger">Không tìm thấy quyền cần sửa.</div>
                </c:if>
                <c:if test="${param.msg == 'error'}">
                    <div class="alert alert-danger">Có lỗi hệ thống. Thử lại sau.</div>
                </c:if>

                <!-- Form -->
                <div class="row">
                    <div class="col-md-2 col-12"></div>
                    <div class="col-md-8 col-xs-12">
                        <div class="white-box">
                            <!-- role được set bởi Servlet: request.setAttribute("role", foundRole); -->
                            <form action="${ctx}/roles-edit" method="post" class="form-horizontal form-material">
                                <!-- giữ lại id để POST -->
                                <input type="hidden" name="id" value="${role.id}" />

                                <div class="form-group">
                                    <label class="col-md-12">Tên quyền</label>
                                    <div class="col-md-12">
                                        <input type="text" name="roleName" placeholder="Tên quyền"
                                               value="${role.name}"
                                               class="form-control form-control-line" />
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-12">Mô tả</label>
                                    <div class="col-md-12">
                                        <input type="text" name="desc" placeholder="Mô tả"
                                               value="${role.description}"
                                               class="form-control form-control-line" />
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-sm-12">
                                        <button type="submit" class="btn btn-success">Lưu thay đổi</button>
                                        <a href="${ctx}/roles" class="btn btn-primary">Quay lại</a>
                                    </div>
                                </div>
                            </form>

                            <!-- Trường hợp không có role (null) nhưng vẫn vào trang -->
                            <c:if test="${empty role}">
                                <div class="alert alert-danger m-t-20">Không có dữ liệu quyền để chỉnh sửa.</div>
                            </c:if>
                        </div>
                    </div>
                    <div class="col-md-2 col-12"></div>
                </div>
            </div>

            <footer class="footer text-center">2018 &copy; myclass.com</footer>
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="${ctx}/plugins/bower_components/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="${ctx}/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- Menu Plugin JavaScript -->
    <script src="${ctx}/plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.js"></script>
    <!-- slimscroll JavaScript -->
    <script src="${ctx}/js/jquery.slimscroll.js"></script>
    <!-- Wave Effects -->
    <script src="${ctx}/js/waves.js"></script>
    <!-- Custom Theme JavaScript -->
    <script src="${ctx}/js/custom.min.js"></script>
</body>
</html>
