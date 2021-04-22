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
		<h3 class="text-center my-4">Créer un compte</h3>

		<jsp:include page="error.jsp"></jsp:include>

		<form action ="${pageContext.request.contextPath }/CreationCompteServlet" method="POST">
			<div class="row">
				<div class="col-lg col-sm">
					<div class="form-group row">
					   	<label for="pseudo" class="col-sm-3 col-form-label">Pseudo</label>
					   	<div class="col-sm-9">
					      	<input type="text" name="pseudo" class="form-control" id="pseudo" value="${pseudo}">
					   	</div>
					</div>
					<div class="form-group row">
					   	<label for="prenom" class="col-sm-3 col-form-label">Prénom</label>
					   	<div class="col-sm-9">
					      	<input type="text" name="prenom" class="form-control" id="prenom" value="${prenom}">
					   	</div>
					</div>
					<div class="form-group row">
					   	<label for="telephone" class="col-sm-3 col-form-label">Téléphone</label>
					   	<div class="col-sm-9">
					      	<input type="text" name="telephone" class="form-control" id="telephone" value="${telephone}">
					   	</div>
					</div>
					<div class="form-group row">
					   	<label for="codepostal" class="col-sm-3 col-form-label">Code Postal</label>
					   	<div class="col-sm-9">
					      	<input type="text" name="codepostal" class="form-control" id="codepostal" value="${codepostal}">
					   	</div>
					</div>
					<div class="form-group row">
					   	<label for="motdepasse" class="col-sm-3 col-form-label">Mot de passe</label>
					   	<div class="col-sm-9">
					      	<input type="password" name="motdepasse" class="form-control" id="motdepasse">
					   	</div>
					</div>
				</div>
				<div class="col-lg col-sm">
					<div class="form-group row">
					   	<label for="nom" class="col-sm-3 col-form-label">Nom</label>
					   	<div class="col-sm-9">
					      	<input type="text" name="nom" class="form-control" id="nom" value="${nom}">
					   	</div>
					</div>
					<div class="form-group row">
					   	<label for="email" class="col-sm-3 col-form-label">Email</label>
					   	<div class="col-sm-9">
					      	<input type="email" name="email" class="form-control" id="email" value="${email}">
					   	</div>
					</div>
					<div class="form-group row">
					   	<label for="rue" class="col-sm-3 col-form-label">Rue</label>
					   	<div class="col-sm-9">
					      	<input type="text" name="rue" class="form-control" id="rue" value="${rue}">
					   	</div>
					</div>
					<div class="form-group row">
					   	<label for="ville" class="col-sm-3 col-form-label">Ville</label>
					   	<div class="col-sm-9">
					      	<input type="text" name="ville" class="form-control" id="ville" value="${ville}">
					   	</div>
					</div>
					<div class="form-group row">
					   	<label for="confirmationmdp" class="col-sm-3 col-form-label">Confirmation</label>
					   	<div class="col-sm-9">
					      	<input type="password" name="confirmationmdp" class="form-control" id="confirmationmdp">
					   	</div>
					</div>
				</div>
			</div>
			<div class="text-center mb-3">
				<button class="btn btn-danger m-1">Créer</button>		
				<a href="${ pageContext.request.contextPath }" class="btn btn-primary m-1">Annuler</a>		
			</div>
		</form>
		
	</div>	
</body>
</html>