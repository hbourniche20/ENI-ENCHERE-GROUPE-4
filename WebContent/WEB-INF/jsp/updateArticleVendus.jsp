<form action ="AjoutArticleVenduServlet" method = "POST">
<div class="container pt-5">
		<h3 class="text-center my-4">Modifier la vente de l'article</h3>
		
	
			<div class="row">
				<div class="col">
					<div class="form-group row">
					   	<label for="nom" class="col-sm-3 col-form-label">Article : </label>
					   	<div class="col-sm-9">
					      	<input type="text" name="nom" class="form-control" id="nom" value ="${sessionScope.article.getNomArticle() }" required>
					   	</div>
					</div>
					<div class="form-group row">
					   	<label for="description" class="col-sm-3 col-form-label">Description :</label>
					   	<div class="col-sm-9">
					      	<input type="text" name="description" class="form-control" id="description" value ="${sessionScope.article.getDescription() }" required>
					   	</div>
					</div>
					<div class="form-group row">
					   	<label for="noCategorie" class="col-sm-3 col-form-label">Catégorie :</label>
					   	<div class="col-sm-9">
					   	  	<select name="noCategorie" class="custom-select">
								<option value ="${sessionScope.article.getCategorieArticle().getLibelle() }">Toutes</option>
								<c:forEach var="categorie" items="${ categories }" begin="0">
									<option value="${ categorie.noCategorie }">${ categorie.libelle }</option>
								</c:forEach>
							</select>
					   </div>
					</div>	
					<div class="form-group row">
					   	<label for="miseAPrix" class="col-sm-3 col-form-label">Mise à prix :</label>
					   	<div class="col-sm-9">
					      	<input type="number" name="miseAPrix" class="form-control" id="miseAPrix" min=0 value ="${sessionScope.article.getMiseAPrix() }">
					   	</div>
					</div>
					<div class="form-group row">
					   	<label for="dateDebut" class="col-sm-3 col-form-label">Début de l'enchère : </label>
					   	<div class="col-sm-9">
					      	<input type="date" name="dateDebut" class="form-control" id="dateDebut" value ="${sessionScope.article.getDateDebutEncheres() }" required>
					   	</div>
					</div>
					<div class="form-group row">
					   	<label for="dateFin" class="col-sm-3 col-form-label">Fin de l'enchère :</label>
					   	<div class="col-sm-9">
					      	<input type="date" name="dateFin" class="form-control" id="dateFin" value ="${sessionScope.article.getDateFinEncheres() }" required>
					   	</div>
					</div> 					
					</div>
				</div>
				<h3 class="text-center my-4">Retrait de l'article</h3>
		
		
			<div class="row">
				<div class="col">
					<div class="form-group row">
					   	<label for="rue" class="col-sm-3 col-form-label">Rue :  </label>
					   	<div class="col-sm-9">
					      	<input type="text" name="rue" class="form-control" id="rue" value="${sessionScope.article.getVendeur().getRue() }" required> 
					   	</div>
					</div>
					<div class="form-group row">
					   	<label for="codePostal" class="col-sm-3 col-form-label">Code postal : </label>
					   	<div class="col-sm-9">
					      	<input type="text" name="codePostal" class="form-control" id="codePostal" value ="${sessionScope.article.getVendeur().getCodePostal()} "required>
					   	</div>
					</div>
					<div class="form-group row">
					   	<label for="ville" class="col-sm-3 col-form-label">Ville :</label>
					   	<div class="col-sm-9">
					   	  	<input type="text" name="ville" class="form-control" id="ville" value ="${sessionScope.article.getVendeur().getVille() }" required>
					   </div>
					</div>	
					
			<div class="text-center">
				<button class="btn btn-danger m-1">Enregistrer les modifications</button>		
				<a href="${ pageContext.request.contextPath }" class="btn btn-primary m-1">Annuler les modifications</a>		
			</div>
</form>