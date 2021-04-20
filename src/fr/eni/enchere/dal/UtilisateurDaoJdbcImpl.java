package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.eni.enchere.bo.Utilisateur;

public class UtilisateurDaoJdbcImpl implements UtilisateurDao {

	private final String INSERT = "INSERT INTO UTILISATEURS(PSEUDO,NOM,PRENOM,EMAIL,TELEPHONE,RUE,CODE_POSTAL,VILLE,MOT_DE_PASSE,CREDIT,ADMINISTRATEUR) VALUES (?,?,?,?,?,?,?,?,?,0,FALSE)";
	@Override
	public void addUtilisateur(Utilisateur u) {
		// TODO Auto-generated method stub
		
		try(Connection c = ConnectionProvider.getConnection()){
			PreparedStatement pstt = c.prepareStatement(INSERT);
			pstt.setString(1, u.getPseudo());
			pstt.setString(2, u.getNom());
			pstt.setString(3, u.getPrenom());
			pstt.setString(4, u.getEmail());
			pstt.setString(5, u.getTelephone());
			pstt.setString(6, u.getRue());
			pstt.setString(7, u.getCodePostal());
			pstt.setString(8, u.getVille());
			pstt.setString(9, u.getMotDePasse());
			
			pstt.executeUpdate();
			pstt.close();
		}catch(SQLException s) {
			s.printStackTrace();
			
		}
	}

}
