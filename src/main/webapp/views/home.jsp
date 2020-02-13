<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Available files</h1>
        <ol>
            <c:forEach var="name" items="${names}">
                <li>${name}</li>
            </c:forEach>
        </ol>
        <sf:form method="POST" methodParam="val" >
            <label>Type something:</label>
            <input type="text" name="val" id="val"/>
            <input type="submit" value="Get the output by CL" />
        </sf:form>
</body>
</html>
