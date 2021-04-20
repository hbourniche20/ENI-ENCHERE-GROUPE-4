package fr.eni.enchere.dal;

public class DaoFactory {
	
	public static ListeEncheresDao getListeEncheresDao() {
		return new ListeEncheresDaoJdbcImpl();
	}
	

	public static ConnexionDao getConnexionDao() {
		return new ConnexionDaoJdbcImpl();
	}

	public static UtilisateurDao getUtilisateurDao() {
		return new UtilisateurDaoJdbcImpl();
	}
}
