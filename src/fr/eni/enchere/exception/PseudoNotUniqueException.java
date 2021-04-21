package fr.eni.enchere.exception;

public class PseudoNotUniqueException extends Exception {
	
	public PseudoNotUniqueException(String message) {
		super(message);
	}

	public PseudoNotUniqueException() {
		this("Le pseudo est déjà utilisé.");
	}

}
