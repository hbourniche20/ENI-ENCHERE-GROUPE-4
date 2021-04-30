<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div class="row">
	<div class="col-lg col-sm">
		<div class="form-group row">
		   	<label for="pseudo" class="col-sm-3 col-form-label">Pseudo</label>
		   	<div class="col-sm-9">
		      	<input type="text" name="pseudo" class="form-control" id="pseudo" value="${utilisateur.getPseudo()}" >
		   	</div>
		</div>
	</div>
	<div class="col-lg col-sm">
		<div class="form-group row">
		   	<label for="nom" class="col-sm-3 col-form-label">Nom</label>
		   	<div class="col-sm-9">
		      	<input type="text" name="nom" class="form-control" id="nom" value="${utilisateur.getNom()}" >
		   	</div>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-lg col-sm">
		<div class="form-group row">
		   	<label for="prenom" class="col-sm-3 col-form-label">Prénom</label>
		   	<div class="col-sm-9">
		      	<input type="text" name="prenom" class="form-control" id="prenom" value="${utilisateur.getPrenom()}" >
		   	</div>
		</div>
	</div>
	<div class="col-lg col-sm">
		<div class="form-group row">
		   	<label for="email" class="col-sm-3 col-form-label">Email</label>
		   	<div class="col-sm-9">
		      	<input type="text" name="email" class="form-control" id="email" value="${utilisateur.getEmail()}" >
		   	</div>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-lg col-sm">
		<div class="form-group row">
		   	<label for="telephone" class="col-sm-3 col-form-label">Téléphone</label>
		   	<div class="col-sm-9">
		      	<input type="text" name="telephone" class="form-control" id="telephone" value="${utilisateur.getTelephone()}" >
		   	</div>
		</div>
	</div>
	<div class="col-lg col-sm">
		<div class="form-group row">
		   	<label for="rue" class="col-sm-3 col-form-label">Rue</label>
		   	<div class="col-sm-9">
		      	<input type="text" name="rue" class="form-control" id="rue" value="${utilisateur.getRue()}" >
		   	</div>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-lg col-sm">
		<div class="form-group row">
		   	<label for="codepostal" class="col-sm-3 col-form-label">Code Postal</label>
		   	<div class="col-sm-9">
		      	<input type="text" name="codepostal" class="form-control" id="codepostal" value="${utilisateur.getCodePostal()}" >
		   	</div>
		</div>
	</div>
	<div class="col-lg col-sm">
		<div class="form-group row">
		   	<label for="ville" class="col-sm-3 col-form-label">Ville</label>
		   	<div class="col-sm-9">
		      	<input type="text" name="ville" class="form-control" id="ville" value="${utilisateur.getVille()}" >
		   	</div>
		</div>
	</div>
</div>
<c:if test="${ not empty sessionScope.user }">
	<div class="row">
		<div class="col-lg-6 col-sm">
			<div class="form-group row my-auto">
				<label for="motdepasse" class="col-sm-3 col-form-label">Mot de passe actuel</label>
				<div class="col-sm-9">
					<input type="password" name="motdepasse" class="form-control" id="motdepasse" >
				</div>
			</div>
		</div>
	</div>
</c:if>
<div class="row">
	<div class="col-lg col-sm">
		<div class="form-group row">
		   	<label for="newmotdepasse" class="col-sm-3 col-form-label">
		   	<c:if test="${ not empty sessionScope.user }">Nouveau</c:if> Mot de passe</label>
		   	<div class="col-sm-9">
		      	<input type="password" name="newmotdepasse" class="form-control" id="newmotdepasse">
		   	</div>
		</div>
	</div>
	<div class="col-lg col-sm">
		<div class="form-group row">
		   	<label for="confirmationmdp" class="col-sm-3 col-form-label">Confirmation</label>
		   	<div class="col-sm-9">
		      	<input type="password" name="confirmationmdp" class="form-control" id="confirmationmdp">
		   	</div>
		</div>
	</div>
</div>
<c:if test="${ not empty sessionScope.user }">
	<div class="row">
		<div class="col-lg-6 col-sm">
			<div class="form-group row">
				<label for="motdepasse" class="col-sm-3 col-form-label">Vos Credits</label>
				<div class="col-sm-9">
					<input type="number" value="${ utilisateur.getCredit() }" class="form-control" disabled>
				</div>
			</div>
		</div>
	</div>		
</c:if>
<div class="text-center mb-3">
	<button class="btn btn-success m-1">
		<c:choose>
			<c:when test="${ not empty sessionScope.user }">
				Enregistrer
			</c:when>
			<c:otherwise>
				Créer
			</c:otherwise>
		</c:choose>	
	</button>
	<c:if test="${ not empty sessionScope.user && sessionScope.user.getNoUtilisateur() == utilisateur.getNoUtilisateur() }">
		<a href="${ pageContext.request.contextPath }/utilisateur/supprimerUtilisateur" class="btn btn-danger m-1">Supprimer votre compte</a>	
	</c:if>
	<a href="${ pageContext.request.contextPath }" class="btn btn-primary m-1">Retour</a>	
</div>