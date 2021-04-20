package fr.eni.enchere.exception;

public class ConnexionException extends Exception {

	public ConnexionException(String message) {
		super(message);
	}
	
	public ConnexionException() {
		this("Identifiant ou mot de passe incorrecte.");
	}
}
