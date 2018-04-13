<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>shiro-web</title>
</head>
<body>
<shiro:guest>
    欢迎游客访问，<a href="${pageContext.request.contextPath}/chapter7/login">点击登录</a><br/>
</shiro:guest>
<shiro:user>
    欢迎[<shiro:principal/>]登录，<a href="${pageContext.request.contextPath}/chapter7/logout">点击退出</a><br/>
</shiro:user>

<shiro:authenticated>
    用户[<shiro:principal/>]已身份验证通过<br/>
</shiro:authenticated>

<shiro:notAuthenticated>
    未身份验证（包括记住我）<br/>
</shiro:notAuthenticated>

<shiro:hasRole name="admin">
    用户[<shiro:principal/>]拥有角色admin<br/>
</shiro:hasRole>

<shiro:hasAnyRoles name="admin,user">
    用户[<shiro:principal/>]拥有角色admin或user<br/>
</shiro:hasAnyRoles>

<shiro:lacksRole name="abc">
    用户[<shiro:principal/>]没有角色abc<br/>
</shiro:lacksRole>

<shiro:hasPermission name="user:create">
    用户[<shiro:principal/>]拥有权限user:create<br/>
</shiro:hasPermission>
<shiro:lacksPermission name="org:create">
    用户[<shiro:principal/>]没有权限org:create<br/>
</shiro:lacksPermission>

<%--自己实现tag--%>
<%--<zhang:hasAllRoles name="admin,user">--%>
    <%--用户[<shiro:principal/>]拥有角色admin和user<br/>--%>
<%--</zhang:hasAllRoles>--%>

<%--<zhang:hasAllPermissions name="user:create,user:update">--%>
    <%--用户[<shiro:principal/>]拥有权限user:create和user:update<br/>--%>
<%--</zhang:hasAllPermissions>--%>

<%--<zhang:hasAnyPermissions name="user:create,abc:update">--%>
    <%--用户[<shiro:principal/>]拥有权限user:create或abc:update<br/>--%>
<%--</zhang:hasAnyPermissions>--%>
<div>
    <a href="login">登陆</a>
</div>
<div>
    <a href="isAuthenticated">登陆成功</a>
</div>
<div>
    <a href="logout">登出</a>
</div>
<div>
    <a href="hasRole">拥有角色</a>
</div>
<div>
    <a href="isPermitted">拥有权限</a>
</div>

</body>
</html>