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
import fr.eni.enchere.exception.UtilisateurNotFoundException;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/utilisateur/profil")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UtilisateurManager manager = new UtilisateurManager();
		Utilisateur user = null;
		try {
			user = manager.recuperer(request.getParameter("pseudo"));
			request.setAttribute("user", user);
		} catch (UtilisateurNotFoundException e) {
			request.setAttribute("error", e.getMessage());
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp");
		rd.forward(request, response);
	}
}
