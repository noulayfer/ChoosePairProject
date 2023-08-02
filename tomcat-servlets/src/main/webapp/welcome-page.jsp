<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome Page</title>
</head>
<body>
<h1>Welcome to the Subgroups Page</h1>

<div style="display: flex;">
    <!-- Left Subgroup List -->
    <div style="flex: 1; border: 1px solid #ccc; padding: 10px;">
        <h2>Left Subgroup</h2>
        <ul>
            <c:forEach var="member" items="${firstGroup}">
                <li>${member.name}</li>
            </c:forEach>
        </ul>
        <h2>First Student</h2>
        <p>${firstStudent.name}</p>
        <p>${firstStudent.age}</p>
        <!-- Add other student details as needed -->

        <!-- Form to add score for the firstStudent -->
        <form action="/tomcat/update-score" method="post">
            <input type="hidden" name="studentName" value="${firstStudent.name}" />
            <input type="submit" value="Add Score" />
        </form>
    </div>

    <!-- Right Subgroup List -->
    <div style="flex: 1; border: 1px solid #ccc; padding: 10px;">
        <h2>Right Subgroup</h2>
        <ul>
            <c:forEach var="member" items="${secondGroup}">
                <li>${member.name}</li>
            </c:forEach>
        </ul>
        <h2>Second Student</h2>
        <p>${secondStudent.name}</p>
        <p>${secondStudent.age}</p>

        <!-- Add other student details as needed -->

        <!-- Form to add score for the secondStudent -->
        <form action="/tomcat/update-score" method="post">
            <input type="hidden" name="studentName" value="${secondStudent.name}" />
            <input type="submit" value="Add Score" />
        </form>
    </div>

    <a href="/tomcat/create-pair">Create Pair with GET request</a>



</div>
</body>
</html>
