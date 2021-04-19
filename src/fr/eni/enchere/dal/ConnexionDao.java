package fr.eni.enchere.dal;

import fr.eni.enchere.bo.Utilisateur;

public interface ConnexionDao {

	Utilisateur getSession() throws Exception;
}
