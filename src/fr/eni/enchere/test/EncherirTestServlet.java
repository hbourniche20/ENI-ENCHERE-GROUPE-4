	package fr.eni.enchere.test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.ArticleVenduManager;
import fr.eni.enchere.bll.EnchereManager;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exception.ArticleVenduException;
import fr.eni.enchere.exception.EnchereException;

/**
 * Servlet implementation class EncherirTestServlet
 */
@WebServlet("/servlet/fr.eni.enchere.test.EncherirTestServlet")
public class EncherirTestServlet extends TestServlet {
	private static final long serialVersionUID = 1L;
	private static EnchereManager manager;

       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	manager = new EnchereManager();
    	printFirstMessage();
    	testSuccess();
    	testFailCreditLack();
    	testFailUserDisconnected();
    	testFailNoArticle();
    	testFailFinishedAuction();
    	testFailNotBeginAuction();
    	testFailOwnArticle();
    	testFailAlreadyOverbid();
    	printLastMessage(response);
	}
    

	private void testSuccess() {
    	printNewTest("Enchère sur un article");
    	Integer noArticle = 0;
    	Integer montant = 0;
		String numeroArticle = "3";
		String montantEnchere = "250";
		Utilisateur user = new Utilisateur();
		user.setNoUtilisateur(2);
		user.setPseudo("Dupond85");
		user.setCredit(1000);
		try {
			if(user.getNoUtilisateur() != 0) {
				noArticle = Integer.parseInt(numeroArticle);
				montant = Integer.parseInt(montantEnchere);
				manager.ajouterEnchere(noArticle, user, montant);
			} else {
				throw new ArticleVenduException(ArticleVenduException.USER_FORBIDDEN);
			}
		} catch (NumberFormatException e) {
			printTestFail(e.getMessage());
		} catch (ArticleVenduException e) {
			printTestFail(e.getMessage());
		} catch (EnchereException e) {
			printTestFail(e.getMessage());
		}
		
		System.out.println("Test réussi: Vous avez enchérit sur cet article.");
	}
	
	private void testFailCreditLack() {
    	printNewTest("Enchère sur un article avec utilisateur n'ayant pas assez de crédit");
    	boolean bReussi = false;
    	Integer noArticle = 0;
    	Integer montant = 0;
		String numeroArticle = "5";
		String montantEnchere = "250";
		Utilisateur user = new Utilisateur();
		user.setNoUtilisateur(2);
		user.setPseudo("Dupond85");
		user.setCredit(1000);
		
		try {
			if(user.getNoUtilisateur() != 0) {
				noArticle = Integer.parseInt(numeroArticle);
				montant = Integer.parseInt(montantEnchere);
				manager.ajouterEnchere(noArticle, user, montant);
				bReussi = true;
			} else {
				throw new ArticleVenduException(ArticleVenduException.USER_FORBIDDEN);
			}
		} catch (NumberFormatException e) {
			System.out.println("Test réussi: Le paramètre noArticle n'est pas un entier");
		} catch (ArticleVenduException e) {
			System.out.println("Test réussi: " + e.getMessage());
		} catch (EnchereException e) {
			System.out.println("Test réussi: " + e.getMessage());
		} 
		
		if(bReussi) {
			printTestFail("Sans message précédent : Exception non gérée");
		}
	}

	private void testFailUserDisconnected() {
    	printNewTest("Enchère sur un article avec utilisateur non connecté");
    	boolean bReussi = false;
    	Integer noArticle = 0;
    	Integer montant = 0;
		String numeroArticle = "2";
		String montantEnchere = "250";
		Utilisateur user = new Utilisateur();
		try {
			if(user.getNoUtilisateur() != 0) {
				noArticle = Integer.parseInt(numeroArticle);
				montant = Integer.parseInt(montantEnchere);
				manager.ajouterEnchere(noArticle, user, montant);
				bReussi = true;
			} else {
				throw new ArticleVenduException(ArticleVenduException.USER_FORBIDDEN);
			}
		} catch (NumberFormatException e) {
			System.out.println("Test réussi: Le paramètre noArticle n'est pas un entier");
		} catch (ArticleVenduException e) {
			System.out.println("Test réussi: " + e.getMessage());
		} catch (EnchereException e) {
			System.out.println("Test réussi: " + e.getMessage());
		} 
		
		if(bReussi) {
			printTestFail("Sans message précédent : Exception non gérée");
		}
	}

	private void testFailNoArticle() {
    	printNewTest("Enchère sur un article sans le numéro de l'article");
    	boolean bReussi = false;
    	Integer noArticle = 0;
    	Integer montant = 0;
		String numeroArticle = "10";
		String montantEnchere = "250";
		Utilisateur user = new Utilisateur();
		user.setNoUtilisateur(2);
		user.setPseudo("Dupond85");
		user.setCredit(1000);
		try {
			if(user.getNoUtilisateur() != 0) {
				noArticle = Integer.parseInt(numeroArticle);
				montant = Integer.parseInt(montantEnchere);
				manager.ajouterEnchere(noArticle, user, montant);
				bReussi = true;
			} else {
				throw new ArticleVenduException(ArticleVenduException.USER_FORBIDDEN);
			}
		} catch (NumberFormatException e) {
			System.out.println("Test réussi: Le paramètre noArticle n'est pas un entier");
		} catch (ArticleVenduException e) {
			System.out.println("Test réussi: " + e.getMessage());
		} catch (EnchereException e) {
			System.out.println("Test réussi: " + e.getMessage());
		} 
		
		if(bReussi) {
			printTestFail("Sans message précédent : Exception non gérée");
		}
	}

	private void testFailFinishedAuction() {
    	printNewTest("Enchère sur un article alors que les enchères sont terminées");
    	boolean bReussi = false;
    	Integer noArticle = 0;
    	Integer montant = 0;
		String numeroArticle = "2";
		String montantEnchere = "250";
		Utilisateur user = new Utilisateur();
		user.setNoUtilisateur(2);
		user.setPseudo("Dupond85");
		user.setCredit(1000);
		try {
			if(user.getNoUtilisateur() != 0) {
				noArticle = Integer.parseInt(numeroArticle);
				montant = Integer.parseInt(montantEnchere);
				manager.ajouterEnchere(noArticle, user, montant);
				bReussi = true;
			} else {
				throw new ArticleVenduException(ArticleVenduException.USER_FORBIDDEN);
			}
		} catch (NumberFormatException e) {
			System.out.println("Test réussi: Le paramètre noArticle n'est pas un entier");
		} catch (ArticleVenduException e) {
			System.out.println("Test réussi: " + e.getMessage());
		} catch (EnchereException e) {
			System.out.println("Test réussi: " + e.getMessage());
		} 
		
		if(bReussi) {
			printTestFail("Sans message précédent : Exception non gérée");
		}
	}

	private void testFailNotBeginAuction() {
    	printNewTest("Enchère sur un article alors que les enchères ne sont pas commencées");
    	boolean bReussi = false;
    	Integer noArticle = 0;
    	Integer montant = 0;
		String numeroArticle = "7";
		String montantEnchere = "250";
		Utilisateur user = new Utilisateur();
		user.setNoUtilisateur(2);
		user.setPseudo("Dupond85");
		user.setCredit(1000);
		try {
			if(user.getNoUtilisateur() != 0) {
				noArticle = Integer.parseInt(numeroArticle);
				montant = Integer.parseInt(montantEnchere);
				manager.ajouterEnchere(noArticle, user, montant);
				bReussi = true;
			} else {
				throw new ArticleVenduException(ArticleVenduException.USER_FORBIDDEN);
			}
		} catch (NumberFormatException e) {
			System.out.println("Test réussi: Le paramètre noArticle n'est pas un entier");
		} catch (ArticleVenduException e) {
			System.out.println("Test réussi: " + e.getMessage());
		} catch (EnchereException e) {
			System.out.println("Test réussi: " + e.getMessage());
		} 
		
		if(bReussi) {
			printTestFail("Sans message précédent : Exception non gérée");
		}
	}

	private void testFailOwnArticle() {
    	printNewTest("Enchère sur un article qui m'appartient");
    	boolean bReussi = false;
    	Integer noArticle = 0;
    	Integer montant = 0;
		String numeroArticle = "6";
		String montantEnchere = "250";
		Utilisateur user = new Utilisateur();
		user.setNoUtilisateur(2);
		user.setPseudo("Dupond85");
		user.setCredit(1000);
		try {
			if(user.getNoUtilisateur() != 0) {
				noArticle = Integer.parseInt(numeroArticle);
				montant = Integer.parseInt(montantEnchere);
				manager.ajouterEnchere(noArticle, user, montant);
				bReussi = true;
			} else {
				throw new ArticleVenduException(ArticleVenduException.USER_FORBIDDEN);
			}
		} catch (NumberFormatException e) {
			System.out.println("Test réussi: Le paramètre noArticle n'est pas un entier");
		} catch (ArticleVenduException e) {
			System.out.println("Test réussi: " + e.getMessage());
		} catch (EnchereException e) {
			System.out.println("Test réussi: " + e.getMessage());
		} 
		
		if(bReussi) {
			printTestFail("Sans message précédent : Exception non gérée");
		}
	}

	private void testFailAlreadyOverbid() {
    	printNewTest("Enchère sur un article alors que je suis le dernier à avoir enchérit");
    	boolean bReussi = false;
    	Integer noArticle = 0;
    	Integer montant = 0;
		String numeroArticle = "3";
		String montantEnchere = "250";
		Utilisateur user = new Utilisateur();
		user.setNoUtilisateur(2);
		user.setPseudo("Dupond85");
		user.setCredit(1000);
		try {
			if(user.getNoUtilisateur() != 0) {
				noArticle = Integer.parseInt(numeroArticle);
				montant = Integer.parseInt(montantEnchere);
				manager.ajouterEnchere(noArticle, user, montant);
				bReussi = true;
			} else {
				throw new ArticleVenduException(ArticleVenduException.USER_FORBIDDEN);
			}
		} catch (NumberFormatException e) {
			System.out.println("Test réussi: Le paramètre noArticle n'est pas un entier");
		} catch (ArticleVenduException e) {
			System.out.println("Test réussi: " + e.getMessage());
		} catch (EnchereException e) {
			System.out.println("Test réussi: " + e.getMessage());
		} 
		
		if(bReussi) {
			printTestFail("Sans message précédent : Exception non gérée");
		}
	}	
}
