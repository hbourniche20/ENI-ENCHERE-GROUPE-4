package fr.eni.enchere.exception;

public class EncheresException extends Exception {

	private static final long serialVersionUID = 1L;

	public EncheresException(String message) {
		super(message);
	}
	
	public EncheresException() {
		this("Impossible de récupérer la liste des enchères");
	}
}
