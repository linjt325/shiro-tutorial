<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>login</title>
</head>
<body>
<form action="login" method="post">
    <div>${error}</div>
    <br>
    <div>
    <label>用户名</label>
        <input type="text" name="username" id="username" placeholder="请输入用户名">
    </div>
    <div>
    <label>密码</label>
        <input type="password" name="password" id="password" placeholder="请输入密码">
    <label>下次自动登录：</label>
        <input type="checkbox" name="rememberMe" value="true"><br/>
    </div>
    <button type="submit">提交</button>
</form>

</body>
</html>