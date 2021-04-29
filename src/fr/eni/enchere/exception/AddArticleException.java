package fr.eni.enchere.exception;

public class AddArticleException extends Exception {
	
	private static final long serialVersionUID =1L;
	
	public static final String NAME_LACK = "Le nom de l'article est obligatoire";
	public static final String DESCRIPTION_LACK = "La description est obligatoire";
	public static final String WRONG_START_DATE = "La date de début de l'enchère doit être égale ou postérieure à aujourd'hui";
	public static final String WRONG_END_DATE = "La date de fin de l'enchère doit être postérieure à la date du jour";
	public static final String DATE_CONFLICT_DEBUT = "La date de début d'enchère doit être antérieure à la date de fin d'enchère";
	public static final String DATE_CONFLICT_END = "La date de fin d'enchère doit être postérieure à la date de début d'enchère";
	public static final String WRONG_PRICE ="La mise à prix de votre article doit être supérieur à 0 point";
	public static final String LOCATION_ROAD = "La rue de retrait n'est pas renseignée";
	public static final String LOCATION_CITY = "La ville de retrait n'est pas renseignée";
	public static final String LOCATION_CODE = "Le code postal de retrait doit être renseigné";
	public static final String SAME_DATE = "Les dates de début et fin d'enchère ne peuvent pas être identiques";
	
	public AddArticleException(String message) {
		super(message);
	}
}
