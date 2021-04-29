<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h5 class="text-center mt-3 mb-2">Informations sur l'article</h5>
<div class="row">
	<div class="col">
		<div class="form-group row">					
		   	<label for="nom" class="col-sm-3 col-form-label">Article : </label>
		   	<div class="col-sm-9">
		      	<input type="text" name="nom" class="form-control" id="nom" value="${ article.getNomArticle() }" required>
		   	</div>
		</div>
		<div class="form-group row">
		   	<label for="description" class="col-sm-3 col-form-label">Description :</label>
		   	<div class="col-sm-9">
		      	<input type="text" name="description" class="form-control" id="description" value="${ article.getDescription() }" required>
			</div>
		</div>
		<div class="form-group row">
		   	<label for="noCategorie" class="col-sm-3 col-form-label">Catégorie :</label>
		   	<div class="col-sm-9">
		   	  	<select name="noCategorie" class="custom-select" required>
					<option selected value ="${ article.getCategorieArticle().getNoCategorie() }" > ${ article.getCategorieArticle().getLibelle() }</option>
					<c:forEach var="categorie" items="${ categories }" begin="0">
						<option value="${ categorie.noCategorie }">${ categorie.libelle }</option>
					</c:forEach>
				</select>
		   </div>
		</div>	
		<div class="form-group row">
		   	<label for="miseAPrix" class="col-sm-3 col-form-label">Mise à prix :</label>
		   	<div class="col-sm-9">
		      	<input type="number" name="miseAPrix" class="form-control" id="miseAPrix" value="${ article.getMiseAPrix() }" min=0>
		   	</div>
		</div>
		<div class="form-group row">
		   	<label for="dateDebut" class="col-sm-3 col-form-label">Début de l'enchère : </label>
		   	<div class="col-sm-9">
		      	<input type="date" name="dateDebut" class="form-control" id="dateDebut" value="${ article.getDateDebutEncheres() }"required>
		   	</div>
		</div>
		<div class="form-group row">
		   	<label for="dateFin" class="col-sm-3 col-form-label">Fin de l'enchère :</label>
		   	<div class="col-sm-9">
		     	<input type="date" name="dateFin" class="form-control" id="dateFin" value="${ article.getDateFinEncheres() }" required>
		   	</div>
		</div> 					
	</div>
</div>
	 		
<h5 class="text-center mt-3 mb-2">Retrait de l'article</h5>
<div class="row">
	<div class="col">
		<div class="form-group row">
		   	<label for="rue" class="col-sm-3 col-form-label">Rue :  </label>
		   	<div class="col-sm-9">
		      	<input type="text" name="rue" class="form-control" id="rue" value="${ article.getLieuRetrait().getRue() == null ? sessionScope.user.getRue() : article.getLieuRetrait().getRue() }" required> 
		   	</div>
		</div>
		<div class="form-group row">
		   	<label for="codePostal" class="col-sm-3 col-form-label">Code postal : </label>
		   	<div class="col-sm-9">
		      	<input type="text" name="codePostal" class="form-control" id="codePostal" value ="${article.getLieuRetrait().getCodePostal() == null ? sessionScope.user.getCodePostal() : article.getLieuRetrait().getCodePostal()} "required>
		   	</div>
		</div>
		<div class="form-group row">
		   	<label for="ville" class="col-sm-3 col-form-label">Ville :</label>
		   	<div class="col-sm-9">
		   	  	<input type="text" name="ville" class="form-control" id="ville" value ="${article.getLieuRetrait().getVille() == null ? sessionScope.user.getVille() : article.getLieuRetrait().getVille()}" required>
   		    </div>
		</div>	
	</div>
</div>
				
<div class="text-center mb-3">
	<button class="btn btn-success m-1">Enregistrer</button>		
	<a href="${ pageContext.request.contextPath }" class="btn btn-primary m-1">Retour</a>		
	<c:if test="${ article.getNoArticle() != null && user.getPseudo().equals(article.getVendeur().getPseudo()) }">
		<a href="${ pageContext.request.contextPath }/articles/supprimerArticle?noArticle=${ article.getNoArticle() }" class="btn btn-danger m-1">Annuler la vente</a>		
	</c:if>
</div>
					