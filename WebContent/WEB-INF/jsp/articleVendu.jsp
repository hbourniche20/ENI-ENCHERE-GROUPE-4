<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="head.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container py-5">
		<h3 class="text-center mt-5">Détail vente</h3>
		<form method="POST">
			<input type="hidden" name="noArticle" value="${ article.getNoArticle() }"/>
			<div class="row justify-content-md-center my-4">
				<div class="col-lg-6 col-sm">
					<div class="row align-items-center my-3">
						<div class="col">
							<h6 class="text-danger">${ article.getNomArticle() }</h6>					
						</div>
					</div>
					<div class="row align-items-center my-3">
						<div class="col my-auto">
							<span class="font-weight-bold">Description</span>
						</div>
						<div class="col my-auto">
							<label class="text-justify">
								${ article.getDescription() }
							</label>
						</div>
					</div>
					<div class="row align-items-center my-3">
						<div class="col my-auto">
							<span class="font-weight-bold">Catégorie</span>
						</div>
						<div class="col my-auto">
							${ article.getCategorieArticle().getLibelle() }
						</div>
					</div>
					<div class="row align-items-center my-3">
						<div class="col my-auto">
							<span class="font-weight-bold">Meilleure offre</span>
						</div>
						<div class="col my-auto">
							<c:choose>
								<c:when test="${ article.getEncheres().size() == 0 }">
									Pas d'enchères
								</c:when>
								<c:otherwise>
									${ article.getEncheres().get(article.getEncheres().size() - 1).getMontantEnchere() } points par ${ article.getEncheres().get(article.getEncheres().size() - 1).getEncherisseur().getPseudo() }
								</c:otherwise>
							</c:choose>
							
						</div>
					</div>
					<div class="row justify-content-md-center align-items-center my-3">
						<div class="col my-auto">
							<span class="font-weight-bold">Mise à prix</span>
						</div>
						<div class="col my-auto">
							<span class="badge badge-primary p-2">${ article.getMiseAPrix() } points</span>
						</div>
					</div>
					<div class="row align-items-center my-3">
						<div class="col my-auto">
							<span class="font-weight-bold">Fin de l'enchère</span>
						</div>
						<div class="col my-auto">
							<fmt:parseDate  value="${ article.getDateFinEncheres() }"  type="date" pattern="yyyy-MM-dd" var="parsedDate" />
							<fmt:formatDate value="${ parsedDate }" type="date" pattern="dd/MM/yyyy" var="dateFinEncheres" />
							${ dateFinEncheres }
						</div>
					</div>
					<div class="row align-items-center my-3">
						<div class="col my-auto">
							<span class="font-weight-bold">Retrait</span>
						</div>
						<div class="col my-auto">
							<label class="my-1">${ article.getLieuRetrait().getRue() }</label>
							<label class="my-1">${ article.getLieuRetrait().getCodePostal() } - ${ article.getLieuRetrait().getVille() }</label>
						</div>
					</div>
					<div class="row align-items-center my-3">
						<div class="col my-auto">
							<span class="font-weight-bold">Vendeur</span>
						</div>
						<div class="col my-auto">
							<label>${ article.getVendeur().getPseudo() }</label>
						</div>
					</div>
					<div class="row justify-content-md-center align-items-end my-3">
						<div class="col">
							<label for="montantEnchere" class="col-form-label font-weight-bold">Ma proposition</label>
							<input type="number" name="montantEnchere" class="form-control" id="montantEnchere"/>
						</div>
						<div class="col">
							<button class="btn btn-danger col-sm">Enchérir</button>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>