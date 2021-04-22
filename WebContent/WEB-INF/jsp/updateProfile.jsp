<form action="ProfileServlet" method="post">
	<div class="form-group row">
		<label for="pseudo" class="col-sm-3 col-form-label">Pseudo :</label>
		<div class="col-sm-9">
			<input type="text" name="pseudo" class="form-control" id="pseudo" value="${ user.getPseudo() }" disabled>
		</div>
	</div>
	<div class="form-group row">
		<label for="email" class="col-sm-3 col-form-label">Mail :</label>
		<div class="col-sm-9">
			<input type="text" name="email" class="form-control" id="email" value="${ user.getMail() }" disabled>
		</div>
	</div>
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
		<label for="rue" class="col-sm-3 col-form-label">Rue :</label>
		<div class="col-sm-9">
			<input type="text" name="rue" class="form-control" id="rue" value="${ user.getRue() }">
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
	<input type="submit" value="Modifier mes informations">
</form>
