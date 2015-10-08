<%-- 
    Document   : listAuthors
    Created on : Sep 21, 2015, 9:36:05 PM
    Author     : Matthew
    Purpose    : display list of author records and (in the future) provide
                 a way to add/edit/delete records
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Author List</title>
    </head>
    <body>
        <h1>Author List</h1>
        <form method="post" action="AuthorController" id="deleteRecords">
            <input type="submit" value="Delete Record By ID" id="deleteAuthor"/>
            <input type="number" min="0" value="0" id="delete" name="delete"/>
            <input name="action" type="hidden" value="delete"/>
        </form>
        <br>
        <form id="addRecords">
            <input type="submit" value="Add Record" id="add"/>
            <input type="text" placeholder="Author Name" id="addAuthorName" name="addAuthorName"/>
            <input type="date" id="addDate" name="addDate"/>
            <input name="action" type="hidden" value="add"
        </form>
        <br><br>
        <form id="updateRecords">
            <input type="submit" value="Update Record" id="update"/>
            <input type="number" min="0" value="0" name="updateIdSelector"/>
            <input type="text" name="updateAuthorName"/>
            <input type="date" name="updateAuthorDate"/>
        </form>
        <br>
        <table width="500" border="1" cellspacing="0" cellpadding="4">
            <tr style="background-color: black;color:white;">
                <th align="left" class="tableHead">ID</th>
                <th align="left" class="tableHead">Author Name</th>
                <th align="right" class="tableHead">Date Added</th>
            </tr>
        <c:forEach var="a" items="${authors}" varStatus="rowCount">
            <c:choose>
                <c:when test="${rowCount.count % 2 == 0}">
                    <tr style="background-color: white;">
                </c:when>
                <c:otherwise>
                    <tr style="background-color: #ccffff;">
                </c:otherwise>
            </c:choose>
            <td align="left">${a.authorId}</td>
            <td align="left">${a.authorName}</td>
            <td align="right">
                <fmt:formatDate pattern="M/d/yyyy" value="${a.dateAdded}"></fmt:formatDate>
            </td>
        </tr>
        </c:forEach>
        </table>
        <c:if test="${errMsg != null}">
            <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                ${errMsg}</p>
        </c:if>
    </body>
</html>
