package fr.eni.enchere.dal;

import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;

public interface ListeEncheresDao {
	
	List<Categorie> selectAllCategories() throws Exception;
	
	List<ArticleVendu> selectAllArticles() throws Exception;
	
}
