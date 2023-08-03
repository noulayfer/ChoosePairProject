<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <link href='http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css' rel='stylesheet' type='text/css'>
    <title>Welcome Page</title>
</head>
<body>
<h1>Welcome to the Subgroups Page</h1>


<div style="display: flex;">
    <div class="subgroup">
        <h2>Left group</h2>
        <ul>
            <c:forEach var="member" items="${firstGroup}">
                <li>
                    <span>${member.name}</span>
                    <div class="button-container">
                    <form action="/tomcat/delete-student" method="post" class="button-delete">
                        <input type="hidden" name="name" value="${member.name}" />
                        <button type="submit">&cross;</button>
                    </form>
                    </div>
                </li>
            </c:forEach>
        </ul>

        <h4>First Student</h4>
        <p>${firstStudent.name}</p>

        <form action="/tomcat/update-score" method="post" class="button-form">
            <input type="hidden" name="name" value="${firstStudent.name}" />
            <input type="submit" value="&plus; Add point" />
        </form>
        <form action="/tomcat/steal-point" method="post" class="button-form">
            <input type="hidden" name="name" value="${firstStudent.name}" />
            <input type="submit" value="&minus; Steal point" />
        </form>
        <h3>${firstScore}</h3>
    </div>

    <div class="subgroup">
        <h2>Right group</h2>
        <ul>
            <c:forEach var="member" items="${secondGroup}">
                <li>
                <span>${member.name}</span>
                <div class="button-container">
                    <form action="/tomcat/delete-student" method="post" class="button-delete">
                        <input type="hidden" name="name" value="${member.name}" />
                        <button type="submit">&cross;</button>
                    </form>
                </div>
                </li>
            </c:forEach>
        </ul>
        <h4>Second Student</h4>
        <p>${secondStudent.name}</p>


        <form action="/tomcat/update-score" method="post" class="button-form">
            <input type="hidden" name="name" value="${secondStudent.name}" />
            <input type="submit" value="&plus; Add point" />
        </form>
        <form action="/tomcat/steal-point" method="post" class="button-form">
            <input type="hidden" name="name" value="${secondStudent.name}" />
            <input type="submit" value="&minus; Steal point" />
        </form>
        <h3>${secondScore}</h3>
    </div>
</div>

<form action="/tomcat/create-pair" method="get" class="button-form">
            <input type="submit" value="Create Pair &cularr;" />
</form>

<form action="/tomcat/average" method="get" class="button-form">
    <input type="submit" value="Average Mark &udarr;" />
</form>

<h3>${markOne} ${markTwo}</h3>

<h3></h3>

<form action="/tomcat/stat" method="get" class="button-form">
    <input type="submit" value="&telrec; Full statistic" />
</form>

</body>
</html>
