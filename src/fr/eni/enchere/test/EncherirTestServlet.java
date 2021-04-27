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
    	/*ArticleVendu article = null;
		Integer noArticle = 0;
		String numeroArticle = "1";
		Utilisateur user = new Utilisateur();
		user.setNoUtilisateur(2);
		user.setPseudo("Dupond85");
		try {
			if(user.getNoUtilisateur() != 0) {
				noArticle = Integer.parseInt(numeroArticle);
				//article = manager.recupererArticleVendu(noArticle);
			} else {
				throw new ArticleVenduException("Vous n'avez pas l'autorisation d'acceder au détail d'un article");
			}
		} catch (NumberFormatException e) {
			printTestFail(e.getMessage());
		} catch (ArticleVenduException e) {
			printTestFail(e.getMessage());
		}
		
		System.out.println("Test réussi: Le détail de la vente de l'article a été affiché.");*/
	}
	
	private void testFailCreditLack() {
    	printNewTest("Enchère sur un article avec utilisateur n'ayant pas assez de crédit");
		
	}

	private void testFailUserDisconnected() {
    	printNewTest("Enchère sur un article avec utilisateur non connecté");
    	ArticleVendu article = null;
		Integer noArticle = 0;
		String numeroArticle = "1";
		Utilisateur user = new Utilisateur();
		
		try {
			if(user.getNoUtilisateur() != 0) {
				noArticle = Integer.parseInt(numeroArticle);
				//article = manager.recupererArticleVendu(noArticle);
			} else {
				throw new ArticleVenduException("Vous n'avez pas l'autorisation d'acceder au détail d'un article");
			}
			printTestFail("Exception non gérée");
		} catch (NumberFormatException e) {
			System.out.println("Test réussi: Le paramètre noArticle n'est pas un entier");
		} catch (ArticleVenduException e) {
			System.out.println("Test réussi: " + e.getMessage());
		} 
	}

	private void testFailNoArticle() {
    	printNewTest("Enchère sur un article sans le numéro de l'article");
		
	}

	private void testFailFinishedAuction() {
    	printNewTest("Enchère sur un article alors que les enchères sont terminées");
		
	}

	private void testFailNotBeginAuction() {
    	printNewTest("Enchère sur un article alors que les enchères ne sont pas commencées");
		
	}

	private void testFailOwnArticle() {
    	printNewTest("Enchère sur un article qui m'appartient");
		
	}

	private void testFailAlreadyOverbid() {
    	printNewTest("Enchère sur un article alors que je suis le dernier à avoir enchérit");		
	}	

/*
    Integer noArticle = 0;
	Integer montantEnchere = 0;
	try {
		if(request.getSession().getAttribute("user") != null) {
			Utilisateur encherisseur = (Utilisateur) request.getSession().getAttribute("user");
			noArticle = Integer.parseInt(request.getParameter("noArticle"));
			montantEnchere = Integer.parseInt(request.getParameter("montantEnchere"));
			manager.ajouterEnchere(noArticle, encherisseur, montantEnchere);
		} else {
			throw new ArticleVenduException(ArticleVenduException.USER_FORBIDDEN);
		}
		request.setAttribute("noArticle", noArticle);
		doGet(request, response);
	} catch(NumberFormatException e) {
		request.getSession().setAttribute("error", "Le numéro article doit être un nombre entier");
		doGet(request, response);
	} catch(Exception e) {
		System.out.println(e.getLocalizedMessage());
		request.getSession().setAttribute("error", e.getMessage());
		doGet(request, response);
	}*/
}
