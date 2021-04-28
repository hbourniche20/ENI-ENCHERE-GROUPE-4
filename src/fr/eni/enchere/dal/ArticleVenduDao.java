package fr.eni.enchere.dal;

import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.bo.Utilisateur;

import fr.eni.enchere.exception.ArticleVenduException;


public interface ArticleVenduDao {
	
	void addArticleVendu(ArticleVendu a) throws ArticleVenduException;
	
	ArticleVendu selectArticleVenduById(Integer noArticle) throws ArticleVenduException;
	
	void delete(Integer noArticle) throws ArticleVenduException;
	
	void updateArticleVendu(ArticleVendu article) throws ArticleVenduException;

}
