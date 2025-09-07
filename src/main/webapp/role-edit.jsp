<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Sửa quyền</title>
    <link href="${ctx}/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container" style="margin-top:40px;">

    <h3>Sửa quyền</h3>

    <form method="post" action="${ctx}/roles-edit">
        <!-- giữ lại id để POST -->
        <input type="hidden" name="id" value="${role.id}" />

        <div class="form-group">
            <label for="roleName">Tên quyền</label>
            <input type="text" class="form-control" id="roleName" name="roleName"
                   value="${role.name}" required>
        </div>

        <div class="form-group">
            <label for="desc">Mô tả</label>
            <textarea class="form-control" id="desc" name="desc" rows="3">${role.description}</textarea>
        </div>

        <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
        <a href="${ctx}/roles" class="btn btn-default">Hủy</a>
    </form>

</body>
</html>
