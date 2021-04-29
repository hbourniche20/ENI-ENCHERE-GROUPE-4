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
import fr.eni.enchere.exception.UtilisateurException;
import fr.eni.enchere.exception.WrongInputException;
import fr.eni.enchere.util.TextInputUtil;

/**
 * Servlet implementation class UpdateProfileServlet
 */
@WebServlet("/updateProfile")
public class UpdateProfileServlet extends UtilisateurServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd =request.getRequestDispatcher("WEB-INF/jsp/creationCompte.jsp");
		Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("user");
		request.setAttribute("utilisateur", utilisateur);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pseudo = "", nom = "", prenom = "", email = "", telephone = "", rue = "", 
				codepostal = "", ville = "", nouveauMotdePasse = "", motdepasse = "", confirmationmdp = "";
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
			// Current password of the user id .
			motdepasse = TextInputUtil.getSafeParameter(request, "motdepasse");
			// New password correspond to the newly created password.
			confirmationmdp = TextInputUtil.getSafeParameter(request, "confirmationmdp");
		} catch (WrongInputException e1) {
			this.throwException(request, response, e1.getMessage());
			return;
		}

		Utilisateur currentUtilisateur = (Utilisateur) request.getSession().getAttribute("user");
		Utilisateur u = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codepostal, ville, currentUtilisateur.getMotDePasse());
		request.setAttribute("utilisateur", u);

		if(!motdepasse.equals(currentUtilisateur.getMotDePasse())) {
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
				manager.enregistrer(u, confirmationmdp);
				request.getSession().setAttribute("user", u); // update session
				response.sendRedirect("index");
			} catch (EmailNotUniqueException e){
				this.throwException(request, response, e.getMessage());
			} catch (PseudoNotUniqueException e) {
				this.throwException(request, response, e.getMessage());
			} catch (UtilisateurException e) {
				this.throwException(request, response, e.getMessage());
			}
		}
		else {
			this.throwException(request, response, "La confirmation du mot de passe doit être identique au mot de passe");
		}
	}

}
