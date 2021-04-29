package fr.eni.enchere.test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.UtilisateurManager;
import fr.eni.enchere.bo.Utilisateur;
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
			test18UpdateEmptyPseudo();
			test19UpdateEmptyNom();
			test20UpdateEmptyPrenom();
			test21UpdateEmptyEmail();
			test22UpdateEmptyRue();
			test23UpdateEmptyVille();
			test24UpdateFailEmail1();
			test25UpdateFailEmail2();
			test26UpdateFailEmail3();
			test27UpdateFailEmail4();
			test28UpdateFailEmail5();
			test29UpdateFailEmail6();
			test30UpdateFailEmail7();
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
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getPrenom() == "Juan") {
				System.out.println("Test passé");
				// Revert modifications in the database.
				utilisateur.setPrenom("Jean");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			printTestFail(e.getMessage());
		}
	}

	private void test2UpdateSuccessSurname() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du nom avec succès");
		utilisateur.setNom("Dupont");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getNom() == "Dupont") {
				System.out.println("Test passé");
				// Revert modifications in the database.
				utilisateur.setNom("Dupond");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			printTestFail(e.getMessage());
		}
	}

	private void test3UpdateSuccessPseudo() {
		printNewTest("Faire la modification du pseudo avec succès");
		utilisateur.setPseudo("Dupond80");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond80");
			if (user.getPseudo() == "Dupond80") {
				System.out.println("Test passé");
				// Revert modifications in the database.
				utilisateur.setPseudo("Dupond85");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
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
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getEmail() == "dupond.juan@gmail.com") {
				System.out.println("Test passé");
				// Revert modifications in the database.
				utilisateur.setEmail("dupond.jean@gmail.com");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			printTestFail(e.getMessage());
		}
	}
	
	private void test5UpdateSuccessPhoneFormat1() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du numéro de téléphone au format 1 avec succès");
		utilisateur.setTelephone("0102030405");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getTelephone() == "0102030405") {
				System.out.println("Test passé");
				// Revert modifications in the database.
				utilisateur.setTelephone("0251478510");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			printTestFail(e.getMessage());
		}
	}
	
	private void test6UpdateSuccessPhoneFormat2() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du numéro de téléphone au format 2 avec succès");
		utilisateur.setTelephone("+33102030405");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getTelephone() == "+33102030405") {
				System.out.println("Test passé");
				// Revert modifications in the database.
				utilisateur.setTelephone("0251478510");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			printTestFail(e.getMessage());
		}
	}
	
	private void test7UpdateFailPhoneLongFormat1() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du numéro de téléphone trop long avec le format 1");
		utilisateur.setTelephone("+331020304058");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getTelephone() == "+331020304058") {
				printTestFail("Le numéro a été enregistré alors qu'il est trop long !");
				// Revert modifications in the database.
				utilisateur.setTelephone("0251478510");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			if(e.getMessage().equals(UtilisateurException.USER_WRONG_PHONE_FORMAT)) {
				System.out.println("Test passé");
				utilisateur.setTelephone("0251478510");
			} else {
				printTestFail(e.getMessage());
			}

		}
	}
	
	private void test8UpdateFailPhoneLongFormat2() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du numéro de téléphone trop long avec le format 2");
		utilisateur.setTelephone("010203040506");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getTelephone() == "010203040506") {
				printTestFail("Le numéro a été enregistré alors qu'il est trop long !");
				// Revert modifications in the database.
				utilisateur.setTelephone("0251478510");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			if(e.getMessage().equals(UtilisateurException.USER_WRONG_PHONE_FORMAT)) {
				System.out.println("Test passé");
				utilisateur.setTelephone("0251478510");
			} else {
				printTestFail(e.getMessage());
			}
		}
	}
	
	private void test9UpdateFailPhoneWrongChar() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du numéro de téléphone avec un caractère interdit");
		utilisateur.setTelephone("01020b0405");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getTelephone() == "01020b0405") {
				printTestFail("Le numéro a été enregistré contient des characters interdit !");
				// Revert modifications in the database.
				utilisateur.setTelephone("0251478510");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			if(e.getMessage().equals(UtilisateurException.USER_INVALID_PHONE_CHAR)) {
				System.out.println("Test passé");
				utilisateur.setTelephone("0251478510");
			} else {
				printTestFail(e.getMessage());
			}
		}
	}
	
	private void test10UpdateSuccessStreet() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification de la rue avec succès");
		utilisateur.setRue("15 rue de Flobert");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getRue() == "15 rue de Flobert") {
				System.out.println("Test passé");
				// Revert modifications in the database.
				utilisateur.setRue("152 rue Nationale");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			printTestFail(e.getMessage());
		}
	}
	
	private void test11UpdateSuccessPostcode() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du code postal avec succès");
		utilisateur.setCodePostal("44000");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getCodePostal() == "44000") {
				System.out.println("Test passé");
				// Revert modifications in the database.
				utilisateur.setCodePostal("85000");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			printTestFail(e.getMessage());
		}
	}
	
	private void test12UpdateFailPostcodeLong() throws UtilisateurNotFoundException {
			printNewTest("Faire la modification du code postal trop long");
			utilisateur.setCodePostal("445000");
			try {
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
				Utilisateur user = manager.recuperer("Dupond85");
				if (user.getCodePostal() == "445000") {
					printTestFail("Le code postal a été enregistré alors qu'il est trop long !");
					// Revert modifications in the database.
					utilisateur.setCodePostal("85000");
					manager.updateUser(utilisateur, utilisateur.getMotDePasse());
				}
			} catch (UtilisateurException e) {
				if(e.getMessage().equals(UtilisateurException.USER_WRONG_POSTCODE_FORMAT)) {
					System.out.println("Test passé");
					utilisateur.setCodePostal("85000");
				} else {
					printTestFail(e.getMessage());
				}
			}
	}
	
	private void test13UpdateFailPostcodeWrongChar() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du code postal avec un caractère interdit");
		utilisateur.setCodePostal("445O0");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getCodePostal() == "445O0") {
				printTestFail("Le code postal a été enregistré alors qu'il contient un caractère interdit !");
				// Revert modifications in the database.
				utilisateur.setCodePostal("85000");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			if(e.getMessage().equals(UtilisateurException.USER_INVALID_POSTCODE_CHAR)) {
				System.out.println("Test passé");
				utilisateur.setCodePostal("85000");
			} else {
				printTestFail(e.getMessage());
			}
		}
	}
	
	private void test14UpdateSuccessCity() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification de la ville avec succès");
		utilisateur.setVille("Nantes");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getVille() == "Nantes") {
				System.out.println("Test passé");
				// Revert modifications in the database.
				utilisateur.setVille("LA ROCHE-SUR-YON");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			printTestFail(e.getMessage());
		}
	}
	
	private void test15UpdateSuccessPassword() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du mot de passe avec succès");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse(), "qsdfgklm", "qsdfgklm");
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getMotDePasse() == "qsdfgklm") {
				System.out.println("Test passé");
				// Revert modifications in the database.
				manager.updateUser(utilisateur, utilisateur.getMotDePasse(), "azertyuiop", "azertyuiop");
			}
		} catch (UtilisateurException e) {
			printTestFail(e.getMessage());
		}
	}
	
	private void test16UpdateFailPseudo() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du pseudo avec un pseudo existant");
		utilisateur.setPseudo("Shadow79");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getPseudo() == "Shadow79") {
				printTestFail("Le changement est passé alors que le pseudo existe déjà");
				// Revert modifications in the database.
				utilisateur.setMotDePasse("azertyuiop");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			if(e.getMessage().equals(UtilisateurException.USER_PSEUDO_NOT_UNIQUE)) {
				System.out.println("Test passé");
				utilisateur.setPseudo("Dupond85");
			} else {
				printTestFail(e.getMessage());
			}
		}
	}
	
	private void test17UpdateFailEmail() throws UtilisateurNotFoundException {
		printNewTest("Faire la modification du mail avec un mail existant");
		utilisateur.setEmail("tatania.talmond@orange.fr");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond85");
			if (user.getEmail() == "tatania.talmond@orange.fr") {
				printTestFail("Le changement est passé alors que l'email existe déjà");
				// Revert modifications in the database.
				utilisateur.setMotDePasse("azertyuiop");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			if(e.getMessage().equals(UtilisateurException.USER_MAIL_NOT_UNIQUE)) {
				System.out.println("Test passé");
				utilisateur.setEmail("dupond.jean@gmail.com");
			} else {
				printTestFail(e.getMessage());
			}
		}
	}

	private void test18UpdateEmptyPseudo() {
		printNewTest("Faire la modification du pseudo avec un champs vide");
		utilisateur.setPseudo("");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond80");
			if (user.getPseudo() == "") {
				printTestFail("Le pseudo a été changé alors que le champs était vide !");
				// Revert modifications in the database.
				utilisateur.setPseudo("Dupond85");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			if(e.getMessage().equals(UtilisateurException.USER_EMPTY_FIELD)) {
				System.out.println("Test passé");
				utilisateur.setPseudo("Dupond85");
			} else {
				printTestFail(e.getMessage());
			}
		} catch (UtilisateurNotFoundException e) {
			printTestFail("Le pseudo n'a pas pu être changé");
		}
	}
	
	private void test19UpdateEmptyNom() {
		printNewTest("Faire la modification du nom avec un champs vide");
		utilisateur.setNom("");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond80");
			if (user.getNom() == "") {
				printTestFail("Le nom a été changé alors que le champs était vide !");
				// Revert modifications in the database.
				utilisateur.setNom("Dupond");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			if(e.getMessage().equals(UtilisateurException.USER_EMPTY_FIELD)) {
				System.out.println("Test passé");
				utilisateur.setNom("Dupond");
			} else {
				printTestFail(e.getMessage());
			}
		} catch (UtilisateurNotFoundException e) {
			printTestFail("Utilisateur Dupond80 introuvable.");
		}
	}

	private void test20UpdateEmptyPrenom() {
		printNewTest("Faire la modification du prenom avec un champs vide");
		utilisateur.setPrenom("");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond80");
			if (user.getPrenom() == "") {
				printTestFail("Le prenom a été changé alors que le champs était vide !");
				// Revert modifications in the database.
				utilisateur.setPrenom("Jean");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			if(e.getMessage().equals(UtilisateurException.USER_EMPTY_FIELD)) {
				System.out.println("Test passé");
				utilisateur.setPrenom("Jean");
			} else {
				printTestFail(e.getMessage());
			}
		} catch (UtilisateurNotFoundException e) {
			printTestFail("Utilisateur Dupond80 introuvable.");
		}
	}
	
	private void test21UpdateEmptyEmail() {
		printNewTest("Faire la modification du mail avec un champs vide");
		utilisateur.setEmail("");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond80");
			if (user.getEmail() == "") {
				printTestFail("Le mail a été changé alors que le champs était vide !");
				// Revert modifications in the database.
				utilisateur.setEmail("dupond.jean@gmail.com");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			if(e.getMessage().equals(UtilisateurException.USER_EMPTY_FIELD)) {
				System.out.println("Test passé");
				utilisateur.setEmail("dupond.jean@gmail.com");
			} else {
				printTestFail(e.getMessage());
			}
		} catch (UtilisateurNotFoundException e) {
			printTestFail("Utilisateur Dupond80 introuvable.");
		}
	}

	private void test22UpdateEmptyRue() {
		printNewTest("Faire la modification de la rue avec un champs vide");
		utilisateur.setRue("");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond80");
			if (user.getRue() == "") {
				printTestFail("La rue a été changé alors que le champs était vide !");
				// Revert modifications in the database.
				utilisateur.setRue("152 rue Nationale");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			if(e.getMessage().equals(UtilisateurException.USER_EMPTY_FIELD)) {
				System.out.println("Test passé");
				utilisateur.setRue("152 rue Nationale");
			} else {
				printTestFail(e.getMessage());
			}
		} catch (UtilisateurNotFoundException e) {
			printTestFail("Utilisateur Dupond80 introuvable.");
		}
	}

	private void test23UpdateEmptyVille() {
		printNewTest("Faire la modification de la ville avec un champs vide");
		utilisateur.setVille("");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond80");
			if (user.getVille() == "") {
				printTestFail("La ville a été changé alors que le champs était vide !");
				// Revert modifications in the database.
				utilisateur.setVille("LA ROCHE-SUR-YON");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			if(e.getMessage().equals(UtilisateurException.USER_EMPTY_FIELD)) {
				System.out.println("Test passé");
				utilisateur.setVille("LA ROCHE-SUR-YON");
			} else {
				printTestFail(e.getMessage());
			}
		} catch (UtilisateurNotFoundException e) {
			printTestFail("Utilisateur Dupond80 introuvable.");
		}
	}
	
	private void test24UpdateFailEmail1() {
		printNewTest("Faire la modification de l'email avec un mauvais format @gmail.com");
		utilisateur.setEmail("@gmail.com");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond80");
			if (user.getEmail() == "@gmail.com") {
				printTestFail("La ville a été changé alors que le champs était vide !");
				// Revert modifications in the database.
				utilisateur.setEmail("dupond.jean@gmail.com");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			if(e.getMessage().equals(UtilisateurException.USER_MAIL_WRONG_FORMAT)) {
				System.out.println("Test passé");
				utilisateur.setEmail("dupond.jean@gmail.com");
			} else {
				printTestFail(e.getMessage());
			}
		} catch (UtilisateurNotFoundException e) {
			printTestFail("Utilisateur Dupond80 introuvable.");
		}
	}
	
	private void test25UpdateFailEmail2() {
		printNewTest("Faire la modification de l'email avec un mauvais format oui@");
		utilisateur.setEmail("oui@");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond80");
			if (user.getEmail() == "oui@") {
				printTestFail("La ville a été changé alors que le champs était vide !");
				// Revert modifications in the database.
				utilisateur.setEmail("dupond.jean@gmail.com");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			if(e.getMessage().equals(UtilisateurException.USER_MAIL_WRONG_FORMAT)) {
				System.out.println("Test passé");
				utilisateur.setEmail("dupond.jean@gmail.com");
			} else {
				printTestFail(e.getMessage());
			}
		} catch (UtilisateurNotFoundException e) {
			printTestFail("Utilisateur Dupond80 introuvable.");
		}
	}
	
	private void test26UpdateFailEmail3() {
		printNewTest("Faire la modification de l'email avec un mauvais format oui@oui");
		utilisateur.setEmail("oui@oui");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond80");
			if (user.getEmail() == "oui@oui") {
				printTestFail("La ville a été changé alors que le champs était vide !");
				// Revert modifications in the database.
				utilisateur.setEmail("dupond.jean@gmail.com");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			if(e.getMessage().equals(UtilisateurException.USER_MAIL_WRONG_FORMAT)) {
				System.out.println("Test passé");
				utilisateur.setEmail("dupond.jean@gmail.com");
			} else {
				printTestFail(e.getMessage());
			}
		} catch (UtilisateurNotFoundException e) {
			printTestFail("Utilisateur Dupond80 introuvable.");
		}
	}
	
	private void test27UpdateFailEmail4() {
		printNewTest("Faire la modification de l'email avec un mauvais format oui@oui.");
		utilisateur.setEmail("oui@oui.");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond80");
			if (user.getEmail() == "oui@oui.") {
				printTestFail("La ville a été changé alors que le champs était vide !");
				// Revert modifications in the database.
				utilisateur.setEmail("dupond.jean@gmail.com");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			if(e.getMessage().equals(UtilisateurException.USER_MAIL_WRONG_FORMAT)) {
				System.out.println("Test passé");
				utilisateur.setEmail("dupond.jean@gmail.com");
			} else {
				printTestFail(e.getMessage());
			}
		} catch (UtilisateurNotFoundException e) {
			printTestFail("Utilisateur Dupond80 introuvable.");
		}
	}
	
	private void test28UpdateFailEmail5() {
		printNewTest("Faire la modification de l'email avec un mauvais format oui@.oui");
		utilisateur.setEmail("oui@.oui");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond80");
			if (user.getEmail() == "oui@.oui") {
				printTestFail("La ville a été changé alors que le champs était vide !");
				// Revert modifications in the database.
				utilisateur.setEmail("dupond.jean@gmail.com");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			if(e.getMessage().equals(UtilisateurException.USER_MAIL_WRONG_FORMAT)) {
				System.out.println("Test passé");
				utilisateur.setEmail("dupond.jean@gmail.com");
			} else {
				printTestFail(e.getMessage());
			}
		} catch (UtilisateurNotFoundException e) {
			printTestFail("Utilisateur Dupond80 introuvable.");
		}
	}
	
	private void test29UpdateFailEmail6() {
		printNewTest("Faire la modification de l'email avec un mauvais format @.");
		utilisateur.setEmail("@.");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond80");
			if (user.getEmail() == "@.") {
				printTestFail("La ville a été changé alors que le champs était vide !");
				// Revert modifications in the database.
				utilisateur.setEmail("dupond.jean@gmail.com");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			if(e.getMessage().equals(UtilisateurException.USER_MAIL_WRONG_FORMAT)) {
				System.out.println("Test passé");
				utilisateur.setEmail("dupond.jean@gmail.com");
			} else {
				printTestFail(e.getMessage());
			}
		} catch (UtilisateurNotFoundException e) {
			printTestFail("Utilisateur Dupond80 introuvable.");
		}
	}

	private void test30UpdateFailEmail7() {
		printNewTest("Faire la modification de l'email avec un mauvais format oui@.com");
		utilisateur.setEmail("oui@.com");
		try {
			manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			Utilisateur user = manager.recuperer("Dupond80");
			if (user.getEmail() == "oui@.com") {
				printTestFail("La ville a été changé alors que le champs était vide !");
				// Revert modifications in the database.
				utilisateur.setEmail("dupond.jean@gmail.com");
				manager.updateUser(utilisateur, utilisateur.getMotDePasse());
			}
		} catch (UtilisateurException e) {
			if(e.getMessage().equals(UtilisateurException.USER_MAIL_WRONG_FORMAT)) {
				System.out.println("Test passé");
				utilisateur.setEmail("dupond.jean@gmail.com");
			} else {
				printTestFail(e.getMessage());
			}
		} catch (UtilisateurNotFoundException e) {
			printTestFail("Utilisateur Dupond80 introuvable.");
		}
	}
}
