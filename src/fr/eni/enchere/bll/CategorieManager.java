package fr.eni.enchere.bll;

import java.util.List;

import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.dal.CategorieDao;
import fr.eni.enchere.dal.DaoFactory;
import fr.eni.enchere.dal.EncheresDao;
import fr.eni.enchere.exception.CategorieException;

public class CategorieManager {

private CategorieDao dao;
	
	
	public CategorieManager() {
		dao = DaoFactory.getCategorieDao();
	}
	
	public List<Categorie> recupererListeCategories() throws CategorieException {
		return dao.selectAllCategories();
	}
	
	public List<Categorie> recupererAutresCategories(int noCategorie) throws CategorieException {	
		return dao.selectOtherCategories(noCategorie);
	}
}
