package fr.eni.enchere.ihm;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.EncheresManager;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;

/**
 * Servlet implementation class ListeEncheresServlet
 */
@WebServlet("/index")
public class ListeEncheresServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EncheresManager manager = new EncheresManager();
		
		try {

			List<Categorie> listeCategories = manager.recupererListeCategories();
			List<ArticleVendu> listeArticles = manager.recupererListeArticles();
	
			request.setAttribute("categories", listeCategories);
			request.setAttribute("articles", listeArticles);
		} catch (Exception e) {
			//request.setAttribute("error", e.getMessage());
		}
		
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EncheresManager manager = new EncheresManager();
		String article = null;
		Integer noCategorie = 0;
		
		try {
			article = request.getParameter("article");
			if(!request.getParameter("article").equals("")) {
				article = request.getParameter("article");
			}
			if(!request.getParameter("noCategorie").equals("")) {
				try {
					noCategorie = Integer.parseInt(request.getParameter("noCategorie"));
				} catch(NumberFormatException e) {
					noCategorie = 0;
				}
			} 

			List<Categorie> listeCategories = manager.recupererListeCategories();

			List<ArticleVendu> listeArticles = manager.recupererListeArticlesAvecFiltres(article, noCategorie);
	
			request.setAttribute("categories", listeCategories);
			request.setAttribute("articles", listeArticles);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			System.out.println(e);
			//request.setAttribute("error", e.getMessage());
		}
	}

}
