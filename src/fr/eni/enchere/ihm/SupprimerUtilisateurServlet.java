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
 * Servlet implementation class DeleteUserServlet
 */
@WebServlet("/utilisateur/supprimerUtilisateur")
public class SupprimerUtilisateurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UtilisateurManager manager = new UtilisateurManager();
		try {
			manager.supprimer((Utilisateur) request.getSession().getAttribute("user"));
			request.getSession().setAttribute("user", null);
			response.sendRedirect("index");
		} catch (UtilisateurNotFoundException e) {
			request.setAttribute("error", e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/creationCompte.jsp");
			rd.forward(request, response);
		}
	}
}
