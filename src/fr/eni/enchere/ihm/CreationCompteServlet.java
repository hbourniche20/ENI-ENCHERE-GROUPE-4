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
@WebServlet("/CreationCompteServlet")
public class CreationCompteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd =request.getRequestDispatcher("WEB-INF/jsp/creationCompte.jsp");
		rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pseudo = request.getParameter("pseudo");
		request.setAttribute("pseudo", pseudo);
		String nom = request.getParameter("nom");
		request.setAttribute("nom", nom);
		String prenom = request.getParameter("prenom");
		request.setAttribute("prenom", prenom);
		String email = request.getParameter("email");
		request.setAttribute("email", email);
		String telephone = request.getParameter("telephone");
		request.setAttribute("telephone", telephone);
		String rue = request.getParameter("rue");
		request.setAttribute("rue", rue);
		String codepostal = request.getParameter("codepostal");
		request.setAttribute("codepostal", codepostal);
		String ville = request.getParameter("ville");
		request.setAttribute("ville", ville);
		String motdepasse = request.getParameter("motdepasse");
		String confirmationmdp = request.getParameter("confirmationmdp");


		if (confirmationmdp.equals(motdepasse) ) {
			
			Utilisateur u = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, ville, codepostal, motdepasse);
			
			UtilisateurManager manager = new UtilisateurManager();
			try {
				manager.enregistrer(u);
			} catch (EmailNotUniqueException e){
				this.throwException(request, response, e.getMessage());
			} catch (PseudoNotUniqueException e) {
				this.throwException(request, response, e.getMessage());
			} catch (WrongInputException e) {
				this.throwException(request, response, e.getMessage());
			}
			
			response.sendRedirect("index");
		}
		else {
			System.out.println("La confirmation du mot de passe doit être identique au mot de passe");
		}
		
		
	}
	
	private void throwException(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
		request.setAttribute("error", message);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/creationCompte.jsp");
		rd.forward(request, response);
	}
		
}

