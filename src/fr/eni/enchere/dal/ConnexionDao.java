package fr.eni.enchere.dal;


import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exception.ConnexionException;

public interface ConnexionDao {

	Utilisateur getSession(String id, String password) throws ConnexionException;
}
