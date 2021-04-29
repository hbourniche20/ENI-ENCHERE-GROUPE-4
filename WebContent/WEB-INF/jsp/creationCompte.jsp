<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
			<jsp:include page="head.jsp"></jsp:include>
	</head>
	<body>
		<jsp:include page="header.jsp"></jsp:include>
	
		<div class="container pt-5">
			<h3 class="text-center my-4">Créer un compte</h3>
			<jsp:include page="error.jsp"></jsp:include>
			<form action ="${pageContext.request.contextPath }/creationCompte" method="POST">
				<jsp:include page="compteForm.jsp"></jsp:include>
			</form>
		</div>
	</body>
</html>