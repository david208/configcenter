<html>
<body>

<form action="http://localhost:8080/esb/account/customer/attachment/save" method="post" enctype="multipart/form-data">
    <input type="text" name="type" />
    <input type="file" name="file" />
    <input type="file" name="file" />
    <input type="submit" value="go" />
</form>

<form action="http://localhost:8080/esb/account/customer/attachment/audit" method="post">
    <input type="text" name="atts[0].id" />
    <input type="text" name="atts[0].costomerId" />
    <input type="text" name="atts[1].id" />
    <input type="text" name="atts[1].costomerId" />
    <input type="submit" value="go" />
</form>
</body>
</html>
