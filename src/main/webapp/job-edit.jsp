<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" type="image/png" sizes="16x16" href="plugins/images/favicon.png">
    <title>Pixel Admin — Sửa dự án</title>

    <!-- CSS -->
    <link href="bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.css" rel="stylesheet">
    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    <link href="css/colors/blue-dark.css" id="theme" rel="stylesheet">
</head>
<body>
<div class="preloader"><div class="cssload-speeding-wheel"></div></div>

<div id="wrapper">
    <!-- Topbar -->
    <nav class="navbar navbar-default navbar-static-top m-b-0">
        <div class="navbar-header">
            <a class="navbar-toggle hidden-sm hidden-md hidden-lg" href="javascript:void(0)" data-toggle="collapse" data-target=".navbar-collapse">
                <i class="fa fa-bars"></i>
            </a>
            <div class="top-left-part">
                <a class="logo" href="index.html">
                    <b><img src="plugins/images/pixeladmin-logo.png" alt="home"/></b>
                    <span class="hidden-xs"><img src="plugins/images/pixeladmin-text.png" alt="home"/></span>
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
                            <img src="plugins/images/users/varun.jpg" alt="user-img" width="36" class="img-circle"/>
                            <b class="hidden-xs">Cybersoft</b>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="profile.html">Thông tin cá nhân</a></li>
                            <li><a href="${ctx}/profile">Thống kê công việc</a></li>
                            <li class="divider"></li>
                            <li><a href="${ctx}/logout">Đăng xuất</a></li>
                        </ul>
                    </div>
                </li>
            </ul>
        </div>
    </nav>

    <!-- Sidebar -->
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
                    <a href="${ctx}/job" class="waves-effect"><i class="fa fa-table fa-fw" aria-hidden="true"></i><span class="hide-menu">Dự án</span></a>
                </li>
                <li>
                    <a href="${ctx}/task" class="waves-effect"><i class="fa fa-table fa-fw" aria-hidden="true"></i><span class="hide-menu">Công việc</span></a>
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

    <!-- Page Content -->
    <div id="page-wrapper">
        <div class="container-fluid">
            <div class="row bg-title">
                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                    <h4 class="page-title">Sửa dự án</h4>
                </div>
                <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12 text-right">
                    <a href="${ctx}/job" class="btn btn-default btn-sm">Quay lại danh sách</a>
                </div>
            </div>

            <!-- Flash message -->
            <c:if test="${param.msg == 'updated'}">
                <div class="alert alert-success">Cập nhật dự án thành công!</div>
            </c:if>
            <c:if test="${param.msg == 'failed'}">
                <div class="alert alert-warning">Cập nhật dự án thất bại! Vui lòng kiểm tra dữ liệu.</div>
            </c:if>
            <c:if test="${param.msg == 'error'}">
                <div class="alert alert-danger">Có lỗi xảy ra! Vui lòng thử lại.</div>
            </c:if>

            <!-- Guard: nếu không có job (vào trang trực tiếp) -->
            <c:if test="${empty job}">
                <div class="alert alert-danger">Không tìm thấy dự án để sửa. <a href="${ctx}/job">Về danh sách</a></div>
            </c:if>

            <c:if test="${not empty job}">
                <div class="row">
                    <div class="col-md-8 col-md-offset-2">
                        <div class="white-box p-30">
                            <h3 class="box-title m-b-20">Chỉnh sửa thông tin dự án</h3>

                            <form action="${ctx}/job-edit" method="post" autocomplete="off">
                                <input type="hidden" name="id" value="${job.id}" />

                                <div class="form-group">
                                    <label for="name" class="control-label">Tên dự án</label>
                                    <input type="text" id="name" name="name" class="form-control"
                                           value="${job.name}" placeholder="Tên dự án" required>
                                </div>

                                <div class="form-group">
                                    <label for="start_date" class="control-label">Ngày bắt đầu</label>
                                    <input type="date" id="start_date" name="start_date" class="form-control"
                                           value="${job.start_date}" required>
                                </div>

                                <div class="form-group">
                                    <label for="end_date" class="control-label">Ngày kết thúc</label>
                                    <input type="date" id="end_date" name="end_date" class="form-control"
                                           value="${job.end_date}" required>
                                </div>

                                <div class="m-t-15">
                                    <button type="submit" class="btn btn-success btn-sm">Lưu thay đổi</button>
                                    <a href="${ctx}/job" class="btn btn-default btn-sm">Hủy</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>

        <footer class="footer text-center">2018 &copy; myclass.com</footer>
    </div>
</div>

<!-- JS -->
<script src="plugins/bower_components/jquery/dist/jquery.min.js"></script>
<script src="bootstrap/dist/js/bootstrap.min.js"></script>
<script src="plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.js"></script>
<script src="js/jquery.slimscroll.js"></script>
<script src="js/waves.js"></script>
<script src="js/custom.min.js"></script>

<!-- Validate end_date >= start_date -->
<script>
    (function() {
        var start = document.getElementById('start_date');
        var end = document.getElementById('end_date');
        function syncMin() {
            if (start && end) {
                end.min = start.value || '';
                if (end.value && start.value && end.value < start.value) {
                    end.value = start.value;
                }
            }
        }
        if (start && end) {
            start.addEventListener('change', syncMin);
            window.addEventListener('load', syncMin);
        }
    })();
</script>
</body>
</html>
