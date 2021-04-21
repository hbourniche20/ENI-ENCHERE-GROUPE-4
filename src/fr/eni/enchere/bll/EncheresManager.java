package fr.eni.enchere.bll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Utilisateur;
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

		return dao.selectAuctions(date, "", 0);
	}
	
	public List<ArticleVendu> recupererListeArticlesAvecFiltres(String nomArticle, Integer noCategorie) throws Exception {
		// Récupération de la date du jour
		LocalDate date = LocalDate.now();
		
		return dao.selectAuctions(date, nomArticle, noCategorie);
	}

	public List<ArticleVendu> recupererListeArticlesAvecFiltresAdditionnels(Utilisateur utilisateur, String nomArticle, Integer noCategorie, String encheresOuvertes, String mesEncheres, String mesEncheresRemportees, 
			String ventesEnCours, String ventesNonDebutees, String ventesTerminees) throws Exception {
		List<ArticleVendu> listeArticles = new ArrayList<>();
		LocalDate date = LocalDate.now();

		
		if(encheresOuvertes != null) {
			listeArticles = dao.selectCurrentAuctions(listeArticles, date, nomArticle, noCategorie);
			System.out.println("ma liste avec encheres ouvertes");
		}
		
		if(mesEncheres != null) {
			listeArticles = dao.selectMyAuctions(listeArticles, date, nomArticle, noCategorie,utilisateur);
			System.out.println("ma liste avec mes encheres");
		}
		
		if(mesEncheresRemportees != null) {
			listeArticles = dao.selectMyWinAuctions(listeArticles, date, nomArticle, noCategorie,utilisateur);
			System.out.println("ma liste avec mes encheres remportées");
		}
		
		if(ventesEnCours != null) {
			listeArticles = dao.selectCurrentSales(listeArticles, date, nomArticle, noCategorie,utilisateur);
			System.out.println("ma liste avec mes ventes actuelles");
		}
		
		if(ventesNonDebutees != null) {
			listeArticles = dao.selectNotBeginSales(listeArticles, date, nomArticle, noCategorie,utilisateur);
			System.out.println("ma liste avec mes ventes non débutées");
		}
		
		if(ventesTerminees != null ) {
			listeArticles = dao.selectFinishedSales(listeArticles, date, nomArticle, noCategorie,utilisateur);
			System.out.println("ma liste avec mes ventes terminées");
		}
		
		
		// Suppression des doublons
		
		// Tri par ordre croissant du nom de l'article
		
		return listeArticles;
	}

}
