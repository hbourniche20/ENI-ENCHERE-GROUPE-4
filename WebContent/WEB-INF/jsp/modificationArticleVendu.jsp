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
	 	<jsp:include page="error.jsp"></jsp:include>
		<form action ="${pageContext.request.contextPath }/articles/modifierArticle" method="POST">
			<input type="hidden" name="noArticle" value="${ article.getNoArticle() }"/>
			<input type ="hidden" name="noRetrait" value="${article.getLieuRetrait().getNoRetrait() }"/>
			<jsp:include page="articleVenduForm.jsp"></jsp:include>
	</form>
</div>
</body>
</html>