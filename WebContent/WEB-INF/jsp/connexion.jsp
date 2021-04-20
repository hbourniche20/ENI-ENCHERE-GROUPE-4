<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

		<c:if test="${ not empty errors }">
			<div class="alert alert-danger text-center pt-5" role="alert">
					${ error } 
			</div>
		</c:if>
		<div class="row justify-content-md-center pt-5">
			<form action="ConnexionServlet" method="post" class="col-6 pt-5" >
				<div class="form-group row">
					<label for="identifiant" class="col-sm-3 col-form-label">Identifiant :</label>
					<div class="col-sm-9">
						<input type="text" name="id" class="form-control" id="identifiant">
					</div>
				</div>
				<div class="form-group row">
					<label for="password" class="col-sm-3 col-form-label">Mot de passe :</label>
					<div class="col-sm-9">
						<input type="password" name="password" class="form-control" id="password">
					</div>
				</div>
				<div class="row justify-content-md-center my-3">
					<button class="btn btn-danger ">Connexion</button>
				</div>
				<div class="row justify-content-md-center my-3">
					<a href="CreationCompteServlet" class="btn btn-primary">Créer un compte</a>
				</div>
			</form>
		</div>
	</div>
	</body>
</html>