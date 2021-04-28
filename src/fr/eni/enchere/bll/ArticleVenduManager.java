package fr.eni.enchere.bll;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.ArticleVenduDao;
import fr.eni.enchere.dal.ConnectionProvider;
import fr.eni.enchere.dal.DaoFactory;
import fr.eni.enchere.dal.UtilisateurDao;

import fr.eni.enchere.exception.ArticleVenduException;
import fr.eni.enchere.exception.EnchereException;
import fr.eni.enchere.exception.UtilisateurNotFoundException;
import fr.eni.enchere.exception.WrongInputException;

public class ArticleVenduManager {
	
	private ArticleVenduDao dao;
	
	
	public ArticleVenduManager() {
		dao = DaoFactory.getArticleVenduDao();
		
	}

	public void enregistrerArticleVendu(ArticleVendu a) throws ArticleVenduException{
		
		LocalDate date = LocalDate.now();
		
		if(a.getNomArticle().equals("")) {
			throw new ArticleVenduException(ArticleVenduException.ARTICLE_NAME_NOT_DEFINED);
		}
		
		if(a.getDescription().equals("")) {
			throw new ArticleVenduException(ArticleVenduException.ARTICLE_DESCRIPTION_NOT_DEFINED);
		}
		
		if(a.getCategorieArticle()== null) {
			throw new ArticleVenduException(ArticleVenduException.ARTICLE_CATEGORIE_NOT_DEFINED);
		}
		
		
		if(a.getDateDebutEncheres().isBefore(date)) {
			throw new ArticleVenduException(EnchereException.WRONG_BEGIN_AUCTION);
		}
		
		if(a.getDateFinEncheres().isBefore(date) || a.getDateFinEncheres().isBefore(a.getDateDebutEncheres())) {
			throw new ArticleVenduException(EnchereException.WRONG_END_AUCTION);
		}
		
		dao.addArticleVendu(a);
	}

	
	/**
	 * Recupérer un article à partir de son numéro
	 * 
	 * @param noArticle
	 * @return ArticleVendu
	 * @throws ArticleVenduException
	 */
	public ArticleVendu recupererArticleVendu(Integer noArticle) throws ArticleVenduException {
		return dao.selectArticleVenduById(noArticle);
	}
	
	/**
	 * Supprimer un article
	 * 
	 * @param vendeur
	 * @param noArticle
	 * @throws ArticleVenduException
	 */
	public void supprimerArticleVendu(Utilisateur vendeur, Integer noArticle) throws ArticleVenduException {
		LocalDate date = LocalDate.now();
		
		ArticleVendu article = recupererArticleVendu(noArticle);
		if(article != null) {
			if(vendeur.getNoUtilisateur() != article.getVendeur().getNoUtilisateur()) {
				throw new ArticleVenduException(ArticleVenduException.USER_FORBIDDEN_DELETE);
			}
			if(date.isEqual(article.getDateDebutEncheres()) || date.isAfter(article.getDateDebutEncheres())) {
				throw new ArticleVenduException(EnchereException.BEGIN_AUCTION);
			}
			
			// Suppression de l'article
			dao.delete(noArticle);
		} else {
			throw new ArticleVenduException();
		}
		
	}
	
	public void modificationArticleVendu(Utilisateur vendeur, Integer noArticle) throws ArticleVenduException{
		LocalDate date = LocalDate.now();
		
		ArticleVendu article = recupererArticleVendu(noArticle);
		
		if(article != null) {
			if(vendeur.getNoUtilisateur() != article.getVendeur().getNoUtilisateur()) {
				throw new ArticleVenduException(ArticleVenduException.USER_FORBIDDEN_UPDATE);
			}
			if(date.isEqual(article.getDateDebutEncheres()) || date.isAfter(article.getDateDebutEncheres())) {
				throw new ArticleVenduException(EnchereException.BEGIN_AUCTION);
			}
			dao.updateArticleVendu(noArticle);
		}else {
			throw new ArticleVenduException();
		}
		
	}
}
