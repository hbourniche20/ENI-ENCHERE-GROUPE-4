package fr.eni.enchere.bll;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.ArticleVenduDao;
import fr.eni.enchere.dal.ConnectionProvider;
import fr.eni.enchere.dal.DaoFactory;
import fr.eni.enchere.dal.EncheresDao;
import fr.eni.enchere.dal.UtilisateurDao;
import fr.eni.enchere.exception.ArticleVenduException;
import fr.eni.enchere.exception.EnchereException;

public class EnchereManager {
	
	private EncheresDao dao;
	private ArticleVenduDao articleVenduDao;
	private UtilisateurDao utilisateurDao;
	
	
	public EnchereManager() {
		dao = DaoFactory.getEnchereDao();
		articleVenduDao = DaoFactory.getArticleVenduDao();
		utilisateurDao = DaoFactory.getUtilisateurDao();
	}
	
	
	public void ajouterEnchere(Integer noArticle, Utilisateur encherisseur, Integer montantEnchere) throws EnchereException, ArticleVenduException {
		Integer credit = null;
		LocalDate date = LocalDate.now();
		ArticleVendu article = articleVenduDao.selectArticleVenduById(noArticle);
		if(article != null) {
			if(verifierEligibilite(article, encherisseur, date, montantEnchere)) {
				Enchere enchere = setEnchere(article, encherisseur, date, montantEnchere);
				try(Connection con = ConnectionProvider.getConnection()) {
					try {
						con.setAutoCommit(false);
						
						// Créditer le précédent enchérisseur du montant de son enchère
						Enchere encherePrecedente = dao.selectLastAuction(con, noArticle);
						if(encherePrecedente != null) {
							credit = encherePrecedente.getEncherisseur().getCredit() + encherePrecedente.getMontantEnchere();
							Utilisateur encherisseurPrecedent = encherePrecedente.getEncherisseur();
							utilisateurDao.updateCredit(con, encherisseurPrecedent.getNoUtilisateur(), credit);
						}
						
						// Insérer l'enchère
						dao.insert(con, enchere);
						
						// Débiter le crédit de l'enchérisseur
						credit = enchere.getEncherisseur().getCredit() - enchere.getMontantEnchere();
						utilisateurDao.updateCredit(con, encherisseur.getNoUtilisateur(), credit);
						
						con.commit();
					} catch(SQLException e) {
						con.rollback();
						throw e;
					}
				} catch (Exception e) {
					throw new EnchereException();
				}
			} else {
				throw new ArticleVenduException();
			}
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
	
	private Enchere setEnchere(ArticleVendu article, Utilisateur encherisseur, LocalDate date, Integer montantEnchere) {
		LocalDateTime heureActuelle = LocalDateTime.now();
		Enchere enchere = new Enchere();
		enchere.setArticle(article);
		enchere.setEncherisseur(encherisseur);
		enchere.setMontantEnchere(montantEnchere);
		enchere.setDateEnchere(heureActuelle);
	
		return enchere;
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
			
		if(article.getEncheres().size() > 0) {
			if(encherisseur.getNoUtilisateur() == article.getEncheres().get(article.getEncheres().size() - 1).getEncherisseur().getNoUtilisateur()) {
				throw new EnchereException(EnchereException.USER_LATEST_AUCTION);
			}
			if(encherisseur.getCredit() <= article.getEncheres().get(article.getEncheres().size() - 1).getMontantEnchere()) {
				throw new ArticleVenduException(ArticleVenduException.CREDIT_LACK);
			}
		} else {
			if(encherisseur.getCredit() < article.getMiseAPrix()) {
				throw new ArticleVenduException(ArticleVenduException.CREDIT_LACK);
			}
		}
		
		if(date.isBefore(article.getDateDebutEncheres())) {
			throw new EnchereException(EnchereException.NOT_BEGIN_AUCTION);
		}
			
		if(date.isEqual(article.getDateFinEncheres()) || date.isAfter(article.getDateFinEncheres())) {
			throw new EnchereException(EnchereException.FINISHED_AUCTION);
		}
		
		
		return true;
	}

}



























