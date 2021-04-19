package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.ConnexionDao;
import fr.eni.enchere.dal.DaoFactory;
import fr.eni.enchere.exception.ConnexionException;

public class ConnexionManager {

	ConnexionDao dao;
	
	public ConnexionManager() {
		dao = DaoFactory.getConnexionDao();
	}
	
	public Utilisateur authentification(String id, String password) throws ConnexionException {
		return this.dao.getSession(id, password);
	}
}
