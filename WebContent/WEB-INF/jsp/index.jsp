<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<a href="">S'inscrire - Se connecter</a>
	<h1>Liste des enchères</h1>
	<h2>Filtres : </h2>
	<input type="text" name="article"/>
	<label>Catégorie : </label>
	<select>
		<option value="">--- Selectionnez une catégorie ---</option>
		<c:forEach var="categorie" items="${ categories }" begin="0">
			<option value="${ categorie.noCategorie }">${ categorie.libelle }</option>
		</c:forEach>
	</select>
	<button>RECHERCHER</button>
	<br/>
	<br/>
	<br/>
	<fieldset>
		<label>Nom</label>
		<label>Prix</label>
		<label>Fin de l'enchère</label>
		<label>Vendeur</label>
	</fieldset>
</body>
</html>