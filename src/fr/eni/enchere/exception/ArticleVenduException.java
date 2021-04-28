package fr.eni.enchere.exception;

public class ArticleVenduException extends Exception {

	private static final long serialVersionUID = 1L;

	public static final String CREDIT_LACK = "Vous n'avez pas assez de crédit pour participer à l'enchère";
	
	public static final String USER_FORBIDDEN = "Vous n'avez pas l'autorisation d'acceder au détail d'un article";

	public static final String DELETE = "Impossible de supprimer l'article";
	
	public static final String UPDATE ="Impossible de modifier l'article";

	public static final String USER_FORBIDDEN_DELETE = "Vous n'avez pas l'autorisation de supprimer cet article";
	
	public static final String USER_FORBIDDEN_UPDATE = "Vous n'avez pas l'autorisation de modifier cet article";

	public static final String ARTICLE_NOT_FOUND = "Article inexistant";

	
	public ArticleVenduException(String message) {
		super(message);
	}
	
	public ArticleVenduException() {
		this("Impossible de récupérer le détail de l'article ");
	}
}
