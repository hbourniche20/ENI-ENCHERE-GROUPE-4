package fr.eni.enchere.dal;

import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.bo.Utilisateur;

public interface ArticleVenduDao {
	
	void addArticleVendu(ArticleVendu a) throws Exception;

	List<Categorie> selectAllCategories() throws Exception;
	
	//List<Retrait> selectAllRetraits() throws Exception;
	
	

}
