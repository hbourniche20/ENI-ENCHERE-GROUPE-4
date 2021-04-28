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

	<jsp:include page="error.jsp"></jsp:include>

		<div class="row justify-content-md-center pt-2">
			<form action="${ pageContext.request.contextPath }/seConnecter" method="post" class="col-lg-6 col-sm pt-5" >
				<div class="form-group row">
					<label for="identifiant" class="col-sm col-form-label">Identifiant :</label>
					<div class="col-sm">
						<input type="text" name="id" class="form-control" id="identifiant" value="${ userId }">
					</div>
				</div>
				<div class="form-group row">
					<label for="password" class="col-sm col-form-label">Mot de passe :</label>
					<div class="col-sm">
						<input type="password" name="password" class="form-control" id="password" value="${ userPassword }">
					</div>
				</div>
				<div class="text-center my-3">
					<button class="btn btn-danger ">Connexion</button>
					<input type="checkbox" name="rememberMe" class="ml-3" id="rememberMe" value="yes">
					<label for="rememberMe" class="">Se souvenir de moi</label>
				</div>
				<div class="form-group text-center my-3">
					<a href="${ pageContext.request.contextPath }/enregistrerUtilisateur" class="btn btn-primary">Créer un compte</a>
				</div>
			</form>
		</div>
	</div>
	</body>
</html>