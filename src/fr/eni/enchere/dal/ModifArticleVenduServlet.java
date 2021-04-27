package fr.eni.enchere.dal;

import java.io.IOException;
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
		doGet(request, response);
	}

}
