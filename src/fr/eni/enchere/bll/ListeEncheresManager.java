package fr.eni.enchere.bll;

import java.util.List;

import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.dal.DaoFactory;
import fr.eni.enchere.dal.ListeEncheresDao;

public class ListeEncheresManager {
	
	private ListeEncheresDao dao;
	
	
	public ListeEncheresManager() {
		dao = DaoFactory.getListeEncheresDao();
	}
	
	public List<Categorie> recupererListeCategories() throws Exception {
		return dao.selectAllCategories();
	}

}
