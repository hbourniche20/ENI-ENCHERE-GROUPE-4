package fr.eni.enchere.bll;

import java.time.LocalDate;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.dal.ArticleVenduDao;
import fr.eni.enchere.dal.DaoFactory;

public class ArticleVenduManager {
	
	private ArticleVenduDao dao;
	
	public ArticleVenduManager() {
		dao = DaoFactory.getArticleVenduDao();
	}

	public void enregistrerArticleVendu(ArticleVendu a) throws Exception{
		
		LocalDate date = LocalDate.now();
		
		if(a.getNomArticle() == null) {
			throw new Exception("Le nom de l'article est obligatoire");
		}
		
		if(a.getDescription() == null) {
			throw new Exception("La description de l'article est obligatoire");
		}
		
//		if(a.getCategorieArticle() == null) {
//			throw new Exception("La catégorie de l'article est obligatoire");
//		}
		
		
		if(a.getDateDebutEncheres().isBefore(date)) {
			throw new Exception("La date de mise en vente doit être postérieur à aujourd'hui");
		}
		
		if(a.getDateFinEncheres().isBefore(date) || a.getDateFinEncheres().isBefore(a.getDateDebutEncheres())) {
			throw new Exception("Erreur lors de la saisie de la date de fin d'enchères! (Vérifiez qu'elle est bien postérieur à aujourd'hui ou à la date de mise en vente");
		}
		
//		if(a.getLieuRetrait() == null) {
//			throw new Exception("Le lieu de retrait doit être rempli (Par défaut le votre)");
//		}
	}
}
