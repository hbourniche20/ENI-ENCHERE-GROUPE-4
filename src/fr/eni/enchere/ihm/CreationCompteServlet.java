package fr.eni.enchere.ihm;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exception.UtilisateurException;
import fr.eni.enchere.exception.WrongInputException;
import fr.eni.enchere.util.TextInputUtil;

/**
 * Servlet implementation class CreationCompteServlet
 */
@WebServlet("/creerCompte")
public class CreationCompteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd =request.getRequestDispatcher("/WEB-INF/jsp/creationCompte.jsp");
		rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pseudo, nom, prenom, email, telephone, rue, codepostal, ville, nouveauMotdePasse, confirmationmdp;
		try {
			pseudo = TextInputUtil.getSafeParameter(request, "pseudo");
			nom = TextInputUtil.getSafeParameter(request, "nom");
			prenom = TextInputUtil.getSafeParameter(request, "prenom");
			email = TextInputUtil.getSafeParameter(request, "email");
			telephone = TextInputUtil.getSafeParameter(request, "telephone");
			rue = TextInputUtil.getSafeParameter(request, "rue");
			codepostal = TextInputUtil.getSafeParameter(request, "codepostal");
			ville = TextInputUtil.getSafeParameter(request, "ville");
			// newly created password.
			nouveauMotdePasse = TextInputUtil.getSafeParameter(request, "newmotdepasse");
			// New password correspond to the newly created password.
			confirmationmdp = TextInputUtil.getSafeParameter(request, "confirmationmdp");
			
			Utilisateur u = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codepostal, ville, nouveauMotdePasse);
			request.setAttribute("utilisateur", u);
			UtilisateurManager manager = new UtilisateurManager();
		
			manager.enregistrer(u, confirmationmdp);
			request.getSession().setAttribute("user", u); // update session
			response.sendRedirect("index");
		} catch (UtilisateurException e) {
			this.throwException(request, response, e.getMessage());
		} catch (WrongInputException e1) {
			this.throwException(request, response, e1.getMessage());
		}
	}
	
	protected void throwException(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
		request.setAttribute("error", message);
		System.out.println("error ! " + message);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/creationCompte.jsp");
		rd.forward(request, response);
	}
}