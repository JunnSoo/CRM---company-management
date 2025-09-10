<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Danh sách quyền</title>

    <c:set var="ctx" value="${pageContext.request.contextPath}" />

    <!-- Styles -->
    <link rel="icon" type="image/png" sizes="16x16" href="${ctx}/plugins/images/favicon.png">
    <link href="${ctx}/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
    <link href="${ctx}/css/animate.css" rel="stylesheet">
    <link href="${ctx}/css/style.css" rel="stylesheet">
    <link href="${ctx}/css/colors/blue-dark.css" id="theme" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/css/custom.css">
</head>

<body>
<div class="preloader"><div class="cssload-speeding-wheel"></div></div>

<div id="wrapper">
    <!-- Top Nav -->
    <nav class="navbar navbar-default navbar-static-top m-b-0">
        <div class="navbar-header">
            <a class="navbar-toggle hidden-sm hidden-md hidden-lg" href="javascript:void(0)" data-toggle="collapse" data-target=".navbar-collapse">
                <i class="fa fa-bars"></i>
            </a>
            <div class="top-left-part">
                <a class="logo" href="${ctx}/index.html">
                    <b><img src="${ctx}/plugins/images/pixeladmin-logo.png" alt="home"/></b>
                    <span class="hidden-xs"><img src="${ctx}/plugins/images/pixeladmin-text.png" alt="home"/></span>
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
                            <img src="${ctx}/plugins/images/users/varun.jpg" alt="user-img" width="36" class="img-circle"/>
                            <b class="hidden-xs">Cybersoft</b>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="${ctx}/profile.html">Thông tin cá nhân</a></li>
                            <li><a href="#">Thống kê công việc</a></li>
                            <li class="divider"></li>
                            <li><a href="">Đăng xuất</a></li>
                        </ul>
                    </div>
                </li>
            </ul>
        </div>
    </nav>

    <!-- Left Sidebar -->
    <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse slimscrollsidebar">
            <ul class="nav" id="side-menu">
                <li style="padding:10px 0 0;">
                    <a href="${ctx}/dashboard.jsp" class="waves-effect">
                        <i class="fa fa-clock-o fa-fw" aria-hidden="true"></i><span class="hide-menu">Dashboard</span>
                    </a>
                </li>
                <li>
                    <a href="${ctx}/user" class="waves-effect">
                        <i class="fa fa-user fa-fw" aria-hidden="true"></i><span class="hide-menu">Thành viên</span>
                    </a>
                </li>
                <li>
                    <!-- Trang hiện tại -->
                    <a href="${ctx}/roles" class="waves-effect">
                        <i class="fa fa-modx fa-fw" aria-hidden="true"></i><span class="hide-menu">Quyền</span>
                    </a>
                </li>
                <li><a href="${ctx}/groupwork.jsp" class="waves-effect"><i class="fa fa-table fa-fw"></i><span class="hide-menu">Dự án</span></a></li>
                <li><a href="${ctx}/task.jsp" class="waves-effect"><i class="fa fa-table fa-fw"></i><span class="hide-menu">Công việc</span></a></li>
                <li><a href="${ctx}/blank.html" class="waves-effect"><i class="fa fa-columns fa-fw"></i><span class="hide-menu">Blank Page</span></a></li>
                <li><a href="${ctx}/404.html" class="waves-effect"><i class="fa fa-info-circle fa-fw"></i><span class="hide-menu">Error 404</span></a></li>
            </ul>
        </div>
    </div>

    <!-- Page Content -->
    <div id="page-wrapper">
        <div class="container-fluid">
            <div class="row bg-title">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">Danh sách quyền</h4>
                </div>
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12 text-right">
                    <!-- Đúng route: /roles-add -->
                    <a href="${ctx}/roles-add" class="btn btn-sm btn-success">Thêm mới</a>
                </div>
            </div>

            <!-- Flash message -->
            <c:if test="${not empty param.msg}">
                <div class="alert">
                    <c:choose>
                        <c:when test='${param.msg eq "created" || param.msg eq "updated" || param.msg eq "deleted"}'>alert-success</c:when>
                        <c:otherwise>alert-danger</c:otherwise>
                    </c:choose>
                " role="alert" style="margin-top:10px;">
                    <c:choose>
                        <c:when test="${param.msg eq 'created'}">Tạo quyền thành công.</c:when>
                        <c:when test='${param.msg eq "updated"}'>Cập nhật quyền thành công.</c:when>
                        <c:when test='${param.msg eq "deleted"}'>Xóa quyền thành công.</c:when>
                        <c:when test='${param.msg eq "failed"}'>Thao tác thất bại. Vui lòng kiểm tra lại.</c:when>
                        <c:when test='${param.msg eq "delete_failed"}'>Xóa thất bại. Vui lòng thử lại.</c:when>
                        <c:when test='${param.msg eq "missing_id"}'>Thiếu tham số ID.</c:when>
                        <c:when test='${param.msg eq "bad_id"}'>ID không hợp lệ.</c:when>
                        <c:when test='${param.msg eq "not_found"}'>Không tìm thấy quyền.</c:when>
                        <c:otherwise>Có lỗi xảy ra.</c:otherwise>
                    </c:choose>
                </div>
            </c:if>

            <!-- Table -->
            <div class="row m-t-10">
                <div class="col-sm-12">
                    <div class="white-box">
                        <div class="table-responsive">
                            <table class="table" id="example">
                                <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Tên Quyền</th>
                                    <th>Mô Tả</th>
                                    <th>Options</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="r" items="${listRole}" varStatus="st">
                                    <tr>
                                        <td>${st.index + 1}</td>
                                        <td><c:out value="${r.name}"/></td>
                                        <td><c:out value="${r.description}"/></td>
                                        <td>
                                            <!-- Sửa: GET /role-edit?id= -->
                                            <a href="${ctx}/roles-edit?id=${r.id}" class="btn btn-sm btn-primary">Sửa</a>
                                            <!-- Xóa: GET /role-delete?id= (theo yêu cầu của bạn) -->
                                            <a href="${ctx}/roles-delete?id=${r.id}"
                                               class="btn btn-sm btn-danger"
                                               onclick="return confirm('Bạn chắc chắn muốn xóa quyền này?');">
                                                Xóa
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty listRole}">
                                    <tr>
                                        <td colspan="4" class="text-center text-muted">Chưa có quyền nào.</td>
                                    </tr>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div><!-- /row -->
        </div>

        <footer class="footer text-center">2018 &copy; myclass.com</footer>
    </div>
</div>

<!-- Scripts -->
<script src="${ctx}/plugins/bower_components/jquery/dist/jquery.min.js"></script>
<script src="${ctx}/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="${ctx}/plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.js"></script>
<script src="${ctx}/js/jquery.slimscroll.js"></script>
<script src="${ctx}/js/jquery.dataTables.js"></script>
<script src="${ctx}/js/waves.js"></script>
<script src="${ctx}/js/custom.min.js"></script>
<script>
    $(document).ready(function () {
        $('#example').DataTable();
    });
</script>
</body>
</html>
