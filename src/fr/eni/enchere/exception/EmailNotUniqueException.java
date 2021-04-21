package fr.eni.enchere.exception;

public class EmailNotUniqueException extends Exception {

	public EmailNotUniqueException(String message) {
		super(message);
	}

	public EmailNotUniqueException() {
		this("L'email est déjà utilisé.");
	}
}
