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
	<jsp:include page="error.jsp"></jsp:include>
	<div class="container pt-5">
	 	<c:if test="${ not empty sessionScope.user && user.getPseudo().equals(article.getVendeur().getPseudo())  }"> 
	 		<h3 class="text-center my-4">Modifier la vente</h3> 
	 	</c:if> 
 		<c:if test="${not empty sessionScope.user && article.getNoArticle() == null }">
			<h3 class="text-center my-4">Vendre un article</h3>
	 	</c:if> 
	 	
		<form action ="${pageContext.request.contextPath }/ajouterArticle" method="POST">
			<input type="hidden" name="noArticle" value="${ article.getNoArticle() }"/>
			
			<h5 class="text-center mt-3 mb-2">Informations sur l'article</h5>
			<div class="row">
				<div class="col">
					<div class="form-group row">					
					   	<label for="nom" class="col-sm-3 col-form-label">Article : </label>
					   	<div class="col-sm-9">
							<c:if test="${not empty sessionScope.user && article.getNoArticle() != 0 }">			   	
						      	<input type="text" name="nom" class="form-control" id="nom" value="${ article.getNomArticle() }" required>
						    </c:if>
						    <c:if test="${not empty sessionScope.user && article.getNoArticle().equals(null) }">			   	
						      	<input type="text" name="nom" class="form-control" id="nom"  required>
						    </c:if>
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
					   	  	<select name="noCategorie" class="custom-select">
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
					      	<input type="text" name="rue" class="form-control" id="rue" value="${sessionScope.user.getRue() }" required> 
					   	</div>
					</div>
					<div class="form-group row">
					   	<label for="codePostal" class="col-sm-3 col-form-label">Code postal : </label>
					   	<div class="col-sm-9">
					      	<input type="text" name="codePostal" class="form-control" id="codePostal" value ="${sessionScope.user.getCodePostal()} "required>
					   	</div>
					</div>
					<div class="form-group row">
					   	<label for="ville" class="col-sm-3 col-form-label">Ville :</label>
					   	<div class="col-sm-9">
					   	  	<input type="text" name="ville" class="form-control" id="ville" value ="${sessionScope.user.getVille() }" required>
					   </div>
					</div>	
			</div>
			</div>
				
					<div class="text-center mb-3">
						<button class="btn btn-success m-1">Enregistrer</button>		
						<a href="${ pageContext.request.contextPath }" class="btn btn-primary m-1">Retour</a>		
						<c:if test="${not empty sessionScope.user && article.getNoArticle() != null && user.getPseudo().equals(article.getVendeur().getPseudo()) }">
							<a href="${ pageContext.request.contextPath }/SuppressionArticleServlet?noArticle=${ article.getNoArticle() }" class="btn btn-danger m-1">Annuler la vente</a>		
						</c:if>
					</div>
					
	</form>
</div>
</body>
</html>