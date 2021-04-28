package fr.eni.enchere.exception;

public class WrongInputException extends Exception {
	private static final long serialVersionUID = 1L;

	public static final String USER_WRONG_PHONE_FORMAT = "Le numéro de téléphone n'est pas au bon format.";
	
	public static final String USER_WRONG_POSTCODE_FORMAT = "Le code postal n'est pas au bon format";
	
	public static final String USER_NEGATIVE_CREDITS = "Le crédit doit être supérieur à 0";
	
	public static final String USER_INVALID_POSTCODE_CHAR = "Le code postal contient des caratères interdits";

	public static final String USER_INVALID_PHONE_CHAR = "Le numéro de téléphone contient des caratères interdits";

	public WrongInputException(String message) {
		super(message);
	}

}
