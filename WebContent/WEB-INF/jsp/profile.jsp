<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="head.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container py-5">
		<c:choose>
			<c:when test="${ not empty sessionScope.user && user.getPseudo().equals(sessionScope.user.getPseudo()) }">
				<h3 class="text-center my-4">Mon profil</h3>
			</c:when>
			<c:otherwise>
				<h3 class="text-center my-4">Profil de l'utilisateur</h3>
			</c:otherwise>
		</c:choose>
		<div class="row justify-content-md-center">
			<div class="col-lg-6 col-sm">
				<div class="form-group row">
					<label for="pseudo" class="col-sm-3 col-form-label">Pseudo :</label>
					<div class="col-sm-9">
						<input type="text" name="pseudo" class="form-control" id="pseudo" value="${ user.getPseudo() }" readonly>
					</div>
				</div>
				<div class="form-group row">
					<label for="nom" class="col-sm-3 col-form-label">Nom :</label>
					<div class="col-sm-9">
						<input type="text" name="nom" class="form-control" id="nom" value="${ user.getNom() }" readonly>
					</div>
				</div>
				<div class="form-group row">
					<label for="prenom" class="col-sm-3 col-form-label">Prénom :</label>
					<div class="col-sm-9">
						<input type="text" name="prenom" class="form-control" id="prenom" value="${ user.getPrenom() }" readonly>
					</div>
				</div>
				<div class="form-group row">
					<label for="telephone" class="col-sm-3 col-form-label">Email :</label>
					<div class="col-sm-9">
						<input type="text" name="telephone" class="form-control" id="telephone" value="${ user.getEmail() }"readonly>
					</div>
				</div>
				<c:if test="${ not empty sessionScope.user && user.getPseudo().equals(sessionScope.user.getPseudo()) }">
					<div class="form-group row">
						<label for="telephone" class="col-sm-3 col-form-label">Téléphone :</label>
						<div class="col-sm-9">
							<input type="text" name="telephone" class="form-control" id="telephone" value="${ user.getTelephone() }" readonly>
						</div>
					</div>
					<div class="form-group row">
						<label for="codePostal" class="col-sm-3 col-form-label">Rue :</label>
						<div class="col-sm-9">
							<input type="text" name="codePostal" class="form-control" id="codePostal" value="${ user.getRue() }" readonly>
						</div>
					</div>
				</c:if>
				<div class="form-group row">
					<label for="codePostal" class="col-sm-3 col-form-label">Code Postal :</label>
					<div class="col-sm-9">
						<input type="text" name="codePostal" class="form-control" id="codePostal" value="${ user.getCodePostal() }" readonly>
					</div>
				</div>
				<div class="form-group row">
					<label for="ville" class="col-sm-3 col-form-label">Ville :</label>
					<div class="col-sm-9">
						<input type="text" name="ville" class="form-control" id="Ville" value="${ user.getVille() }" readonly>
					</div>
				</div>
				<c:if test="${ not empty sessionScope.user && user.getPseudo().equals(sessionScope.user.getPseudo()) }">
					<div class="form-group row">
						<label for="credit" class="col-sm-3 col-form-label">Credits :</label>
						<div class="col-sm-9">
							<input type="number" name="credit" class="form-control" id="Credit" value="${ user.getCredit() }" readonly>
						</div>
					</div>
					<div class="text-center mb-3">
						<a href="${ pageContext.request.contextPath }/enregistrerUtilisateur" class="btn btn-primary">Modifier</a>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>