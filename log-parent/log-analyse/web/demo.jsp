<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>测试页面1</title>
    <script type="text/javascript" src="./js/analytics.js"></script>
</head>
<body>
测试页面1<br/>
跳转到:
<a href="demo.jsp" target="_blank">demo</a>
<a href="demo2.jsp" target="_blank">demo2</a>
<a href="demo3.jsp" target="_blank">demo3</a>
<a href="demo4.jsp" target="_blank">demo4</a>


<a href="javascript:void(0)">点击时间</a>

<script>
    (function (para) {
        var p = para.s_url, n = 'mob', w = window, d = document, s = 'script', x = null, y = null;
        if (typeof (w['mobModuleName']) !== 'undefined') {
            return false;
        }
        w['mobModuleName'] = n;
        w[n] = w[n] || function (a) {
            return function () {
                (w[n]._q = w[n]._q || []).push([a, arguments]);
            }
        };
        x = d.createElement(s), y = d.getElementsByTagName(s)[0];
        x.async = 1;
        x.src = p;
        w[n].para = para;
        y.parentNode.insertBefore(x, y);
    })({
        s_url: 'https://leadstap-visitor.mob.com/assets/js/tracker.js?t=' + Date.now(),
        s_id: "DimasDemo" // TODO：修改ID-不同站点不同的id
    });
</script>
</body>
</html>