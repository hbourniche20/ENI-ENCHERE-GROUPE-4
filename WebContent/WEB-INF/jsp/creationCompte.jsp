<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<title>ENI - Enchère</title>
</head>
<body>
	<h2 style = "text-align:center"> Mon profil </h2>
	
	<form action ="CreationCompteServlet" method="POST">
	
	<table class="table table-striped">
    <tbody>
      <tr>
        <td><p>Pseudo : <input type ="text" name="pseudo" /></p></td>
        <td><p>Nom : <input type="text" name ="nom" /></p></td>
    
      </tr>
      <tr>
        <td><p>Prénom : <input type="text" name="prenom"/></p></td>
        <td><p>Email : <input type="email" name ="email" /></p></td>
        
      </tr>
      <tr>
        <td><p>Téléphone : <input type="text" name ="telephone" /></p></td>
        <td><p>Rue : <input type="text" name ="rue" /></p></td>       
      </tr>
      
      <tr>
        <td><p>Code Postal : <input type="text" name ="codepostal" /></p></td>
        <td><p>Ville : <input type="text" name ="ville" /></p></td>       
      </tr>
      
      <tr>
        <td><p>Mot de passe : <input type="password" name ="motdepasse" /></p></td>
        <td><p>Confirmation : <input type="password" name ="confirmationmdp" /></p></td>       
      </tr>
    </tbody>
  </table>
  <p><button> Créer </button></p>
	</form>
	
</body>
</html>