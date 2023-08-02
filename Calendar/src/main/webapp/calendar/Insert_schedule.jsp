<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>일정 등록</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/insert.css">
</head>
<body>
    <a href="${pageContext.request.contextPath}/calendar/calendar.jsp">달력보기</a>
    <c:if test="${not empty requestScope.currentDate }">
        <form name="insertForm" action="${pageContext.request.contextPath }/calendar/Insert.do" method="POST">
            <label for="event_title">event_title:</label>
                <select id="event_title_dropdown" name="event_title_dropdown">
			        <option value="To-do">To-do</option>
			        <option value="과제">과제</option>
			    </select>
            <input type="text" id="event_title" name="event_title" required><br>
            <label for="event_date">event_date:</label>
            <input type="text" id="event_date" name="event_date" value="<%=request.getAttribute("currentDate")%>" required><br>
            <label for="event_description">event_description:</label>
            <input type="text" id="event_description" name="event_description" required><br>
            <input class="insert-button" type="submit" value="일정 등록" onclick="return checkInsert(this.form);">
        </form>
    </c:if>

	<c:if test="${not empty requestScope.events}">
	        <table>
	            <tr>
	                <th>Event Date</th>
	                <th>Event Title</th>
	                <th>Event Description</th>
	                <th>Delete / Update</th>
	            </tr>
	            <c:forEach items="${requestScope.events}" var="event">
	                <tr>
	                    <td>${event.event_date}</td>
	                    <td>${event.event_title}</td>
	                    <td>${event.event_description}</td>
				            <td>
				                <button name="eventId" value="${event.event_id}" onclick="return deleteEvent('${event.event_id}')">Delete</button>
				            </td>
	                </tr>
	            </c:forEach>
	        </table>
	 </c:if>
 
    <c:if test="${empty requestScope.currentDate }">
        <div class="empty-message">
            뒤로 가서 날짜를 선택하세요
        </div>
    </c:if>

    <script type="text/javascript">
        function checkInsert(form) {
            var r = form;
            if (form.event_title.value === '' || form.event_date.value === '' || form.event_description.value === '') {
                alert("빈칸이 있습니다. 모두 입력해주세요.");
                return false;
            }
            return true;
        }
        
        function deleteEvent(eventId) {
            if (confirm("삭제 하시겠습니까?")) {
                $.ajax({
                    url: "${pageContext.request.contextPath}/calendar/Delete.do?event_id=" + eventId,
                    type: "DELETE",
                    success: function (data) {
                        alert("Event with ID " + eventId + " 삭제하였습니다.");
                        // You can handle the success response here if needed.
                    },
                    error: function (xhr, status, error) {
                        alert("Error deleting event: " + error);
                        // You can handle the error response here if needed.
                    }
                });
            } else {
                // The user canceled the deletion
            }
        }
    </script>
</body>
</html>