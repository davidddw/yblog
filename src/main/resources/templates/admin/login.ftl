<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>后台管理页面</title>
    <link rel="shortcut icon" href="${basePath}/static/favicon.ico"/>

    <!-- DataTables CSS end-->
    <link rel="stylesheet" href="${basePath}/static/admin/css/user-manage.css">
    <!-- Custom Theme Style -->
    <#--<link href="${basePath}/static/admin/css/custom.css" rel="stylesheet">-->
</head>
<body class="login" mycollectionplug="bind">
<div class="login_m">
    <div class="login_logo"><img src="${basePath}/static/gr/images/logo.png" width="231" height="69"></div>
    <div class="login_boder">
        <div class="login_padding" id="login_model">
            <form id="loginForm" action="login"  method="post">
                <div class="inputbox">
                    <h2>用户名</h2>
                    <label>
                        <input type="text" name="username" id="username" class="txt_input txt_input2" placeholder="用户名" required>
                    </label>
                </div>
                <div class="inputbox">
                    <h2>密码</h2>
                    <label>
                        <input type="password" name="password" id="password" class="txt_input" placeholder="输入密码" required>
                    </label>
                </div>
                <p class="forgot"></p>
                <div class="rem_sub">
                    <div class="rem_sub_l">
                        <input type="checkbox" name="checkbox" id="save_me">
                        <label for="checkbox">记住密码</label>
                    </div>
                    <label>
                        <input type="submit" class="sub_button" name="button" id="button" value="登录" style="opacity: 0.7;">
                    </label>
                </div>
            </form>
        </div>
        <!--login_padding  Sign up end-->
    </div><!--login_boder end-->
</div><!--login_m end-->
<!-- JQuery -->
<script src="${basePath}/static/vender/jquery/1.12.4/jquery.min.js"></script>
<script src="${basePath}/static/vender/parsley.js/2.7.0/parsley.min.js"></script>
<script src="${basePath}/static/admin/js/login.js"></script>
</body>
</html>