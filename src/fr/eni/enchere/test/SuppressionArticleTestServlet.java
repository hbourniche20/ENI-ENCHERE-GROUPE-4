package fr.eni.enchere.test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.ArticleVenduManager;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exception.ArticleVenduException;
import fr.eni.enchere.exception.EnchereException;

/**
 * Servlet implementation class SuppressionArticleTestServlett
 */
@WebServlet("/servlet/fr.eni.enchere.test.SuppressionArticleTestServlet")
public class SuppressionArticleTestServlet extends TestServlet {
	private static final long serialVersionUID = 1L;
	private static ArticleVenduManager manager;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	manager = new ArticleVenduManager();
    	printFirstMessage();
    	testDeleteArticleSuccess();
    	testDeleteArticleUserIsSellerFail();
    	testDeleteArticleAuctionNotBeginFail();
    	testDeleteArticleUserFail();
    	testDeleteArticleNoArticleFail();
    	printLastMessage(response);
	}

	private void testDeleteArticleSuccess() {
		printNewTest("Suppression d'un article");
		Integer noArticle = 0;
		String numeroArticle = "41";
		Utilisateur user = new Utilisateur();
		user.setNoUtilisateur(2);
		
		try {
			if(user.getNoUtilisateur() != 0) {
				noArticle = Integer.parseInt(numeroArticle);
				manager.supprimerArticleVendu(user, noArticle);
			} else {
				throw new ArticleVenduException(ArticleVenduException.USER_FORBIDDEN);
			}
		} catch(NumberFormatException e) {
			printTestFail(e.getMessage());
			
		} catch(Exception e) {
			printTestFail(e.getMessage());
		}
		System.out.println("Test réussi: Vous avez supprimé sur cet article.");
	
	}

	private void testDeleteArticleUserIsSellerFail() {
		printNewTest("Suppression d'un article si l'utilisateur n'est pas le vendeur de l'article");
		boolean bReussi = false;
    	Integer noArticle = 0;
		String numeroArticle = "42";
		Utilisateur user = new Utilisateur();
		user.setNoUtilisateur(3);
		
		try {
			if(user.getNoUtilisateur() != 0) {
				noArticle = Integer.parseInt(numeroArticle);
				manager.supprimerArticleVendu(user, noArticle);
			} else {
				throw new ArticleVenduException(ArticleVenduException.USER_FORBIDDEN);
			}
		} catch (NumberFormatException e) {
			System.out.println("Test réussi: Le paramètre noArticle n'est pas un entier");
		} catch (ArticleVenduException e) {
			System.out.println("Test réussi: " + e.getMessage());
		} 
		
		if(bReussi) {
			printTestFail("Sans message précédent : Exception non gérée");
		}
	}

	private void testDeleteArticleAuctionNotBeginFail() {
		printNewTest("Suppression d'un article si les enchères ont déjà commencées");
		boolean bReussi = false;
    	Integer noArticle = 0;
		String numeroArticle = "6";
		Utilisateur user = new Utilisateur();
		user.setNoUtilisateur(2);
		
		try {
			if(user.getNoUtilisateur() != 0) {
				noArticle = Integer.parseInt(numeroArticle);
				manager.supprimerArticleVendu(user, noArticle);
			} else {
				throw new ArticleVenduException(ArticleVenduException.USER_FORBIDDEN);
			}
		} catch (NumberFormatException e) {
			System.out.println("Test réussi: Le paramètre noArticle n'est pas un entier");
		} catch (ArticleVenduException e) {
			System.out.println("Test réussi: " + e.getMessage());
		} 
		
		if(bReussi) {
			printTestFail("Sans message précédent : Exception non gérée");
		}
	}

	private void testDeleteArticleUserFail() {
		printNewTest("Suppression d'un article si l'utilisateur est deconnecté");
		boolean bReussi = false;
    	Integer noArticle = 0;
		String numeroArticle = "42";
		Utilisateur user = new Utilisateur();
		
		try {
			if(user.getNoUtilisateur() != 0) {
				noArticle = Integer.parseInt(numeroArticle);
				manager.supprimerArticleVendu(user, noArticle);
			} else {
				throw new ArticleVenduException(ArticleVenduException.USER_FORBIDDEN);
			}
		} catch (NumberFormatException e) {
			System.out.println("Test réussi: Le paramètre noArticle n'est pas un entier");
		} catch (ArticleVenduException e) {
			System.out.println("Test réussi: " + e.getMessage());
		} 
		
		if(bReussi) {
			printTestFail("Sans message précédent : Exception non gérée");
		}
	}

	private void testDeleteArticleNoArticleFail() {
		printNewTest("Suppression d'un article sans le numéro de l'article");
		boolean bReussi = false;
    	Integer noArticle = 0;
		String numeroArticle = "40";
		Utilisateur user = new Utilisateur();
		user.setNoUtilisateur(2);
		
		try {
			if(user.getNoUtilisateur() != 0) {
				noArticle = Integer.parseInt(numeroArticle);
				manager.supprimerArticleVendu(user, noArticle);
			} else {
				throw new ArticleVenduException(ArticleVenduException.USER_FORBIDDEN);
			}
		} catch (NumberFormatException e) {
			System.out.println("Test réussi: Le paramètre noArticle n'est pas un entier");
		} catch (ArticleVenduException e) {
			System.out.println("Test réussi: " + e.getMessage());
		} 
		
		if(bReussi) {
			printTestFail("Sans message précédent : Exception non gérée");
		}
	}

	
}
