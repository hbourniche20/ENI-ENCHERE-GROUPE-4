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
	
	 	<c:if test="${ not empty sessionScope.user && user.getPseudo().equals(article.getVendeur().getPseudo())  }"> 
	 		<h3 class="text-center my-4">Modifier la vente</h3> 
	 	</c:if> 
 		<c:if test="${not empty sessionScope.user && article.getNoArticle() == null }">
			<h3 class="text-center my-4">Vendre un article</h3>
	 	</c:if> 
	 	<jsp:include page="alerts.jsp"></jsp:include>
		<form action ="${pageContext.request.contextPath }/articles/ajouterArticle" method="POST">		
			<jsp:include page="articleVenduForm.jsp"></jsp:include>				
	</form>
</div>
</body>
</html>