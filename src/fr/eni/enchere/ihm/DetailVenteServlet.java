package fr.eni.enchere.ihm;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.ArticleVenduManager;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.exception.ArticleVenduException;

/**
 * Servlet implementation class DetailVenteServlet
 */
@WebServlet("/article")
public class DetailVenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArticleVenduManager manager = new ArticleVenduManager();
		ArticleVendu article = null;
		Integer noArticle = null;
		try {
			if(request.getSession().getAttribute("user") != null) {
				noArticle = Integer.parseInt(request.getParameter("noArticle"));
				article = manager.recupererArticleVendu(noArticle);
				request.setAttribute("article", article);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/articleVendu.jsp");
				rd.forward(request, response);
			} else {
				throw new ArticleVenduException("Vous n'avez pas l'autorisation d'acceder au détail d'un article");
			}
		} catch(NumberFormatException e) {
			request.setAttribute("error", "Le numéro article doit être un nombre entier");
			response.sendRedirect(request.getContextPath());
		} catch(Exception e) {
			request.setAttribute("error", e.getMessage());
			response.sendRedirect(request.getContextPath());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
