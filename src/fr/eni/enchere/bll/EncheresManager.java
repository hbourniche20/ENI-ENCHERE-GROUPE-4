package fr.eni.enchere.bll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.DaoFactory;
import fr.eni.enchere.dal.EncheresDao;
import fr.eni.enchere.exception.CategorieException;
import fr.eni.enchere.exception.EncheresException;

public class EncheresManager {
	
	private EncheresDao dao;
	
	
	public EncheresManager() {
		dao = DaoFactory.getEncheresDao();
	}
	
	public List<ArticleVendu> recupererListeArticles() throws EncheresException {
		// Récupération de la date du jour
		LocalDate date = LocalDate.now();
		String nomArticle = "";
		Integer noCategorie = 0;

		return dao.selectAuctions(date, nomArticle, noCategorie);
	}
	
	public List<ArticleVendu> recupererListeArticlesAvecFiltres(String nomArticle, Integer noCategorie) throws EncheresException {
		// Récupération de la date du jour
		LocalDate date = LocalDate.now();
		
		return dao.selectAuctions(date, nomArticle, noCategorie);
	}

	public List<ArticleVendu> recupererListeArticlesAvecFiltresAdditionnels(Utilisateur utilisateur, String nomArticle, Integer noCategorie, String encheresOuvertes, String mesEncheres, 
			String mesEncheresRemportees, String ventesEnCours, String ventesNonDebutees, String ventesTerminees) throws EncheresException {
		List<ArticleVendu> listeArticles = new ArrayList<>();
		LocalDate date = LocalDate.now();
		
		if(encheresOuvertes != null) {
			listeArticles = dao.selectCurrentAuctions(listeArticles, date, nomArticle, noCategorie);
		}
		
		if(mesEncheres != null) {
			listeArticles = dao.selectMyAuctions(listeArticles, date, nomArticle, noCategorie,utilisateur);
		}
		
		if(mesEncheresRemportees != null) {
			listeArticles = dao.selectMyWinAuctions(listeArticles, date, nomArticle, noCategorie,utilisateur);
		}
		
		if(ventesEnCours != null) {
			listeArticles = dao.selectCurrentSales(listeArticles, date, nomArticle, noCategorie,utilisateur);
		}
		
		if(ventesNonDebutees != null) {
			listeArticles = dao.selectNotBeginSales(listeArticles, date, nomArticle, noCategorie,utilisateur);
		}
		
		if(ventesTerminees != null ) {
			listeArticles = dao.selectFinishedSales(listeArticles, date, nomArticle, noCategorie,utilisateur);
		}
		
		if(listeArticles.size() > 0) {
			// Suppression des doublons
			listeArticles = removeArticlesDuplications(listeArticles);
		
			// Tri par ordre croissant du nom de l'article
			listeArticles.sort(Comparator.comparing(ArticleVendu::getDateFinEncheres));
		}
		
		return listeArticles;
	}

	private List<ArticleVendu> removeArticlesDuplications(List<ArticleVendu> listeArticles) {
		for(int i = 0; i < listeArticles.size(); i++) {
			ArticleVendu article = listeArticles.get(i);
			for(int j = 0; j < listeArticles.size(); j++) {
				if(listeArticles.get(j).getNoArticle() == article.getNoArticle() && i != j) {
					listeArticles.remove(j);
				}
			}
		}
		
		return listeArticles;
	}

}



























