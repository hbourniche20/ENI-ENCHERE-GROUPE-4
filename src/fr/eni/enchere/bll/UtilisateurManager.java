package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.DaoFactory;
import fr.eni.enchere.dal.UtilisateurDao;
import fr.eni.enchere.exception.EmailNotUniqueException;
import fr.eni.enchere.exception.WrongInputException;
import fr.eni.enchere.exception.PseudoNotUniqueException;
import fr.eni.enchere.exception.UtilisateurNotFoundException;

public class UtilisateurManager {

	private UtilisateurDao dao;
	
	public UtilisateurManager() {
		dao = DaoFactory.getUtilisateurDao();
	}
	
	public void enregistrer(Utilisateur utilisateur) throws PseudoNotUniqueException, EmailNotUniqueException, WrongInputException {
		if(utilisateur.hasValidInformations()) {
			dao.addUtilisateur(utilisateur);
		}
	}

	public void modifier(Utilisateur utilisateur) throws UtilisateurNotFoundException, WrongInputException {
		if(utilisateur.hasValidInformations()) {
			dao.updateUtilisateur(utilisateur);
		}
	}
	
	public Utilisateur recuperer(String pseudo) throws UtilisateurNotFoundException {
		return this.dao.getUtilisateur(pseudo);
	}
}
