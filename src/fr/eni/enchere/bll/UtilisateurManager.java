package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.DaoFactory;
import fr.eni.enchere.dal.UtilisateurDao;
import fr.eni.enchere.exception.EmailNotUniqueException;
import fr.eni.enchere.exception.UtilisateurException;
import fr.eni.enchere.exception.PseudoNotUniqueException;
import fr.eni.enchere.exception.UtilisateurNotFoundException;

public class UtilisateurManager {

	private UtilisateurDao dao;
	
	public UtilisateurManager() {
		dao = DaoFactory.getUtilisateurDao();
	}
	
	public void enregistrer(Utilisateur utilisateur, String confirmPassword) throws PseudoNotUniqueException, EmailNotUniqueException, UtilisateurException {
		
		if(utilisateur.hasValidInformations()) {
			if(!confirmPassword.equals(utilisateur.getMotDePasse())) {
				throw new UtilisateurException(UtilisateurException.USER_CONFIRM_PASSWORD);
			}
			dao.saveUtilisateur(utilisateur);
		}
	}
	
	public void updateUser(Utilisateur utilisateur, String newPassword, String confirmNewPassword) {
		
	}

	public Utilisateur recuperer(String pseudo) throws UtilisateurNotFoundException {
		return this.dao.getUtilisateur(pseudo);
	}
	
	public void supprimer(Utilisateur u) throws UtilisateurNotFoundException {
		this.dao.deleteUtilisateur(u);
	}
}
