<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	<title>ENI - Encheres</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>

	<div class="container pt-5">
		<h1 class="text-center my-4">Liste des enchères</h1>
		<h4 class="text-danger my-3">Filtres : </h4>
		
		<form method="POST">
			<div class="row">
				<div class="col-8 mr-1">
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
							<div class="row">
								<div class="col-6">
									<div class="form-check my-2">
									  	<input class="form-check-input" type="radio" name="encheres" id="achats" checked>
									  	<label class="form-check-label" for="achats">
									    	Achats
									  	</label>
									 </div>
									 <div class="custom-control custom-checkbox my-2 ml-3">
  										<input type="checkbox" class="custom-control-input" id="encheresOuvertes">
  										<label class="custom-control-label" for="encheresOuvertes">Enchères ouvertes</label>
									 </div>
									 <div class="custom-control custom-checkbox my-2 ml-3">
  										<input type="checkbox" class="custom-control-input" id="mesEncheres">
  										<label class="custom-control-label" for="mesEncheres">Mes enchères</label>
									 </div>
									 <div class="custom-control custom-checkbox my-2 ml-3">
  										<input type="checkbox" class="custom-control-input" id="mesEncheresRemportees">
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
  										<input type="checkbox" class="custom-control-input" id="ventesEnCours">
  										<label class="custom-control-label" for="ventesEnCours">Ventes en cours</label>
									 </div>
									 <div class="custom-control custom-checkbox my-2 ml-3">
  										<input type="checkbox" class="custom-control-input" id="ventesNonDebutees">
  										<label class="custom-control-label" for="ventesNonDebutees">Ventes non débutées</label>
									 </div>
									 <div class="custom-control custom-checkbox my-2 ml-3">
  										<input type="checkbox" class="custom-control-input" id="ventesTerminees">
  										<label class="custom-control-label" for="ventesTerminees">Ventes terminées</label>
									 </div>
								</div>
							</div>
	    				</c:when>
					</c:choose>		
				</div>
				<div class="col-2 ml-1">
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
							    <p class="card-text ">Fin de l'enchère : ${ article.getDateFinEncheres() }</p>
							    <p class="card-text">Vendeur : <a href="#" class="font-weight-bold">${ article.getVendeur().getPseudo() }</a></p>
							  </div>
						</div>
					  </div>
				</div>	
			</c:forEach>
		</div>
	
	</div>
	
</body>
</html>