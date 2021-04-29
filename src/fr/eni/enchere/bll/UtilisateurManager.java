package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.DaoFactory;
import fr.eni.enchere.dal.UtilisateurDao;
import fr.eni.enchere.exception.UtilisateurException;
import fr.eni.enchere.exception.UtilisateurNotFoundException;

public class UtilisateurManager {

	private UtilisateurDao dao;
	
	public UtilisateurManager() {
		dao = DaoFactory.getUtilisateurDao();
	}
	
	public void enregistrer(Utilisateur utilisateur, String confirmPassword) throws UtilisateurException {
		if(this.hasValidInformations(utilisateur)) {
			if(!confirmPassword.equals(utilisateur.getMotDePasse())) {
				throw new UtilisateurException(UtilisateurException.USER_CONFIRM_PASSWORD);
			}
			dao.saveUtilisateur(utilisateur);
		}
	}
	
	public void updateUser(Utilisateur utilisateur, String confirmPassword, String newPassword, String confirmNewPassword) throws UtilisateurException{
		if(!confirmPassword.equals(utilisateur.getMotDePasse())) {
			throw new UtilisateurException(UtilisateurException.USER_WRONG_PASSWORD);
		}
		
		if(!newPassword.equals("")) {
			utilisateur.setMotDePasse(newPassword);
		} else {
			confirmNewPassword = confirmPassword;
		}
		
		this.enregistrer(utilisateur, confirmNewPassword);
	}

	public Utilisateur recuperer(String pseudo) throws UtilisateurNotFoundException {
		return this.dao.getUtilisateur(pseudo);
	}
	
	public void supprimer(Utilisateur u) throws UtilisateurException {
		this.dao.deleteUtilisateur(u);
	}
	
	public boolean hasValidInformations(Utilisateur utilisateur) throws UtilisateurException {

		if(utilisateur.getPseudo().equals("")) {
			throw new UtilisateurException(UtilisateurException.USER_EMPTY_FIELD);
		}

		if(utilisateur.getNom().equals("")) {
			throw new UtilisateurException(UtilisateurException.USER_EMPTY_FIELD);
		}

		if(utilisateur.getPrenom().equals("")) {
			throw new UtilisateurException(UtilisateurException.USER_EMPTY_FIELD);
		}

		if(utilisateur.getEmail().equals("")) {
			throw new UtilisateurException(UtilisateurException.USER_EMPTY_FIELD);
		}
		
		if(utilisateur.getRue().equals("")) {
			throw new UtilisateurException(UtilisateurException.USER_EMPTY_FIELD);
		}

		if(utilisateur.getTelephone().equals("")) {
			throw new UtilisateurException(UtilisateurException.USER_EMPTY_FIELD);
		}

		if(utilisateur.getCodePostal().equals("")) {
			throw new UtilisateurException(UtilisateurException.USER_EMPTY_FIELD);
		}

		if(utilisateur.getVille().equals("")) {
			throw new UtilisateurException(UtilisateurException.USER_EMPTY_FIELD);
		}

		// Verify e-mail format
		String email = utilisateur.getEmail();
		if(!email.contains("@") && email.split("@").length != 2 
				&& !email.split("@")[1].contains(".") && email.split("@")[1].split(".").length != 2) {
			throw new UtilisateurException(UtilisateurException.USER_MAIL_WRONG_FORMAT);
		}

		// Verify phone number format (we are using the French format here)
		if((utilisateur.getTelephone().length() == 12 && utilisateur.getTelephone().toCharArray()[0] == '+')) {
			checkNumbers(utilisateur.getTelephone().substring(1), UtilisateurException.USER_INVALID_PHONE_CHAR);
		} else if (utilisateur.getTelephone().length() == 10) {
			checkNumbers(utilisateur.getTelephone(), UtilisateurException.USER_INVALID_PHONE_CHAR);
		} else {
			throw new UtilisateurException(UtilisateurException.USER_WRONG_PHONE_FORMAT);
		}

		// Verify postal code format (we are using the French format here)
		if(utilisateur.getCodePostal().length() == 5) {
			checkNumbers(utilisateur.getCodePostal(), UtilisateurException.USER_INVALID_POSTCODE_CHAR);
		} else {
			throw new UtilisateurException(UtilisateurException.USER_WRONG_POSTCODE_FORMAT);
		}
		
		if(utilisateur.getCredit() < 0) {
			throw new UtilisateurException(UtilisateurException.USER_NEGATIVE_CREDITS);
		}
		
		return true;
	}

	public void checkNumbers(String toCheck, String errorMessage) throws UtilisateurException {
		try {
			for(char character: toCheck.toCharArray()) {
				Integer.parseInt(character + "");
			}
		} catch(NumberFormatException e) {
			throw new UtilisateurException(errorMessage);
		}
	}
}
