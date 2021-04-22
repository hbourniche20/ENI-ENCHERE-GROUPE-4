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
		<div style="margin-top:100px; padding:3%; text-align:center;">
			<c:choose>
				<c:when test="${ (not empty sessionScope.user && user.getPseudo() != sessionScope.user.getPseudo()) || empty sessionScope.user }">
					<div>Pseudo : ${ user.getPseudo() }</div>
					<div>Nom : ${ user.getNom() }</div>
					<div>Prenom : ${ user.getPrenom() }</div>
					<div>email : ${ user.getEmail() }</div>
					<div>Code Postal : ${ user.getCodePostal() }</div>
					<div>Ville : ${ user.getVille() }</div>
				</c:when>
				<c:otherwise>
					<div class="form-group row">
						<label for="nom" class="col-sm-3 col-form-label">Nom :</label>
						<div class="col-sm-9">
							<input type="text" name="nom" class="form-control" id="nom" value="${ user.getNom() }">
						</div>
					</div>
					<div class="form-group row">
						<label for="prenom" class="col-sm-3 col-form-label">Prénom :</label>
						<div class="col-sm-9">
							<input type="text" name="prenom" class="form-control" id="prenom" value="${ user.getPrenom() }" >
						</div>
					</div>
					<div class="form-group row">
						<label for="telephone" class="col-sm-3 col-form-label">Téléphone :</label>
						<div class="col-sm-9">
							<input type="text" name="telephone" class="form-control" id="telephone" value="${ user.getTelephone() }">
						</div>
					</div>
					<div class="form-group row">
						<label for="codePostal" class="col-sm-3 col-form-label">Code Postal :</label>
						<div class="col-sm-9">
							<input type="text" name="codePostal" class="form-control" id="codePostal" value="${ user.getCodePostal() }">
						</div>
					</div>
					<div class="form-group row">
						<label for="ville" class="col-sm-3 col-form-label">Ville :</label>
						<div class="col-sm-9">
							<input type="text" name="ville" class="form-control" id="Ville" value="${ user.getVille() }">
						</div>
					</div>
				</c:otherwise>
			</c:choose>
			
		</div>
</body>
</html>