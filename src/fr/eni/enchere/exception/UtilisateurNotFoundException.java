package fr.eni.enchere.exception;

public class UtilisateurNotFoundException extends Exception {

	public UtilisateurNotFoundException(String message) {
		super(message);
	}

	public UtilisateurNotFoundException() {
		this("Impossible de trouver l'utilisateur");
	}

}
