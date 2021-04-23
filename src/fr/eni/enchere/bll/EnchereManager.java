package fr.eni.enchere.bll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.ArticleVenduDao;
import fr.eni.enchere.dal.DaoFactory;
import fr.eni.enchere.dal.EncheresDao;
import fr.eni.enchere.exception.ArticleVenduException;
import fr.eni.enchere.exception.EnchereException;

public class EnchereManager {
	
	private EncheresDao dao;
	private ArticleVenduDao articleVenduDao;
	
	
	public EnchereManager() {
		dao = DaoFactory.getEnchereDao();
		articleVenduDao = DaoFactory.getArticleVenduDao();
	}
	
	
	public void ajouterEnchere(Integer noArticle, Utilisateur encherisseur, Integer montantEnchere) throws EnchereException, ArticleVenduException {
		LocalDate date = LocalDate.now();
		LocalDateTime heureActuelle = LocalDateTime.now();
		ArticleVendu article = articleVenduDao.selectArticleVenduById(noArticle);
		if(article.getNoArticle() != 0 & verifierEligibilite(article, encherisseur, date, montantEnchere)) {
			Enchere e = new Enchere();
			e.setArticle(article);
			e.setEncherisseur(encherisseur);
			e.setMontantEnchere(montantEnchere);
			e.setDateEnchere(heureActuelle);
			dao.insert(e);
		} else {
			throw new ArticleVenduException();
		}
	}

	public List<ArticleVendu> recupererListeArticles() throws EnchereException {
		// Récupération de la date du jour
		LocalDate date = LocalDate.now();
		String nomArticle = "";
		Integer noCategorie = 0;

		return dao.selectAuctions(date, nomArticle, noCategorie);
	}
	
	public List<ArticleVendu> recupererListeArticlesAvecFiltres(String nomArticle, Integer noCategorie) throws EnchereException {
		// Récupération de la date du jour
		LocalDate date = LocalDate.now();
		
		return dao.selectAuctions(date, nomArticle, noCategorie);
	}

	public List<ArticleVendu> recupererListeArticlesAvecFiltresAdditionnels(Utilisateur utilisateur, String nomArticle, Integer noCategorie, String encheresOuvertes, String mesEncheres, 
			String mesEncheresRemportees, String ventesEnCours, String ventesNonDebutees, String ventesTerminees) throws EnchereException {
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
	
	private boolean verifierEligibilite(ArticleVendu article, Utilisateur encherisseur, LocalDate date, Integer montantEnchere) throws ArticleVenduException, EnchereException {
		if(encherisseur.getNoUtilisateur() == article.getVendeur().getNoUtilisateur()) {
			throw new EnchereException(EnchereException.USER_FORBIDDEN);
		}
			
		if(date.isBefore(article.getDateDebutEncheres())) {
			throw new EnchereException(EnchereException.NOT_BEGIN_AUCTION);
		}
			
		if(date.isEqual(article.getDateFinEncheres()) || date.isAfter(article.getDateFinEncheres())) {
			throw new EnchereException(EnchereException.FINISHED_AUCTION);
		}
		
		if(article.getEncheres().size() > 0) {
			if(encherisseur.getCredit() <= article.getEncheres().get(article.getEncheres().size() - 1).getMontantEnchere()) {
				throw new ArticleVenduException(ArticleVenduException.CREDIT_LACK);
			}
			if(encherisseur.getNoUtilisateur() == article.getEncheres().get(article.getEncheres().size() - 1).getEncherisseur().getNoUtilisateur()) {
				throw new EnchereException(EnchereException.USER_LATEST_AUCTION);
			}
		} else {
			if(encherisseur.getCredit() < article.getMiseAPrix()) {
				throw new ArticleVenduException(ArticleVenduException.CREDIT_LACK);
			}
		}
		return true;
	}

}



























