<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link href='http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <title>Statistic Page</title>
</head>
<body>
<header>
    <form action="/tomcat/controller" method="get" class="button-form">
        <input type="hidden" name="command" value="students">
        <span>&larr;</span> <input type="submit" value="Back" />
    </form>
</header>
<h1>Student Statistic</h1>

<h2>First Subgroup</h2>
<ul>
    <c:forEach var="student" items="${firstGroup}">
        <li>${student.name} - ${student.getMark()}</li>
    </c:forEach>
</ul>

<h2>Second Subgroup</h2>
<ul>
    <c:forEach var="student" items="${secondGroup}">
        <li>${student.name} - ${student.getMark()}</li>
    </c:forEach>
</ul>

</body>
</html>
