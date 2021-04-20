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

/**
 * Servlet implementation class CreationCompteServlet
 */
@WebServlet("/CreationCompteServlet")
public class CreationCompteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public CreationCompteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd =request.getRequestDispatcher("WEB-INF/jsp/creationCompte.jsp");
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
		String motdepasse = request.getParameter("motdepasse");
		String confirmationmdp = request.getParameter("confirmationmdp");
		System.out.println(motdepasse);
		System.out.println(confirmationmdp);

		if (confirmationmdp.equals(motdepasse) ) {
			
			Utilisateur u = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, ville, codepostal, motdepasse);
			
			UtilisateurManager manager = new UtilisateurManager();
			
				{
				try {
					manager.enregistrer(u);
				} catch (Exception e){
					e.printStackTrace();
				}
				response.sendRedirect("index");
					
				}
				
		}
		else {
			System.out.println("La confirmation du mot de passe doit être identique au mot de passe");
		}
		
		
	}
		
}

