package fr.eni.enchere.exception;

public class AddArticleException extends Exception {
	
	private static final long serialVersionUID =1L;
	
	public static final String NAME_LACK = "Le nom de l'article est obligatoire";
	public static final String DESCRIPTION_LACK = "La description est obligatoire";
	public static final String WRONG_START_DATE = "La date de début de l'enchère doit être égale ou postérieur à aujourd'hui";
	public static final String WRONG_END_DATE = "La date de fin de l'enchère doit être postérieur à la date de début de l'enchère";

	public AddArticleException(String message) {
		super(message);
	}
}
