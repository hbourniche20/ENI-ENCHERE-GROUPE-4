package fr.eni.enchere.ihm;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.CategorieManager;
import fr.eni.enchere.bll.EnchereManager;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Utilisateur;

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
		EnchereManager manager = new EnchereManager();
		CategorieManager managerCategorie = new CategorieManager();
		
		try {

			List<Categorie> listeCategories = managerCategorie.recupererListeCategories();
			List<ArticleVendu> listeArticles = manager.recupererListeArticles();
	
			request.setAttribute("categories", listeCategories);
			request.setAttribute("articles", listeArticles);
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EnchereManager manager = new EnchereManager();
		CategorieManager managerCategorie = new CategorieManager();
		String nomArticle = null;
		String encheresOuvertes = null;
		String mesEncheres = null;
		String mesEncheresRemportees = null;
		String ventesEnCours = null;
		String ventesNonDebutees = null;
		String ventesTerminees = null;
		Integer noCategorie = 0;
		List<ArticleVendu> listeArticles = null;
		List<Categorie> listeCategories = null;
		
		
		try {
			nomArticle = request.getParameter("article");
			
			if(!request.getParameter("noCategorie").equals("")) {
				try {
					noCategorie = Integer.parseInt(request.getParameter("noCategorie"));
				} catch(NumberFormatException e) {
					throw new Exception("Numéro catégorie inconnu");
				}
			} 

			listeCategories = managerCategorie.recupererListeCategories();

			if(request.getSession().getAttribute("user") != null) {
				if(request.getParameter("encheres").equals("achats")) {
					encheresOuvertes = request.getParameter("encheresOuvertes");
					mesEncheres = request.getParameter("mesEncheres");
					mesEncheresRemportees = request.getParameter("mesEncheresRemportees");
					
				} else if(request.getParameter("encheres").equals("ventes")) {
					ventesEnCours = request.getParameter("ventesEnCours");
					ventesNonDebutees =  request.getParameter("ventesNonDebutees");
					ventesTerminees = request.getParameter("ventesTerminees");
				} else {
					throw new Exception("Filtre 'Achats / Ventes' inconnu");
				}
				
				Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("user");
				listeArticles = manager.recupererListeArticlesAvecFiltresAdditionnels(utilisateur, nomArticle, noCategorie, encheresOuvertes, mesEncheres, mesEncheresRemportees, ventesEnCours, ventesNonDebutees, ventesTerminees);
			} else {
				listeArticles = manager.recupererListeArticlesAvecFiltres(nomArticle, noCategorie);
			}
			
			request.setAttribute("categories", listeCategories);
			request.setAttribute("articles", listeArticles);
			
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
		rd.forward(request, response);
	}

}
