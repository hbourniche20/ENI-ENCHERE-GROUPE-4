package fr.eni.enchere.ihm;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.ArticleVenduManager;
import fr.eni.enchere.bll.CategorieManager;
import fr.eni.enchere.bll.EnchereManager;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exception.ArticleVenduException;
import fr.eni.enchere.util.TextInputUtil;

/**
 * Servlet implementation class AjoutArticleVenduServlet
 */
@WebServlet("/articles/ajouterArticle")
public class AjoutArticleVenduServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CategorieManager managerCategorie = new CategorieManager();
		List<Categorie> listeCategories = null;
		
		try {
			listeCategories = managerCategorie.recupererListeCategories();				
			request.setAttribute("categories", listeCategories);
				
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/ajoutArticleVendu.jsp");
			rd.forward(request, response);
		} catch(Exception e) {
			request.setAttribute("error", e.getMessage());
			response.sendRedirect(request.getContextPath());
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		int nocategorie = 0;
		
		try {	
			Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("user");
			String nomArticleVendu = TextInputUtil.getSafeParameter(request, "nom");
			String descriptionArticleVendu = TextInputUtil.getSafeParameter(request, "description");
			
			String rue = TextInputUtil.getSafeParameter(request, "rue");
			String codePostal = TextInputUtil.getSafeParameter(request, "codePostal");
			String ville = TextInputUtil.getSafeParameter(request, "ville");
			LocalDate dateDebut = LocalDate.parse(request.getParameter("dateDebut"));
			LocalDate dateFin = LocalDate.parse(request.getParameter("dateFin"));
			nocategorie = Integer.parseInt(request.getParameter("noCategorie"));
			int miseAPrix = Integer.parseInt(request.getParameter("miseAPrix"));
			Categorie c = new Categorie();
			c.setNoCategorie(nocategorie);
			Retrait r = new Retrait(rue, codePostal, ville);
			ArticleVendu a = new ArticleVendu(nomArticleVendu,descriptionArticleVendu,dateDebut,dateFin,miseAPrix,c,utilisateur,r);
			ArticleVenduManager manager = new ArticleVenduManager();
			manager.enregistrerArticleVendu(a);
			request.getSession().setAttribute("success", "L'article a été ajouté");
			response.sendRedirect(request.getContextPath());			
		} catch(DateTimeParseException e) {
			e.printStackTrace();
			request.setAttribute("error", "Les dates d'enchère doivent être renseignées");
			doGet(request, response);
		} catch(NumberFormatException e) {
			e.printStackTrace();	
			request.setAttribute("error", "La catégorie n'est pas définie");			
			doGet(request, response);

		} catch(Exception e ) {
			request.setAttribute("error", e.getMessage());
			doGet(request, response);
		}
	}

}
