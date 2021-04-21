package fr.eni.enchere.bll;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.dal.DaoFactory;
import fr.eni.enchere.dal.EncheresDao;

public class EncheresManager {
	
	private EncheresDao dao;
	
	
	public EncheresManager() {
		dao = DaoFactory.getEncheresDao();
	}
	
	public List<Categorie> recupererListeCategories() throws Exception {
		return dao.selectAllCategories();
	}
	
	public List<ArticleVendu> recupererListeArticles() throws Exception {
		// Récupération de la date du jour
		LocalDate date = LocalDate.now();

		return dao.selectAllArticles(date);
	}
	
	public List<ArticleVendu> recupererListeArticlesAvecFiltres(String article, Integer noCategorie) throws Exception {
		// Récupération de la date du jour
		LocalDate date = LocalDate.now();

		return dao.selectAllArticlesWithFilters(date, article, noCategorie);
	}

}
