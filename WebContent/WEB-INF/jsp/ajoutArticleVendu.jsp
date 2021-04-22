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
		<h3 class="text-center my-4">Vendre un article</h3>
		
		<form action ="${pageContext.request.contextPath }/AjoutArticleVenduServlet" method="POST">
			<div class="row">
				<div class="col">
					<div class="form-group row">
					   	<label for="nom" class="col-sm-3 col-form-label">Article : </label>
					   	<div class="col-sm-9">
					      	<input type="text" name="nom" class="form-control" id="nom" required>
					   	</div>
					</div>
					<div class="form-group row">
					   	<label for="description" class="col-sm-3 col-form-label">Description :</label>
					   	<div class="col-sm-9">
					      	<input type="text" name="description" class="form-control" id="description" required>
					   	</div>
					</div>
					<div class="form-group row">
					   	<label for="noCategorie" class="col-sm-3 col-form-label">Catégorie :</label>
					   	<div class="col-sm-9">
					   	  	<select name="noCategorie" class="custom-select">
								<option value="">Toutes</option>
								<c:forEach var="categorie" items="${ categories }" begin="0">
									<option value="${ categorie.noCategorie }">${ categorie.libelle }</option>
								</c:forEach>
							</select>
					   </div>
					</div>	
					<div class="form-group row">
					   	<label for="miseAPrix" class="col-sm-3 col-form-label">Mise à prix :</label>
					   	<div class="col-sm-9">
					      	<input type="number" name="miseAPrix" class="form-control" id="miseAPrix" min=0>
					   	</div>
					</div>
					<div class="form-group row">
					   	<label for="dateDebut" class="col-sm-3 col-form-label">Début de l'enchère : </label>
					   	<div class="col-sm-9">
					      	<input type="date" name="dateDebut" class="form-control" id="dateDebut" required>
					   	</div>
					</div>
					<div class="form-group row">
					   	<label for="dateFin" class="col-sm-3 col-form-label">Fin de l'enchère :</label>
					   	<div class="col-sm-9">
					      	<input type="date" name="dateFin" class="form-control" id="dateFin" required>
					   	</div>
					</div>
<!-- 					<div class="form-group row"> -->
<!-- 					   	<label for="noRetrait" class="col-sm-3 col-form-label">Points de Retraits :</label> -->
<!-- 					   	<div class="col-sm-9"> -->
<!-- 					   	  	<select name="noRetrait" class="custom-select"> -->
<!-- 								<option value="">Toutes</option> -->
<%-- 								<c:forEach var="retrait" items="${ retraits }" begin="0"> --%>
<%--  									<option value="${ retrait.noRetrait }">${ retrait.rue } ${ retrait.codePostal } ${ retrait.ville }</option> ${ retrait.codePostal } ${ retrait.ville } --%>
<%-- 								</c:forEach> --%>
<!-- 							</select> -->
<!-- 					   </div> -->
<!-- 					</div>	 -->
					</div>
				</div>
				<h3 class="text-center my-4">Retrait de l'article</h3>
		
		
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
					
			<div class="text-center">
				<button class="btn btn-danger m-1">Enregistrer</button>		
				<a href="${ pageContext.request.contextPath }" class="btn btn-primary m-1">Annuler</a>		
			</div>
		</form>
		
	</div>	

</body>
</html>