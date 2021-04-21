package fr.eni.enchere.ihm;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.ArticleVenduManager;
import fr.eni.enchere.bo.ArticleVendu;

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
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/ajoutArticleVendu.jsp");
		rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String nomArticleVendu = request.getParameter("nom");
		String descriptionArticleVendu = request.getParameter("description");	
		LocalDate dateDebut = LocalDate.parse(request.getParameter("dateDebut"));
		LocalDate dateFin = LocalDate.parse(request.getParameter("dateFin"));
		int miseAPrix = request.getIntHeader("miseAPrix");
		
		ArticleVendu a = new ArticleVendu(nomArticleVendu,descriptionArticleVendu,dateDebut,dateFin,miseAPrix);
		
		ArticleVenduManager manager = new ArticleVenduManager();
		
		try {
			manager.enregistrerArticleVendu(a);
		}catch(Exception e ) {
			e.printStackTrace();
		}
		response.sendRedirect("index");
	}

}
