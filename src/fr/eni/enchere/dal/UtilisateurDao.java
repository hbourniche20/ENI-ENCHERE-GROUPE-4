package fr.eni.enchere.dal;


import java.sql.Connection;
import java.sql.SQLException;

import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exception.UtilisateurException;
import fr.eni.enchere.exception.UtilisateurNotFoundException;

public interface UtilisateurDao {

	void saveUtilisateur(Utilisateur u) throws UtilisateurException;
	
	Utilisateur getUtilisateur(String pseudo) throws UtilisateurNotFoundException;
	
	void deleteUtilisateur(Utilisateur u)  throws UtilisateurException;

	void updateCredit(Connection con, Integer noUtilisateur, Integer credit) throws SQLException;
}
