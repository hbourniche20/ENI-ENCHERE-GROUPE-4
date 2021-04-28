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
import fr.eni.enchere.exception.EmailNotUniqueException;
import fr.eni.enchere.exception.PseudoNotUniqueException;
import fr.eni.enchere.exception.WrongInputException;

/**
 * Servlet implementation class CreationCompteServlet
 */
@WebServlet("/enregistrerUtilisateur")
public class CreationCompteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd =request.getRequestDispatcher("WEB-INF/jsp/creationCompte.jsp");
		Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("user");
		if(utilisateur != null) {
			request.setAttribute("utilisateur", utilisateur);
		}
		rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pseudo = request.getParameter("pseudo");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String rue = request.getParameter("rue");
		String codepostal = request.getParameter("codepostal");
		String ville = request.getParameter("ville");

		// newly created password.
		String nouveauMotdePasse = request.getParameter("newmotdepasse");
		// Current password of the user id .
		String motdepasse = request.getParameter("motdepasse");
		// New password correspond to the newly created password.
		String confirmationmdp = request.getParameter("confirmationmdp");
		Utilisateur currentUtilisateur = (Utilisateur) request.getSession().getAttribute("user");
		Utilisateur u = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codepostal, ville, nouveauMotdePasse);
		request.setAttribute("utilisateur", u);

		if(currentUtilisateur != null && !motdepasse.equals(currentUtilisateur.getMotDePasse())) {
			this.throwException(request, response, "le mot de passe ne correspond pas");
		}

		if (confirmationmdp.equals(nouveauMotdePasse) || (currentUtilisateur != null && nouveauMotdePasse.equals(""))) {
			if(nouveauMotdePasse.equals("")) {
				u.setMotDePasse(motdepasse);
			}
			if(currentUtilisateur != null) {
				u.setNoUtilisateur(currentUtilisateur.getNoUtilisateur());
				u.setCredit(currentUtilisateur.getCredit());
			}
			UtilisateurManager manager = new UtilisateurManager();
			try {
				manager.enregistrer(u);
				System.out.println("attribute user updated in the session");
				request.getSession().setAttribute("user", u); // update session
				System.out.println("redirection");
				response.sendRedirect("index");
			} catch (EmailNotUniqueException e){
				this.throwException(request, response, e.getMessage());
			} catch (PseudoNotUniqueException e) {
				this.throwException(request, response, e.getMessage());
			} catch (WrongInputException e) {
				this.throwException(request, response, e.getMessage());
			}
		}
		else {
			this.throwException(request, response, "La confirmation du mot de passe doit être identique au mot de passe");
		}
	}
	
	private void throwException(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
		request.setAttribute("error", message);
		System.out.println("error ! " + message);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/creationCompte.jsp");
		rd.forward(request, response);
	}
		
}

