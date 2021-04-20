package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.dal.DaoFactory;
import fr.eni.enchere.dal.UtilisateurDao;

public class UtilisateurManager {

	private UtilisateurDao dao;
	
	public UtilisateurManager() {
		dao = DaoFactory.getUtilisateurDao();
	}
	
	public void enregistrer(Utilisateur u) throws Exception{
		
		if(u.getPseudo() == null) {
			throw new Exception("Le pseudo est obligatoire");
		}
		
		if(u.getNom() == null) {
			throw new Exception("Le nom est obligatoire");
		}
		
		if(u.getPrenom() == null) {
			throw new Exception("Le prenom est obligatoire");
		}
		
		if(u.getEmail() == null) {
			throw new Exception("L'email est obligatoire");
		}
		
		if(u.getRue() == null) {
			throw new Exception("La rue est obligatoire");
		}
		
		if(u.getCodePostal() == null) {
			throw new Exception("Le code postal est obligatoire");
		}
		
		if(u.getVille() == null) {
			throw new Exception("La ville est obligatoire");
		} 
		
		
		if(u.getMotDePasse() == null) {
			throw new Exception("Le mot de passe est obligatoire");
		}
		

		if(u.getCredit()  < 0) {
			throw new Exception("Le crédit doit être supérieur à 0");
		}
		
		
		 dao.addUtilisateur(u);

		
		
	}
}
