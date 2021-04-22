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
@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp");
		UtilisateurManager manager = new UtilisateurManager();
		Utilisateur user = null;
		try {
			user = manager.recuperer(request.getParameter("pseudo"));
			request.setAttribute("user", user);
		} catch (UtilisateurNotFoundException e) {
			request.setAttribute("error", e.getMessage());
		}
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("user");
		
		
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String telephone = request.getParameter("telephone");
		String rue = request.getParameter("rue");
		String codePostal = request.getParameter("codepostal");
		String ville = request.getParameter("ville");
		
		utilisateur.setNom(nom);
		utilisateur.setPrenom(prenom);
		utilisateur.setTelephone(telephone);
		utilisateur.setRue(rue);
		utilisateur.setCodePostal(codePostal);
		utilisateur.setVille(ville);
		

	}
}
