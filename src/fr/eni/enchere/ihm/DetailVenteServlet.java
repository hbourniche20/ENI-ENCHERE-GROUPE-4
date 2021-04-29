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
import fr.eni.enchere.bll.EnchereManager;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exception.ArticleVenduException;

/**
 * Servlet implementation class DetailVenteServlet
 */
@WebServlet("/articles/visualiserArticle")
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
			request.setAttribute("error", request.getSession().getAttribute("error"));
			request.getSession().removeAttribute("error");	
			request.setAttribute("success", request.getSession().getAttribute("success"));
			request.getSession().removeAttribute("success");
			
			noArticle = Integer.parseInt(request.getParameter("noArticle"));
			article = manager.recupererArticleVendu(noArticle);
			if(article == null) {
				throw new ArticleVenduException(ArticleVenduException.ARTICLE_NOT_FOUND);
			}
			request.setAttribute("article", article);
				
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/visualisationArticleVendu.jsp");
			rd.forward(request, response);
		} catch(NumberFormatException e) {
			request.getSession().setAttribute("error", "Le numéro article doit être un nombre entier");
			response.sendRedirect(request.getContextPath());
		} catch(Exception e) {
			System.out.println(e.getMessage());
			request.getSession().setAttribute("error", e.getMessage());
			response.sendRedirect(request.getContextPath());
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		EnchereManager manager = new EnchereManager();
		Integer noArticle = 0;
		Integer montantEnchere = 0;
		try {
			if(request.getSession().getAttribute("user") != null) {
				Utilisateur encherisseur = (Utilisateur) request.getSession().getAttribute("user");
				try {
					noArticle = Integer.parseInt(request.getParameter("noArticle"));
				} catch(NumberFormatException e) {
					throw new Exception("Le numéro article doit être un nombre entier");
				}
				try {
					montantEnchere = Integer.parseInt(request.getParameter("montantEnchere"));
				} catch(NullPointerException e) {
					throw new Exception("Le montant d'une enchère est obligatoire");
				} catch(NumberFormatException e) {
					throw new Exception("Le montant doit être un nombre entier");
				}
				manager.ajouterEnchere(noArticle, encherisseur, montantEnchere);
				request.getSession().setAttribute("success", "Votre enchère sur cet article a été ajoutée");
			} else {
				throw new ArticleVenduException(ArticleVenduException.USER_FORBIDDEN);
			}
		} catch(Exception e) {
			request.getSession().setAttribute("error", e.getMessage());
		}
		request.setAttribute("noArticle", noArticle);
		response.sendRedirect(request.getContextPath() + "/articles/visualiserArticle?noArticle=" + noArticle);
	}

}
