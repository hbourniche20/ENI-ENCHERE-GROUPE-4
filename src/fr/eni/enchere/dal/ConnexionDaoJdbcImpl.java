package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exception.ConnexionException;

public class ConnexionDaoJdbcImpl implements ConnexionDao {

	private static final String GET_USER = "SELECT (no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mmot_de_passe, credit, administrateur) FROM utilisateurs WHERE (email = ? OR pseudo = ?) AND mot_de_passe = ?";
	@Override
	public Utilisateur getSession(String id, String password) throws ConnexionException {
		Utilisateur user = null;
		try(Connection  con = ConnectionProvider.getConnection()) {
			PreparedStatement ps = con.prepareStatement(GET_USER);
			ps.setString(1, id);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				int noUtilisateur = rs.getInt(1);
				String pseudo = rs.getString(2);
				String nom = rs.getString(3);
				String prenom = rs.getString(4);
				String email = rs.getString(5);
				String telephone = rs.getString(6);
				String rue = rs.getString(7);
				String ville = rs.getString(8);
				String motDePasse = rs.getString(9);
				int credit = rs.getInt(10);
				boolean administrateur = rs.getBoolean(11);
				
				user = new Utilisateur(noUtilisateur, pseudo, nom, prenom, email, telephone, rue, ville, motDePasse, credit, administrateur);
			} else {
				throw new ConnexionException();
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return user;
	}

	
}
