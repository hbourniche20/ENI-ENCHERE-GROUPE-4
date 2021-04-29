package fr.eni.enchere.exception;

public class UtilisateurException extends Exception {
	private static final long serialVersionUID = 1L;

	public static final String USER_WRONG_PHONE_FORMAT = "Le numéro de téléphone n'est pas au bon format.";
	
	public static final String USER_WRONG_POSTCODE_FORMAT = "Le code postal n'est pas au bon format";
	
	public static final String USER_NEGATIVE_CREDITS = "Le crédit doit être supérieur à 0";
	
	public static final String USER_INVALID_POSTCODE_CHAR = "Le code postal contient des caratères interdits";

	public static final String USER_INVALID_PHONE_CHAR = "Le numéro de téléphone contient des caratères interdits";

	public static final String USER_CONFIRM_PASSWORD = "La confirmation du mot de passe doit être identique au mot de passe";
	
	public static final String USER_WRONG_PASSWORD = "Votre mot de passe ne correspond pas.";
	
	public static final String USER_PSEUDO_NOT_UNIQUE = "Le pseudo est déjà utilisé.";
	
	public static final String USER_MAIL_NOT_UNIQUE = "L'email est déjà utilisé.";

	
	public UtilisateurException(String message) {
		super(message);
	}

}
