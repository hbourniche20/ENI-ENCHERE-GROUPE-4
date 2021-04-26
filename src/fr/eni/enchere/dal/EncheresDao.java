package fr.eni.enchere.dal;

import java.time.LocalDate;
import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exception.CategorieException;
import fr.eni.enchere.exception.EnchereException;

public interface EncheresDao {
	
	void insert(Enchere e) throws EnchereException;
	
	Enchere selectLastAuction(Integer noArticle) throws EnchereException;
	
	List<ArticleVendu> selectAuctions(LocalDate date, String nomArticle, Integer noCategorie) throws EnchereException;
	
	List<ArticleVendu> selectCurrentAuctions(List<ArticleVendu> listeArticles, LocalDate date, String nomArticle, Integer noCategorie) throws EnchereException;

	List<ArticleVendu> selectMyAuctions(List<ArticleVendu> listeArticles, LocalDate date, String nomArticle, Integer noCategorie, Utilisateur utilisateur) throws EnchereException;

	List<ArticleVendu> selectMyWinAuctions(List<ArticleVendu> listeArticles, LocalDate date, String nomArticle,	Integer noCategorie, Utilisateur utilisateur) throws EnchereException;

	List<ArticleVendu> selectCurrentSales(List<ArticleVendu> listeArticles, LocalDate date, String nomArticle, Integer noCategorie, Utilisateur utilisateur) throws EnchereException;

	List<ArticleVendu> selectNotBeginSales(List<ArticleVendu> listeArticles, LocalDate date, String nomArticle,	Integer noCategorie, Utilisateur utilisateur) throws EnchereException;

	List<ArticleVendu> selectFinishedSales(List<ArticleVendu> listeArticles, LocalDate date, String nomArticle,	Integer noCategorie, Utilisateur utilisateur) throws EnchereException;



	

}
