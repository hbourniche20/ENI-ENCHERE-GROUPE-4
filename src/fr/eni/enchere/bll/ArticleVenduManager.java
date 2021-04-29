package fr.eni.enchere.bll;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.ArticleVenduDao;
import fr.eni.enchere.dal.CategorieDao;
import fr.eni.enchere.dal.ConnectionProvider;
import fr.eni.enchere.dal.DaoFactory;
import fr.eni.enchere.dal.UtilisateurDao;
import fr.eni.enchere.exception.AddArticleException;
import fr.eni.enchere.exception.ArticleVenduException;
import fr.eni.enchere.exception.CategorieException;
import fr.eni.enchere.exception.EnchereException;
import fr.eni.enchere.exception.UtilisateurNotFoundException;
import fr.eni.enchere.exception.WrongInputException;

public class ArticleVenduManager {
	
	private ArticleVenduDao dao;
	private CategorieDao daoC;
	
	public ArticleVenduManager() {
		dao = DaoFactory.getArticleVenduDao();
		daoC= DaoFactory.getCategorieDao();
	}

	public void enregistrerArticleVendu(ArticleVendu a) throws ArticleVenduException{
		
		verifierArticleVendu(a);
		
		dao.addArticleVendu(a);
	}
	
	private void verifierArticleVendu(ArticleVendu av) throws ArticleVenduException{
		LocalDate date = LocalDate.now();
		
		if(av.getNomArticle().equals("")) {
			throw new ArticleVenduException(ArticleVenduException.ARTICLE_NAME_NOT_DEFINED);
		}
		
		if(av.getDescription().equals("")) {
			throw new ArticleVenduException(ArticleVenduException.ARTICLE_DESCRIPTION_NOT_DEFINED);
		}
		
		if(av.getCategorieArticle()== null) {
			throw new ArticleVenduException(ArticleVenduException.ARTICLE_CATEGORIE_NOT_DEFINED);
		}
		
		if( av.getMiseAPrix() <= 0) {
			throw new ArticleVenduException(AddArticleException.WRONG_PRICE);
		}
		
		
		if(av.getDateDebutEncheres().isBefore(date)) {
			throw new ArticleVenduException(EnchereException.WRONG_BEGIN_AUCTION);
		}
		
		
		if(av.getDateFinEncheres().isBefore(date)) {
			throw new ArticleVenduException(EnchereException.WRONG_END_AUCTION);
		}
		if(av.getDateFinEncheres().isBefore(av.getDateDebutEncheres())) {
			throw new ArticleVenduException(AddArticleException.DATE_CONFLICT_END);
		}
		
		if(av.getLieuRetrait().getRue().equals("")) {
			throw new ArticleVenduException(AddArticleException.LOCATION_ROAD);
		}
		
		if(av.getLieuRetrait().getCodePostal().equals("")) {
			throw new ArticleVenduException(AddArticleException.LOCATION_CODE);
		}
		
		if(av.getLieuRetrait().getVille().equals("")) {
			throw new ArticleVenduException(AddArticleException.LOCATION_CITY);
		}
		if(av.getDateDebutEncheres().isEqual(av.getDateFinEncheres())) {
			throw new ArticleVenduException(AddArticleException.SAME_DATE);
		}
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
	
	public void modificationArticleVendu(ArticleVendu article ) throws ArticleVenduException{				
		if(article != null) {
			
			verifierArticleVendu(article);
			
			dao.updateArticleVendu(article);
		}else {
			throw new ArticleVenduException();
		}
		
	}
	
	
}
