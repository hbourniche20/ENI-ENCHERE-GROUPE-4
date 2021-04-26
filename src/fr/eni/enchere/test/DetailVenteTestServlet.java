package fr.eni.enchere.test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.ArticleVenduManager;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exception.ArticleVenduException;

/**
 * Servlet implementation class DetailVenteTestServlet
 */
@WebServlet("/servlet/fr.eni.enchere.test.DetailVenteTestServlet")
public class DetailVenteTestServlet extends TestServlet {
	private static final long serialVersionUID = 1L;
	private static ArticleVenduManager manager;
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	manager = new ArticleVenduManager();
    	printFirstMessage();
    	testSaleDetailSuccess();
    	testSaleDetailUserFail();
    	testSaleDetailNoArticleFail();
    	printLastMessage(response);
	}

	private void testSaleDetailSuccess() {
		printNewTest("Récupération du détail d'une vente");
		ArticleVendu article = null;
		Integer noArticle = 0;
		String numeroArticle = "1";
		Utilisateur user = new Utilisateur();
		user.setNoUtilisateur(2);
		user.setPseudo("Dupond85");
		try {
			if(user.getNoUtilisateur() != 0) {
				noArticle = Integer.parseInt(numeroArticle);
				article = manager.recupererArticleVendu(noArticle);
			} else {
				throw new ArticleVenduException("Vous n'avez pas l'autorisation d'acceder au détail d'un article");
			}
		} catch (NumberFormatException e) {
			printTestFail(e.getMessage());
		} catch (ArticleVenduException e) {
			printTestFail(e.getMessage());
		}
		
		if(article == null) {
			printTestFail("Article null");
		} else {
			System.out.println("Test réussi: Le détail de la vente de l'article a été affiché.");
		}
	}

	private void testSaleDetailUserFail() {
		printNewTest("Récupération du détail d'une vente sans utilisateur connecté");
		ArticleVendu article = null;
		Integer noArticle = 0;
		String numeroArticle = "1";
		Utilisateur user = new Utilisateur();
		
		try {
			if(user.getNoUtilisateur() != 0) {
				noArticle = Integer.parseInt(numeroArticle);
				article = manager.recupererArticleVendu(noArticle);
			} else {
				throw new ArticleVenduException("Vous n'avez pas l'autorisation d'acceder au détail d'un article");
			}
		} catch (NumberFormatException e) {
			System.out.println("Test réussi: Le paramètre noArticle n'est pas un entier");
		} catch (ArticleVenduException e) {
			System.out.println("Test réussi: " + e.getMessage());
		} 
		
		if(article != null) {
			printTestFail("Exception non gérée");
		}
	}

	private void testSaleDetailNoArticleFail() {
		printNewTest("Récupération du détail d'une vente sans numéro d'article");
		ArticleVendu article = null;
		Integer noArticle = 0;
		String numeroArticle = "test";
		Utilisateur user = new Utilisateur();
		user.setNoUtilisateur(2);
		user.setPseudo("Dupond85");
		
		try {
			if(user.getNoUtilisateur() != 0) {
				noArticle = Integer.parseInt(numeroArticle);
				article = manager.recupererArticleVendu(noArticle);
			} else {
				throw new ArticleVenduException("Vous n'avez pas l'autorisation d'acceder au détail d'un article");
			}
		} catch (NumberFormatException e) {
			System.out.println("Test réussi: Le paramètre noArticle n'est pas un entier");
		} catch (ArticleVenduException e) {
			System.out.println("Test réussi: " + e.getMessage());
		} 
		
		if(article != null) {
			printTestFail("Exception non gérée");
		}
	}


}
