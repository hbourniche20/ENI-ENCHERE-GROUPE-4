package fr.eni.enchere.exception;

public class CategorieException extends Exception {

	private static final long serialVersionUID = 1L;

	public CategorieException(String message) {
		super(message);
	}
	
	public CategorieException() {
		this("Impossible de récupérer la liste des catégories");
	}
}
