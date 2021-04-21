<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="head.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>

	<div class="container pt-5">
		<h1 class="text-center my-4">Liste des enchères</h1>
		<h4 class="text-danger my-3">Filtres : </h4>
		
		<form method="POST">
			<div class="row">
				<div class="col-10">
					<div class="form-group row">
					   	<label for="article" class="col-sm-2 col-form-label">Article</label>
					   	<div class="col-sm-10">
					      	<input type="text" name="article" class="form-control" id="article">
					   	</div>
					</div>
					<div class="form-group row">
					   	<label for="noCategorie" class="col-sm-2 col-form-label">Catégorie</label>
					   	<div class="col-sm-10">
					   	  	<select name="noCategorie" class="custom-select">
								<option value="">Toutes</option>
								<c:forEach var="categorie" items="${ categories }" begin="0">
									<option value="${ categorie.noCategorie }">${ categorie.libelle }</option>
								</c:forEach>
							</select>
					   </div>
					</div>	
					<c:choose>
	    				<c:when test="${ not empty sessionScope.user }">
							<div class="row align-items-center">
								<div class="col-6">
									<div class="form-check my-2">
									  	<input class="form-check-input" type="radio" name="encheres" id="achats" checked>
									  	<label class="form-check-label" for="achats">
									    	Achats
									  	</label>
									 </div>
									 <div class="custom-control custom-checkbox my-2 ml-3">
  										<input type="checkbox" name="encheresOuvertes" class="custom-control-input" id="encheresOuvertes" checked/>
  										<label class="custom-control-label" for="encheresOuvertes">Enchères ouvertes</label>
									 </div>
									 <div class="custom-control custom-checkbox my-2 ml-3">
  										<input type="checkbox" name="mesEncheres" class="custom-control-input" id="mesEncheres">
  										<label class="custom-control-label" for="mesEncheres">Mes enchères</label>
									 </div>
									 <div class="custom-control custom-checkbox my-2 ml-3">
  										<input type="checkbox" name="mesEncheresRemportees" class="custom-control-input" id="mesEncheresRemportees">
  										<label class="custom-control-label" for="mesEncheresRemportees">Mes enchères remportées</label>
									 </div>
								</div>
						   		<div class="col-6">
									<div class="form-check my-2">
									  	<input class="form-check-input" type="radio" name="encheres" id="ventes">
									  	<label class="form-check-label" for="ventes">
									    	Mes ventes
									  	</label>
									</div>
									<div class="custom-control custom-checkbox my-2 ml-3">
  										<input type="checkbox" name="ventesEnCours" class="custom-control-input" id="ventesEnCours">
  										<label class="custom-control-label" for="ventesEnCours">Ventes en cours</label>
									 </div>
									 <div class="custom-control custom-checkbox my-2 ml-3">
  										<input type="checkbox" name="ventesNonDebutees" class="custom-control-input" id="ventesNonDebutees">
  										<label class="custom-control-label" for="ventesNonDebutees">Ventes non débutées</label>
									 </div>
									 <div class="custom-control custom-checkbox my-2 ml-3">
  										<input type="checkbox" name="ventesTerminees" class="custom-control-input" id="ventesTerminees">
  										<label class="custom-control-label" for="ventesTerminees">Ventes terminées</label>
									 </div>
								</div>
							</div>
	    				</c:when>
					</c:choose>		
				</div>
				<div class="col-2">
					<button class="btn btn-danger">RECHERCHER</button>		
				</div>
			</div>
		</form>
		<div class="card-columns my-3">
			<c:forEach var="article" items="${ articles }" begin="0">
				<div class="card">
					<div class="card-body">
						<div class="row">
							<div class="col-3 bg-secondary">
						  	
						  	</div>
							<div class="col-9">
							  	<h5 class="card-title"><a href="#" class="text-dark">${ article.getNomArticle() }</a></h5>
							    <p class="card-text text-left mb-0">Prix : ${ article.getPrixVente() } points</p>
							    <fmt:parseDate  value="${ article.getDateFinEncheres() }"  type="date" pattern="yyyy-MM-dd" var="parsedDate" />
								<fmt:formatDate value="${ parsedDate }" type="date" pattern="dd/MM/yyyy" var="dateFinEncheres" />
							    <p class="card-text ">Fin de l'enchère : ${ dateFinEncheres }</p>
							    <p class="card-text">Vendeur : <a href="ProfileServlet?pseudo=${ article.getVendeur().getPseudo()}" class="font-weight-bold">${ article.getVendeur().getPseudo() }</a></p>
							  </div>
						</div>
					</div>
				</div>	
			</c:forEach>
		</div>
	</div>
	<jsp:include page="filtres.jsp"></jsp:include>
</body>
</html>