package fr.eni.enchere.dal;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.exception.ArticleVenduException;

public interface ArticleVenduDao {
	
	void addArticleVendu(ArticleVendu a) throws Exception;
	
	ArticleVendu selectArticleVenduById(Integer noArticle) throws ArticleVenduException;

}
