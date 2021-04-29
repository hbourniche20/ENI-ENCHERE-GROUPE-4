package fr.eni.enchere.exception;

public class WrongInputException extends Exception {

	private static final long serialVersionUID = 1L;

	public WrongInputException(Character notAllowedChar) {
		super(notAllowedChar + " est un caractère interdit !");
	}

}
