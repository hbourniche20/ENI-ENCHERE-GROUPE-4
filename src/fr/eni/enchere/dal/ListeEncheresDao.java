package fr.eni.enchere.dal;

import java.time.LocalDate;
import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;

public interface ListeEncheresDao {
	
	List<Categorie> selectAllCategories() throws Exception;

	List<ArticleVendu> selectAllArticles(LocalDate date) throws Exception;
	
	List<ArticleVendu> selectAllArticlesWithFilters(LocalDate date, String article, Integer noCategorie) throws Exception;

}
