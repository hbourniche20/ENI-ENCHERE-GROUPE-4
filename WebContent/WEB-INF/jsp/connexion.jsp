<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Connection</title>
	</head>
	<body>
		<h1>Connection</h1>
		<c:if test="${ not empty errors }">
			<div class="alert alert-danger text-center" role="alert">
					${ error } 
			</div>
		</c:if>
		<form action="connexion" method="post">
			<input type="text" name="id" placeholder="Entrez votre identifiant">
			<input type="password" name="password" placeholder="Entre votre mot de passe">
			<input type="submit" value="Se connecter">
		</form>
	</body>
</html>