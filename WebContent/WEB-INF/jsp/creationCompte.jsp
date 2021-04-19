<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ENI - Enchère</title>
</head>
<body>
	<h2 style = "text-align:center"> Mon profil </h2>
	
	<form action ="" method="POST">
	<table>
	<th>
	<tr><p>Pseudo : <input type ="text" name="pseudo" /></p></tr>
	<tr><p>Prénom : <input type="text" name="prenom"/></p></tr>
	<tr>Téléphone : <input type="text" name ="telephone" /></p></tr>
	<tr>Code Postal : <input type="text" name ="codepostal" /></p></tr>
	<tr>Mot de passe : <input type="password" name ="motdepasse" /></p></tr>
	</th>
	<th>
	<tr>Nom : <input type="text" name ="nom" /></p></tr>
	<tr>Email : <input type="text" name ="email" /></p></tr>
	<tr>Rue : <input type="text" name ="rue" /></p></tr>
	<tr>Ville : <input type="text" name ="ville" /></p></tr>
	<tr>Confirmation : <input type="password" name ="confirmationmdp" /></p></tr>
	</th>
	
	</table>
	
	<p><button> Créer </button></p>
	</form>
</body>
</html>