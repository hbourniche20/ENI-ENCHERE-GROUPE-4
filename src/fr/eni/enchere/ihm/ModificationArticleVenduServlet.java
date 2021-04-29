package fr.eni.enchere.ihm;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.ArticleVenduManager;
import fr.eni.enchere.bll.CategorieManager;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exception.ArticleVenduException;

/**
 * Servlet implementation class ModifArticleVenduServlet
 */
@WebServlet("/articles/modifierArticle")
public class ModificationArticleVenduServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	ArticleVenduManager manager = new ArticleVenduManager();
		CategorieManager managerC = new CategorieManager();
		Integer noArticle = null;
		ArticleVendu article = null;
		List<Categorie> listeCategories = null;
		
		try {
			if(request.getSession().getAttribute("user") != null){
				noArticle = Integer.parseInt(request.getParameter("noArticle"));
						
				article = manager.recupererArticleVendu(noArticle);
				request.setAttribute("article", article);	
				
				listeCategories = managerC.recupererAutresCategories(article.getCategorieArticle().getNoCategorie());
				request.setAttribute("categories", listeCategories);
				
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/modificationArticleVendu.jsp");
				rd.forward(request, response);
				
			} else {
				throw new ArticleVenduException("Vous n'avez pas l'autorisation de modifier cet article");
			}		
		} catch(Exception e) {
			request.setAttribute("error", e.getMessage());
			response.sendRedirect(request.getContextPath());
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("user");
		String nomArticleVendu = request.getParameter("nom");
		String descriptionArticleVendu = request.getParameter("description");	
		
		String rue = request.getParameter("rue");
		String codePostal = request.getParameter("codePostal");
		String ville = request.getParameter("ville");
		int noCategorie = 0;
		int noArticle =0;
		int noRetrait = 0;
		
		try {
			LocalDate dateDebut = LocalDate.parse(request.getParameter("dateDebut"));
			LocalDate dateFin = LocalDate.parse(request.getParameter("dateFin"));
			noArticle = Integer.parseInt(request.getParameter("noArticle"));
			noRetrait = Integer.parseInt(request.getParameter("noRetrait"));
			noCategorie = Integer.parseInt(request.getParameter("noCategorie"));
			int miseAPrix = Integer.parseInt(request.getParameter("miseAPrix"));
			Categorie c = new Categorie();
			c.setNoCategorie(noCategorie);
			
			Retrait r = new Retrait(rue,codePostal,ville,noRetrait);
			
			ArticleVendu av = new ArticleVendu(noArticle, nomArticleVendu, descriptionArticleVendu, dateDebut, dateFin, miseAPrix, c, r, utilisateur);
			ArticleVenduManager manager = new ArticleVenduManager();
			
			manager.modificationArticleVendu(av);
			request.getSession().setAttribute("success", "L'article a été modifié");
			response.sendRedirect(request.getContextPath());		
		}catch(DateTimeParseException e) {
			e.printStackTrace();
			request.setAttribute("error", "Les dates d'enchère doivent être renseignées");
			doGet(request, response);
		}
		catch(NumberFormatException e){
			e.printStackTrace();
			request.setAttribute("error", "Impossible de récupérer le numéro");
			doGet(request, response);
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
			doGet(request, response);
		}
		
		
	}

}
