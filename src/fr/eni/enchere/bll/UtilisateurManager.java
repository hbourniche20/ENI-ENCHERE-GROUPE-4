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
	
	public void enregistrer(Utilisateur u) throws PseudoNotUniqueException, EmailNotUniqueException, WrongInputException {
		System.out.println(u.toString());
		if(u.getPseudo().equals("")) {
			System.out.println("exception");
			throw new WrongInputException("Le pseudo est obligatoire");
		}
		
		if(u.getNom().equals("")) {
			System.out.println("exception");
			throw new WrongInputException("Le nom est obligatoire");
		}
		
		if(u.getPrenom().equals("")) {
			System.out.println("exception");
			throw new WrongInputException("Le prenom est obligatoire");
		}
		
		if(u.getEmail().equals("")) {
			System.out.println("exception");
			throw new WrongInputException("L'email est obligatoire");
		}
		
		if(u.getRue().equals("")) {
			System.out.println("exception");
			throw new WrongInputException("La rue est obligatoire");
		}
		
		if(u.getCodePostal().equals("")) {
			System.out.println("exception");
			throw new WrongInputException("Le code postal est obligatoire");
		}
		
		if(u.getVille().equals("")) {
			System.out.println("exception");
			throw new WrongInputException("La ville est obligatoire");
		} 
		
		
		if(u.getMotDePasse().equals("")) {
			System.out.println("exception");
			throw new WrongInputException("Le mot de passe est obligatoire");
		}
		

		if(u.getCredit() < 0) {
			System.out.println("exception");
			throw new WrongInputException("Le crédit doit être supérieur à 0");
		}
		
		System.out.println("mais je l'ajoute quand même.");
		 dao.addUtilisateur(u);
		
	}

	public Utilisateur recuperer(String pseudo) throws UtilisateurNotFoundException {
		return this.dao.getUtilisateur(pseudo);
	}
}
