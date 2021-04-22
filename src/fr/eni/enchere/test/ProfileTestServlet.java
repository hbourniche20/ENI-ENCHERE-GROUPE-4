package fr.eni.enchere.test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.ConnexionManager;
import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exception.ConnexionException;
import fr.eni.enchere.exception.UtilisateurNotFoundException;

/**
 * Servlet implementation class ProfileTestServlet
 */
@WebServlet("/servlet/fr.eni.enchere.test.ProfileTestServlet")
public class ProfileTestServlet extends TestServlet {
	private static final long serialVersionUID = 1L;

	private static UtilisateurManager manager;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		manager = new UtilisateurManager();
		printFirstMessage();
		test1GetUserSuccess();
		test2GetUserFail();
		printLastMessage(response);
	}

	private static void test1GetUserSuccess() {
		printNewTest("Recuperer le profil d'un utilisateur existant");
		Utilisateur user = null;
		try {
			user = manager.recuperer("Dupond85");
		} catch (UtilisateurNotFoundException e) {
			printTestFail(e.getMessage());
			return;
		}
		if(user == null) {
			printTestFail("Utilisateur null");
		} else {
			System.out.println("Test réussi: le profil a été affiché.");
		}
	}
	
	private static void test2GetUserFail() {
		printNewTest("Recuperer le profil d'un utilisateur existant");
		try {
			manager.recuperer("NightSunrise");
		} catch (UtilisateurNotFoundException e) {
			System.out.println("Test réussi: l'utilisateur n'existe pas dans la db.");
			return;
		}
		printTestFail("NightSunrise existe dans la db");
	}
}
