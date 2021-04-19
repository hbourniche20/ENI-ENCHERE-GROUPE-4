package fr.eni.enchere.dal;


public class DaoFactory {
	
	public static ListeEncheresDao getListeEncheresDao() {
		return new ListeEncheresDao();
	}
	
}
