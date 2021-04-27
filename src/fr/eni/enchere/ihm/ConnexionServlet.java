package fr.eni.enchere.ihm;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.ConnexionManager;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exception.ConnexionException;

/**
 * Servlet implementation class ConnexionServlet
 */
@WebServlet("/seConnecter")
public class ConnexionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConnexionManager manager = new ConnexionManager();
		
		String email = request.getParameter("id");
		String password = request.getParameter("password");
		Utilisateur user = null;
		try {
			user = manager.authentification(email, password);
			request.getSession().setAttribute("user", user); // add to session
			response.sendRedirect(request.getContextPath());
		} catch (ConnexionException exception) {
			request.setAttribute("error", exception.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
			rd.forward(request, response);
		}
		
	}

}
