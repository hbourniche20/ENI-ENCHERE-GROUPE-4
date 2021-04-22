package fr.eni.enchere.ihm;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.ArticleVenduManager;
import fr.eni.enchere.bll.EncheresManager;
import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.bo.Utilisateur;

/**
 * Servlet implementation class AjoutArticleVenduServlet
 */
@WebServlet("/AjoutArticleVenduServlet")
public class AjoutArticleVenduServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public AjoutArticleVenduServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArticleVenduManager manager = new ArticleVenduManager();
		
		try {

			List<Categorie> listeCategories = manager.recupererListeCategories();
			//List<Retrait> listeRetraits = manager.recupererListeRetraits();
	
			request.setAttribute("categories", listeCategories);
			//request.setAttribute("retraits", listeRetraits);
			
		} catch (Exception e) {
			//request.setAttribute("error", e.getMessage());
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/ajoutArticleVendu.jsp");
		rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("user");
		String nomArticleVendu = request.getParameter("nom");
		String descriptionArticleVendu = request.getParameter("description");	
		LocalDate dateDebut = LocalDate.parse(request.getParameter("dateDebut"));
		LocalDate dateFin = LocalDate.parse(request.getParameter("dateFin"));
		int miseAPrix = request.getIntHeader("miseAPrix");
		String rue = request.getParameter("rue");
		String codePostal = request.getParameter("codePostal");
		String ville = request.getParameter("ville");
	    int noRetrait = 0;
		int nocategorie = 0;
		
		try {
			try {
				 nocategorie = Integer.parseInt(request.getParameter("noCategorie"));
				// noRetrait = Integer.parseInt(request.getParameter("noRetrait"));
			}catch(NumberFormatException e) {
				e.printStackTrace();
			}
			
			Categorie c = new Categorie();
			c.setNoCategorie(nocategorie);
			Retrait r = new Retrait();
			r.setNoRetrait(noRetrait);
			
			r = new Retrait(rue, codePostal, ville);
			ArticleVendu a = new ArticleVendu(nomArticleVendu,descriptionArticleVendu,dateDebut,dateFin,miseAPrix,c,utilisateur,r);
			
			ArticleVenduManager manager = new ArticleVenduManager();
			manager.enregistrerArticleVendu(a);
			response.sendRedirect("index");
		}catch(Exception e ) {
			
			e.printStackTrace();
		}
		
	}

}
