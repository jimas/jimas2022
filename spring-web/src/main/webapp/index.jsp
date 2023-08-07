<html>
<body>
<h2>Hello World!</h2>

<script>
    var xhr = new XMLHttpRequest();
    xhr.open('get', 'http://localhost:8080/short')
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            console.log(xhr.responseText)
        }
    }

    xhr.send()
</script>
</body>
</html>
