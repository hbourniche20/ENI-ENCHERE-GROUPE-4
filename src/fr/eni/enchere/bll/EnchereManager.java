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
	
	/**
	 * Ajouter une enchère sur un article
	 * 
	 * @param Integer noArticle
	 * @param Utilisateur encherisseur
	 * @param Integer montantEnchere
	 * @throws EnchereException
	 * @throws ArticleVenduException
	 */
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
						encherisseur.setCredit(credit);
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

	/**
	 * Recupere la liste des articles dont les enchères sont en cours
	 * @return List<ArticleVendu>
	 * @throws EnchereException
	 */
	public List<ArticleVendu> recupererListeArticles() throws EnchereException {
		// Récupération de la date du jour
		LocalDate date = LocalDate.now();
		String nomArticle = "";
		Integer noCategorie = 0;
		List<ArticleVendu> listeArticles = dao.selectAuctions(date, nomArticle, noCategorie);
		listeArticles = setEtatVenteToArticle(listeArticles, date);
		
		// Tri par ordre croissant du nom de l'article
		listeArticles.sort(Comparator.comparing(ArticleVendu::getDateFinEncheres));
		
		return listeArticles;
	}
	
	/**
	 * Recupere la liste des articles dont les enchères sont en cours (avec utilisation des filtres)
	 * 
	 * @param String nomArticle
	 * @param Integer noCategorie
	 * @return List<ArticleVendu>
	 * @throws EnchereException
	 */
	public List<ArticleVendu> recupererListeArticlesAvecFiltres(String nomArticle, Integer noCategorie) throws EnchereException {
		// Récupération de la date du jour
		LocalDate date = LocalDate.now();
		List<ArticleVendu> listeArticles = dao.selectAuctions(date, nomArticle, noCategorie);
		listeArticles = setEtatVenteToArticle(listeArticles, date);
		
		// Tri par ordre croissant du nom de l'article
		listeArticles.sort(Comparator.comparing(ArticleVendu::getDateFinEncheres));
		
		return listeArticles;
	}

	/**
	 * Recupere la liste des articles dont les enchères sont en cours (avec utilisation des filtres et en mode connecté)
	 * 
	 * @param Utilisateur utilisateur
	 * @param String nomArticle
	 * @param Integer noCategorie
	 * @param String encheresOuvertes
	 * @param String mesEncheres
	 * @param String mesEncheresRemportees
	 * @param String ventesEnCours
	 * @param String ventesNonDebutees
	 * @param String ventesTerminees
	 * @return List<ArticleVendu>
	 * @throws EnchereException
	 */
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
			listeArticles = setEtatVenteToArticle(listeArticles, date);
			
			// Tri par ordre croissant du nom de l'article
			listeArticles.sort(Comparator.comparing(ArticleVendu::getDateFinEncheres));
		}
		
		return listeArticles;
	}
	
	/**
	 * Appliquer l'état de la vente en fonction de la date et des enchères
	 * @param List<ArticleVendu> listeArticles
	 * @param Utilisateur utilisateur
	 * @param LocalDate date
	 * @return
	 */
	private List<ArticleVendu> setEtatVenteToArticle(List<ArticleVendu> listeArticles, LocalDate date) {
		for(int i = 0; i < listeArticles.size(); i++) {
			ArticleVendu article = listeArticles.get(i);
			if(article.getDateDebutEncheres().isAfter(date) && article.getEncheres().size() == 0) {
				article.setEtatVente("Vente en attente");
			} else if(article.getDateFinEncheres().isBefore(date) || article.getDateFinEncheres().isEqual(date)) {
				article.setEtatVente("Vente terminée");
			} else {
				article.setEtatVente("Vente en cours");
			}
		}
		
		return listeArticles;
	}

	/**
	 * Supprime de la liste les articles récupérés plusieurs fois
	 * @param List<ArticleVendu> listeArticles
	 * @return List<ArticleVendu> listeArticles
	 */
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
	
	/**
	 * Vérifie que les conditions de validité de l'enchère
	 * 
	 * @param ArticleVendu article
	 * @param Utilisateur encherisseur
	 * @param LocalDate date
	 * @param Integer montantEnchere
	 * @return true
	 * @throws ArticleVenduException
	 * @throws EnchereException
	 */
	private boolean verifierEligibilite(ArticleVendu article, Utilisateur encherisseur, LocalDate date, Integer montantEnchere) throws ArticleVenduException, EnchereException {
		// Le vendeur ne peut pas enchérir
		if(encherisseur.getNoUtilisateur() == article.getVendeur().getNoUtilisateur()) {
			throw new EnchereException(EnchereException.USER_FORBIDDEN);
		}
			
		if(article.getEncheres().size() > 0) {
			// L'utilisateur ne peut pas enchérir 2 fois de suite sur un article
			if(encherisseur.getNoUtilisateur() == article.getEncheres().get(article.getEncheres().size() - 1).getEncherisseur().getNoUtilisateur()) {
				throw new EnchereException(EnchereException.USER_LATEST_AUCTION);
			}
			
			// L'utilisateur n'a pas assez de crédit pour enchérir
			if(montantEnchere <= article.getEncheres().get(article.getEncheres().size() - 1).getMontantEnchere()) {
				throw new ArticleVenduException(EnchereException.INSUFFICIENT_AMOUNT);
			}
		} else {
			// L'utilisateur n'a pas assez de crédit pour enchérir
			if(montantEnchere < article.getMiseAPrix()) {
				throw new ArticleVenduException(EnchereException.INSUFFICIENT_AMOUNT);
			}
		}
		
		// Le montant de l'enchère ne peut être inférieur à celui de la précédente
		if(encherisseur.getCredit() < montantEnchere) {
			throw new ArticleVenduException(ArticleVenduException.CREDIT_LACK);
		}
					
		// Les enchères pour cet articles n'ont pas commencées
		if(date.isBefore(article.getDateDebutEncheres())) {
			throw new EnchereException(EnchereException.NOT_BEGIN_AUCTION);
		}
		
		// Les enchères pour cet articles sont terminées
		if(date.isEqual(article.getDateFinEncheres()) || date.isAfter(article.getDateFinEncheres())) {
			throw new EnchereException(EnchereException.FINISHED_AUCTION);
		}
		
		return true;
	}
	
	/**
	 * Instancier un objet Enchere
	 * 
	 * @param Article article
	 * @param Utilisateur encherisseur
	 * @param LocalDate date
	 * @param Integer montantEnchere
	 * @return Enchere enchere
	 */
	private Enchere setEnchere(ArticleVendu article, Utilisateur encherisseur, LocalDate date, Integer montantEnchere) {
		LocalDateTime heureActuelle = LocalDateTime.now();
		Enchere enchere = new Enchere();
		enchere.setArticle(article);
		enchere.setEncherisseur(encherisseur);
		enchere.setMontantEnchere(montantEnchere);
		enchere.setDateEnchere(heureActuelle);
	
		return enchere;
	}

}



























