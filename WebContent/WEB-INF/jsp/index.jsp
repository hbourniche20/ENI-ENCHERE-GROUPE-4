<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<jsp:include page="header.jsp"></jsp:include>
	<h1>Liste des enchères</h1>
	<form method="POST">
		<h2>Filtres : </h2>
		<input type="text" name="article"/>
		<label>Catégorie : </label>
		<select name="noCategorie">
			<option value="">Toutes</option>
			<c:forEach var="categorie" items="${ categories }" begin="0">
				<option value="${ categorie.noCategorie }">${ categorie.libelle }</option>
			</c:forEach>
		</select>
		<button>RECHERCHER</button>
	</form>
	<br/>
	
	<c:forEach var="article" items="${ articles }" begin="0">
		<br/>
		<br/>
		<div>
			<label>${ article.getNomArticle() }</label>
			<br/>
			<label>Prix : ${ article.getPrixVente() }</label>
			<br/>
			<label>Fin de l'enchère : ${ article.getDateFinEncheres() }</label>
			<br/>
			<label>Vendeur : ${ article.getVendeur().getPseudo() }</label>
		</div>	
	</c:forEach>
</body>
</html>