package fr.eni.enchere.test;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected static int index = 0;
	protected static String finalMessage = "";

	protected static void printNewTest(String testName) {
		System.out.println("===================================");
		System.out.println("TEST " + ((index++) + 1) + " : " + testName);
	}
	
	protected static void printTestFail(String message) {
		System.out.println("Test raté: " + message);
		finalMessage += "\ntest numéro " + index + " raté.\n";
	}
	
	protected static void printFirstMessage() {
		System.out.println("Vérifiez bien d'avoir initialisé la base de données avec le jeu de donnée mis à disposition dans le projet");
	}
	
	protected static void printLastMessage(HttpServletResponse response) throws IOException {
		if(finalMessage.equals("")) {
			finalMessage = "Les " + index + " tests sont passés.";
		}
		response.getWriter().append(finalMessage);
	}
}
