package fr.eni.enchere.ihm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.ArticleVenduManager;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exception.ArticleVenduException;

/**
 * Servlet implementation class SuppressionArticleVenduServlet
 */
@WebServlet("/SuppressionArticleServlet")
public class SuppressionArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArticleVenduManager manager = new ArticleVenduManager();
		Integer noArticle = 0;
		try {
			if(request.getSession().getAttribute("user") != null) {
				Utilisateur vendeur = (Utilisateur) request.getSession().getAttribute("user");
				noArticle = Integer.parseInt(request.getParameter("noArticle"));
				manager.supprimerArticleVendu(vendeur, noArticle);
			} else {
				throw new ArticleVenduException(ArticleVenduException.USER_FORBIDDEN);
			}
			request.getSession().setAttribute("success", "L'article a été supprimé avec succès");
			
		} catch(NumberFormatException e) {
			request.getSession().setAttribute("error", "Le numéro article doit être un nombre entier");
			
		} catch(Exception e) {
			request.getSession().setAttribute("error", e.getMessage());
		}
		response.sendRedirect(request.getContextPath());
	}

}
