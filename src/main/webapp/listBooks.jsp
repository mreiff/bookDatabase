<%-- 
    Document   : listAuthors
    Created on : Sep 21, 2015, 9:36:05 PM
    Author     : Matthew
    Purpose    : display list of book records and (in the future) provide
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
        <title>Book List</title>
    </head>
    <body>
        <h1>Book List</h1>
        <form method="post" action="BookController" id="deleteRecords">
            <input type="submit" value="Delete Record By ID" id="deleteBook"/>
            <input type="number" min="0" value="0" id="delete" name="delete"/>
            <input name="action" type="hidden" value="delete"/>
        </form>
        <br>
        <form method="post" action="BookController" id="addRecords">
            <input type="submit" value="Add Record" id="add"/>
            <label>Book Title</label>
            <input type="text" placeholder="Book Title" id="addBookTitle" name="addBookTitle"/>
            <label>Book Isbn</label>
            <input type="date" id="addIsbn" name="addBookIsbn"/>
            <input name="action" type="hidden" value="add"/>
        </form>
        <br>
        <form method="post" action="GameController" id="updateRecords">
            <input type="submit" value="Update Record" id="update"/>
            <input type="number" min="0" value="0" name="updateIdSelector"/>
            <label>Book Title</label>
            <input type="text" name="updateBookTitle"/>
            <label>Book Isbn</label>
            <input type="text" name="updateBookIsbn"/>
            <input name="action" type="hidden" value="update"/>
        </form>
        <br>
        <table width="500" border="1" cellspacing="0" cellpadding="4">
            <tr style="background-color: black;color:white;">
                <th align="left" class="tableHead">ID</th>
                <th align="left" class="tableHead">Book Title</th>
                <th align="right" class="tableHead">Book ISBN</th>
                <th align="left" class="tableHead">Author Id</th>
            </tr>
        <c:forEach var="a" items="${books}" varStatus="rowCount">
            <c:choose>
                <c:when test="${rowCount.count % 2 == 0}">
                    <tr style="background-color: white;">
                </c:when>
                <c:otherwise>
                    <tr style="background-color: #ccffff;">
                </c:otherwise>
            </c:choose>
            <td align="left">${a.bookId}</td>
            <td align="left">${a.title}</td>
            <td align="left">${a.isbn}</td>
            <td align="left">${a.authorId}</td>
        </tr>
        </c:forEach>
        </table>
        <c:if test="${errMsg != null}">
            <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                ${errMsg}</p>
        </c:if>
    </body>
</html>