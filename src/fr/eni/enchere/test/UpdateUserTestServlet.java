package fr.eni.enchere.test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exception.EmailNotUniqueException;
import fr.eni.enchere.exception.PseudoNotUniqueException;
import fr.eni.enchere.exception.UtilisateurNotFoundException;
import fr.eni.enchere.exception.UtilisateurException;

/**
 * Servlet implementation class UpdateUserTestServlet
 */
@WebServlet("/servlet/fr.eni.enchere.test.UpdateUserTestServlet")
public class UpdateUserTestServlet extends TestServlet {
	private static final long serialVersionUID = 1L;
       
	private static UtilisateurManager manager;
	private static Utilisateur utilisateur;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		manager = new UtilisateurManager();
		try {
			initData();
			printFirstMessage();
			test1UpdateSuccessName();
			test2UpdateSuccessSurname();
			test3UpdateSuccessPseudo();
			test4UpdateSuccessEmail();
			test5UpdateSuccessPhoneFormat1();
			test6UpdateSuccessPhoneFormat2();
			test7UpdateFailPhoneLongFormat1();
			test8UpdateFailPhoneLongFormat2();
			test9UpdateFailPhoneWrongChar();
			test10UpdateSuccessStreet();
			test11UpdateSuccessPostcode();
			test12UpdateFailPostcodeLong();
			test13UpdateFailPostcodeWrongChar();
			test14UpdateSuccessCity();
			test15UpdateSuccessPassword();
			test16UpdateFailPseudo();
			test17UpdateFailEmail();
			printLastMessage(response);
		} catch (UtilisateurNotFoundException e) {
			response.getWriter().append("Vérifiez vos données dans la base de donnée.");
		}
	}
	private void initData() throws UtilisateurNotFoundException {
		utilisateur = manager.recuperer("Dupond85");// new Utilisateur(2, "Dupond85", "Dupond", "Jean", "dupond.jean@gmail.com", "0251478510", "152 rue Nationale", "85000", "LA ROCHE-SUR-YON", );
		System.out.println(utilisateur.toString());
	}

	private void test1UpdateSuccessName() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du prénom avec succès");
		utilisateur.setPrenom("Juan");
		try {
			manager.enregistrer(utilisateur);
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getPrenom() == "Juan") {
				System.out.println("Test passé");
				// Revert modifications in the database.
				utilisateur.setPrenom("Jean");
				manager.enregistrer(utilisateur);
			}
		} catch (PseudoNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (EmailNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (UtilisateurException e) {
			printTestFail(e.getMessage());
		}
	}

	private void test2UpdateSuccessSurname() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du nom avec succès");
		utilisateur.setNom("Dupont");
		try {
			manager.enregistrer(utilisateur);
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getNom() == "Dupont") {
				System.out.println("Test passé");
				// Revert modifications in the database.
				utilisateur.setNom("Dupond");
				manager.enregistrer(utilisateur);
			}
		} catch (PseudoNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (EmailNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (UtilisateurException e) {
			printTestFail(e.getMessage());
		}
	}

	private void test3UpdateSuccessPseudo() {
		printNewTest("Faire la modification du pseudo avec succès");
		utilisateur.setPseudo("Dupond80");
		try {
			manager.enregistrer(utilisateur);
			Utilisateur user = manager.recuperer("Dupond80");
			if (user.getPseudo() == "Dupond80") {
				System.out.println("Test passé");
				// Revert modifications in the database.
				utilisateur.setPseudo("Dupond85");
				manager.enregistrer(utilisateur);
			}
		} catch (PseudoNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (EmailNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (UtilisateurException e) {
			printTestFail(e.getMessage());
		} catch (UtilisateurNotFoundException e) {
			printTestFail("Le pseudo n'a pas pu être changé");
		}
	}

	private void test4UpdateSuccessEmail() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du mail avec succès");
		utilisateur.setEmail("dupond.juan@gmail.com");
		try {
			manager.enregistrer(utilisateur);
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getEmail() == "dupond.juan@gmail.com") {
				System.out.println("Test passé");
				// Revert modifications in the database.
				utilisateur.setEmail("dupond.jean@gmail.com");
				manager.enregistrer(utilisateur);
			}
		} catch (PseudoNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (EmailNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (UtilisateurException e) {
			printTestFail(e.getMessage());
		}
	}
	
	private void test5UpdateSuccessPhoneFormat1() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du numéro de téléphone au format 1 avec succès");
		utilisateur.setTelephone("0102030405");
		try {
			manager.enregistrer(utilisateur);
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getTelephone() == "0102030405") {
				System.out.println("Test passé");
				// Revert modifications in the database.
				utilisateur.setTelephone("0251478510");
				manager.enregistrer(utilisateur);
			}
		} catch (PseudoNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (EmailNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (UtilisateurException e) {
			printTestFail(e.getMessage());
		}
	}
	
	private void test6UpdateSuccessPhoneFormat2() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du numéro de téléphone au format 2 avec succès");
		utilisateur.setTelephone("+33102030405");
		try {
			manager.enregistrer(utilisateur);
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getTelephone() == "+33102030405") {
				System.out.println("Test passé");
				// Revert modifications in the database.
				utilisateur.setTelephone("0251478510");
				manager.enregistrer(utilisateur);
			}
		} catch (PseudoNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (EmailNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (UtilisateurException e) {
			printTestFail(e.getMessage());
		}
	}
	
	private void test7UpdateFailPhoneLongFormat1() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du numéro de téléphone trop long avec le format 1");
		utilisateur.setTelephone("+331020304058");
		try {
			manager.enregistrer(utilisateur);
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getTelephone() == "+331020304058") {
				printTestFail("Le numéro a été enregistré alors qu'il est trop long !");
				// Revert modifications in the database.
				utilisateur.setTelephone("0251478510");
				manager.enregistrer(utilisateur);
			}
		} catch (PseudoNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (EmailNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (UtilisateurException e) {
			System.out.println("Test passé");
			utilisateur.setTelephone("0251478510");
		}
	}
	
	private void test8UpdateFailPhoneLongFormat2() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du numéro de téléphone trop long avec le format 2");
		utilisateur.setTelephone("010203040506");
		try {
			manager.enregistrer(utilisateur);
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getTelephone() == "010203040506") {
				printTestFail("Le numéro a été enregistré alors qu'il est trop long !");
				// Revert modifications in the database.
				utilisateur.setTelephone("0251478510");
				manager.enregistrer(utilisateur);
			}
		} catch (PseudoNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (EmailNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (UtilisateurException e) {
			System.out.println("Test passé");
			utilisateur.setTelephone("0251478510");
		}
	}
	
	private void test9UpdateFailPhoneWrongChar() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du numéro de téléphone avec un caractère interdit");
		utilisateur.setTelephone("01020b0405");
		try {
			manager.enregistrer(utilisateur);
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getTelephone() == "01020b0405") {
				printTestFail("Le numéro a été enregistré contient des characters interdit !");
				// Revert modifications in the database.
				utilisateur.setTelephone("0251478510");
				manager.enregistrer(utilisateur);
			}
		} catch (PseudoNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (EmailNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (UtilisateurException e) {
			System.out.println("Test passé");
			utilisateur.setTelephone("0251478510");
		}
	}
	
	private void test10UpdateSuccessStreet() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification de la rue avec succès");
		utilisateur.setRue("15 rue de Flobert");
		try {
			manager.enregistrer(utilisateur);
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getRue() == "15 rue de Flobert") {
				System.out.println("Test passé");
				// Revert modifications in the database.
				utilisateur.setRue("152 rue Nationale");
				manager.enregistrer(utilisateur);
			}
		} catch (PseudoNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (EmailNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (UtilisateurException e) {
			printTestFail(e.getMessage());
		}
	}
	
	private void test11UpdateSuccessPostcode() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du code postal avec succès");
		utilisateur.setCodePostal("44000");
		try {
			manager.enregistrer(utilisateur);
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getCodePostal() == "44000") {
				System.out.println("Test passé");
				// Revert modifications in the database.
				utilisateur.setCodePostal("85000");
				manager.enregistrer(utilisateur);
			}
		} catch (PseudoNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (EmailNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (UtilisateurException e) {
			printTestFail(e.getMessage());
		}
	}
	
	private void test12UpdateFailPostcodeLong() throws UtilisateurNotFoundException {
			printNewTest("Faire la modification du code postal trop long");
			utilisateur.setCodePostal("445000");
			try {
				manager.enregistrer(utilisateur);
				Utilisateur user = manager.recuperer("Dupond85");
				if (user.getCodePostal() == "445000") {
					printTestFail("Le code postal a été enregistré alors qu'il est trop long !");
					// Revert modifications in the database.
					utilisateur.setCodePostal("85000");
					manager.enregistrer(utilisateur);
				}
			} catch (PseudoNotUniqueException e) {
				printTestFail(e.getMessage());
			} catch (EmailNotUniqueException e) {
				printTestFail(e.getMessage());
			} catch (UtilisateurException e) {
				System.out.println("Test passé");
				utilisateur.setCodePostal("85000");
			}
	}
	
	private void test13UpdateFailPostcodeWrongChar() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du code postal avec un caractère interdit");
		utilisateur.setCodePostal("445O0");
		try {
			manager.enregistrer(utilisateur);
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getCodePostal() == "445O0") {
				printTestFail("Le code postal a été enregistré alors qu'il contient un caractère interdit !");
				// Revert modifications in the database.
				utilisateur.setCodePostal("85000");
				manager.enregistrer(utilisateur);
			}
		} catch (PseudoNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (EmailNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (UtilisateurException e) {
			System.out.println("Test passé");
			utilisateur.setCodePostal("85000");
		}
	}
	
	private void test14UpdateSuccessCity() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification de la ville avec succès");
		utilisateur.setVille("Nantes");
		try {
			manager.enregistrer(utilisateur);
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getVille() == "Nantes") {
				System.out.println("Test passé");
				// Revert modifications in the database.
				utilisateur.setVille("LA ROCHE-SUR-YON");
				manager.enregistrer(utilisateur);
			}
		} catch (PseudoNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (EmailNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (UtilisateurException e) {
			printTestFail(e.getMessage());
		}
	}
	
	private void test15UpdateSuccessPassword() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du mot de passe avec succès");
		utilisateur.setMotDePasse("qsdfgklm");
		try {
			manager.enregistrer(utilisateur);
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getMotDePasse() == "qsdfgklm") {
				System.out.println("Test passé");
				// Revert modifications in the database.
				utilisateur.setMotDePasse("azertyuiop");
				manager.enregistrer(utilisateur);
			}
		} catch (PseudoNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (EmailNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (UtilisateurException e) {
			printTestFail(e.getMessage());
		}
	}
	
	private void test16UpdateFailPseudo() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du pseudo avec un pseudo existant");
		utilisateur.setPseudo("Shadow79");
		try {
			manager.enregistrer(utilisateur);
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getPseudo() == "Shadow79") {
				printTestFail("Le changement est passé alors que le pseudo existe déjà");
				// Revert modifications in the database.
				utilisateur.setMotDePasse("azertyuiop");
				manager.enregistrer(utilisateur);
			}
		} catch (PseudoNotUniqueException e) {
			System.out.println("Test passé");
			utilisateur.setPseudo("Dupond85");
		} catch (EmailNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (UtilisateurException e) {
			printTestFail(e.getMessage());
		}
	}
	
	private void test17UpdateFailEmail() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du mail avec un mail existant");
		utilisateur.setEmail("tatania.talmond@orange.fr");
		try {
			manager.enregistrer(utilisateur);
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getEmail() == "tatania.talmond@orange.fr") {
				printTestFail("Le changement est passé alors que l'email existe déjà");
				// Revert modifications in the database.
				utilisateur.setMotDePasse("azertyuiop");
				manager.enregistrer(utilisateur);
			}
		} catch (PseudoNotUniqueException e) {
			printTestFail(e.getMessage());
		} catch (EmailNotUniqueException e) {
			System.out.println("Test passé");
			utilisateur.setEmail("dupond.jean@gmail.com");
		} catch (UtilisateurException e) {
			printTestFail(e.getMessage());
		}
	}
}
