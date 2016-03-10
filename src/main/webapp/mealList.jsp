<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<h2>Meal list</h2>
<c:forEach items="${meals}" var="meal">
    <br>${meal.description} <span style="color: ${meal.exceed ? 'red' : 'green'}; ">${meal.calories}</span>

</c:forEach>
</body>
</html>
