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