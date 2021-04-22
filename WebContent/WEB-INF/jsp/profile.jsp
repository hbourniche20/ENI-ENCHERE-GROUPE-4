<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="head.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
		<div style="margin-top:100px; padding:3%; text-align:center;">
			<c:choose>
				<c:when test="${ (not empty sessionScope.user && user.getPseudo() != sessionScope.user.getPseudo()) || empty sessionScope.user }">
					<div>Pseudo : ${ user.getPseudo() }</div>
					<div>Nom : ${ user.getNom() }</div>
					<div>Prenom : ${ user.getPrenom() }</div>
					<div>email : ${ user.getEmail() }</div>
					<div>Code Postal : ${ user.getCodePostal() }</div>
					<div>Ville : ${ user.getVille() }</div>
				</c:when>
				<c:otherwise>
					<jsp:include page="updateProfile.jsp"></jsp:include>
				</c:otherwise>
			</c:choose>
			
		</div>
</body>
</html>