package fr.eni.enchere.dal;

import java.io.IOException;
import java.time.LocalDate;
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
@WebServlet("/ModifArticleVenduServlet")
public class ModifArticleVenduServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ModifArticleVenduServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		ArticleVenduManager manager = new ArticleVenduManager();
		CategorieManager managerC = new CategorieManager();
		Integer noArticle = null;
		ArticleVendu article = null;
		List<Categorie> listeCategories = null;
		
		try {
			if(request.getSession().getAttribute("user") != null){
				noArticle = Integer.parseInt(request.getParameter("noArticle"));
				System.out.println(noArticle);
				
				listeCategories = managerC.recupererListeCategories();
				request.setAttribute("categories", listeCategories);
				
				article = manager.recupererArticleVendu(noArticle);
				request.setAttribute("article", article);			
			} else {
				throw new ArticleVenduException("Vous n'avez pas l'autorisation de vendre un article");
			}		
		} catch(Exception e) {
			request.setAttribute("error", e.getMessage());
			response.sendRedirect(request.getContextPath());
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/ajoutArticleVendu.jsp");
		rd.forward(request, response);
		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("user");
		String nomArticleVendu = request.getParameter("nom");
		String descriptionArticleVendu = request.getParameter("description");	
		LocalDate dateDebut = LocalDate.parse(request.getParameter("dateDebut"));
		LocalDate dateFin = LocalDate.parse(request.getParameter("dateFin"));
		int miseAPrix = Integer.parseInt(request.getParameter("miseAPrix"));
		String rue = request.getParameter("rue");
		String codePostal = request.getParameter("codePostal");
		String ville = request.getParameter("ville");
		int noCategorie = 0;
		int noArticle =0;
		
		try {
			noArticle = Integer.parseInt(request.getParameter("noArticle"));
			
			noCategorie = Integer.parseInt(request.getParameter("noCategorie"));
			
			Categorie c = new Categorie();
			c.setNoCategorie(noCategorie);
			
			Retrait r = new Retrait(rue,codePostal,ville);
			
			ArticleVendu av = new ArticleVendu(noArticle, nomArticleVendu, descriptionArticleVendu, dateDebut, dateFin, miseAPrix, c, r, utilisateur);
			ArticleVenduManager manager = new ArticleVenduManager();
			
			manager.modificationArticleVendu(utilisateur,av.getNoArticle());
					
		}catch(NumberFormatException e){
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		doGet(request, response);
	}

}
