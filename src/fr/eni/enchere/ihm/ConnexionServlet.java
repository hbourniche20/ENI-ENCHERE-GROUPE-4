package fr.eni.enchere.ihm;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
	private static final String COOKIE_FULLNAME_ID = "ENCHERE_userId";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(COOKIE_FULLNAME_ID)) {
                    request.setAttribute("userId", cookie.getValue());
                }
            }
        }
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
		String checkbox = request.getParameter("rememberMe");

		Utilisateur user = null;
		try {
			user = manager.authentification(email, password);
			request.getSession().setAttribute("user", user); // add to session
			if(checkbox != null) {
		        Cookie cookie = new Cookie(COOKIE_FULLNAME_ID, email);
		        response.addCookie(cookie);
			}
			response.sendRedirect(request.getContextPath());
		} catch (ConnexionException exception) {
			request.setAttribute("error", exception.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/connexion.jsp");
			rd.forward(request, response);
		}
		
	}

}
