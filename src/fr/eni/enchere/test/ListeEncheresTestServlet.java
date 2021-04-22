package fr.eni.enchere.test;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.EncheresManager;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exception.CategorieException;
import fr.eni.enchere.exception.EncheresException;

/**
 * Servlet implementation class ListeEncheresTestServlet
 */
@WebServlet("/servlet/fr.eni.enchere.test.ListeEncheresTestServlet")
public class ListeEncheresTestServlet extends TestServlet  {
	private static final long serialVersionUID = 1L;
	private static EncheresManager manager;
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	manager = new EncheresManager();
    	printFirstMessage();	
		testGetCategories();
		testGetAuctions();
		testGetAuctionsWithFiltersSuccess();
		testGetAuctionsWithFiltersError();
		testGetAuctionsWithAdditionnalFiltersSuccess();
		testGetAuctionsWithAdditionnalFiltersError();
    	printLastMessage(response);
	}

	private void testGetAuctionsWithAdditionnalFiltersError() {
		printNewTest("Récupération des enchères en cours avec filtres additionnels");
		List<ArticleVendu> articles = null;
		String nomArticle = "Veste";
		String categorie = "test";
		Utilisateur user = new Utilisateur();
		user.setNoUtilisateur(2);
		user.setPseudo("Dupond85");
		String encheresOuvertes = "on";
		String mesEncheres = "on";
		String mesEncheresRemportees = null;
		String ventesEnCours = null;
		String ventesNonDebutees = "on";
		String ventesTerminees = null;
		try {
			Integer noCategorie = Integer.parseInt(categorie);
		
			articles = manager.recupererListeArticlesAvecFiltresAdditionnels(user, nomArticle, noCategorie, encheresOuvertes, mesEncheres, mesEncheresRemportees, ventesEnCours, ventesNonDebutees, ventesTerminees);
		} catch (EncheresException e) {
			printTestFail(e.getMessage());
		}
		
		if(articles == null) {
			printTestFail("Articles null");
		} else {
			System.out.println("Test réussi: Les articles des enchères en cours avec filtres additionnels ont été récupérés.");
		}
		
	}

	private void testGetAuctionsWithAdditionnalFiltersSuccess() {
		printNewTest("Récupération des enchères en cours avec filtres additionnels");
		List<ArticleVendu> articles = null;
		String nomArticle = "Veste";
		Integer noCategorie = 3;
		Utilisateur user = new Utilisateur();
		user.setNoUtilisateur(2);
		user.setPseudo("Dupond85");
		String encheresOuvertes = "on";
		String mesEncheres = null;
		String mesEncheresRemportees = null;
		String ventesEnCours = null;
		String ventesNonDebutees = null;
		String ventesTerminees = null;

		try {		
			articles = manager.recupererListeArticlesAvecFiltresAdditionnels(user, nomArticle, noCategorie, encheresOuvertes, mesEncheres, mesEncheresRemportees, ventesEnCours, ventesNonDebutees, ventesTerminees);
		} catch (EncheresException e) {
			printTestFail(e.getMessage());
		}
		
		if(articles == null) {
			printTestFail("Articles null");
		} else {
			System.out.println("Test réussi: Les articles des enchères en cours avec filtres additionnels ont été récupérés.");
		}
		
	}

	private void testGetAuctionsWithFiltersError() {
		printNewTest("Récupération des enchères en cours avec filtres");
		List<ArticleVendu> articles = null;
		String nomArticle = "Veste";
		String categorie = "test";
		
		try {
			Integer noCategorie = Integer.parseInt(categorie);
		
			articles = manager.recupererListeArticlesAvecFiltres(nomArticle, noCategorie);
		} catch (EncheresException e) {
			printTestFail(e.getMessage());
		}
		
		if(articles == null) {
			printTestFail("Articles null");
		} else {
			System.out.println("Test réussi: Les articles des enchères en cours avec filtres ont été récupérés.");
		}
	}

	private void testGetAuctionsWithFiltersSuccess() {
		printNewTest("Récupération des enchères en cours avec filtres");
		List<ArticleVendu> articles = null;
		String nomArticle = "Veste";
		Integer noCategorie = 3;
		
		try {
			articles = manager.recupererListeArticlesAvecFiltres(nomArticle, noCategorie);
		} catch (EncheresException e) {
			printTestFail(e.getMessage());
		}
		
		if(articles == null) {
			printTestFail("Articles null");
		} else {
			System.out.println("Test réussi: Les articles des enchères en cours avec filtres ont été récupérés.");
		}
	}

	private void testGetAuctions() {
		printNewTest("Récupération des enchères en cours");
		List<ArticleVendu> articles = null;
		try {
			articles = manager.recupererListeArticles();
		} catch (EncheresException e) {
			printTestFail(e.getMessage());
		}
		
		if(articles == null) {
			printTestFail("Articles null");
		} else {
			System.out.println("Test réussi: Les articles des enchères en cours ont été récupérés.");
		}
	}

	private void testGetCategories() {
		printNewTest("Récupération des catégories");
		List<Categorie> categories = null;
		try {
			categories = manager.recupererListeCategories();
		} catch (CategorieException e) {
			printTestFail(e.getMessage());
		}
		
		if(categories == null) {
			printTestFail("Catégorie null");
		} else {
			System.out.println("Test réussi: Les catégories ont été récupérées.");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
