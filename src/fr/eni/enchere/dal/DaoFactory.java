package fr.eni.enchere.dal;

public class DaoFactory {
	
	public static EncheresDao getEncheresDao() {
		return new EncheresDaoJdbcImpl();
	}
	

	public static ConnexionDao getConnexionDao() {
		return new ConnexionDaoJdbcImpl();
	}

	public static UtilisateurDao getUtilisateurDao() {
		return new UtilisateurDaoJdbcImpl();
	}
}
