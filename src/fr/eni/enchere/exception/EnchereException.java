package fr.eni.enchere.exception;

public class EnchereException extends Exception {

	private static final long serialVersionUID = 1L;

	public static final String FINISHED_AUCTION = "Désolé, les enchères pour cet article sont déjà terminées";

	public static final String NOT_BEGIN_AUCTION = "Veuillez patientez, l'article n'est pas encore proposé aux enchères";

	public static final String USER_FORBIDDEN = "Un utilisateur ne peut enchérir sur son propre article";

	public static final String USER_LATEST_AUCTION = "Vous avez déjà enchérie sur cet article";

	public static final String BEGIN_AUCTION = "L'article est déjà proposé aux enchères";
	
	public static final String WRONG_BEGIN_AUCTION ="La date de début d'enchère doit être postérieure ou égale à aujourd'hui";
	
	public static final String WRONG_END_AUCTION ="La date de fin d'enchère doit être postérieure à la date de début d'enchère";
	
	public EnchereException(String message) {
		super(message);
	}
	
	public EnchereException() {
		this("Impossible de récupérer la liste des enchères");
	}
}
