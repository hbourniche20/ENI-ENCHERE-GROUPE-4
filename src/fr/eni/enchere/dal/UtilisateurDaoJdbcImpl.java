package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exception.EnchereException;
import fr.eni.enchere.exception.UtilisateurException;
import fr.eni.enchere.exception.UtilisateurNotFoundException;

public class UtilisateurDaoJdbcImpl implements UtilisateurDao {

	private final String INSERT = "INSERT INTO UTILISATEURS(PSEUDO,NOM,PRENOM,EMAIL,TELEPHONE,RUE,CODE_POSTAL,VILLE,MOT_DE_PASSE,CREDIT,ADMINISTRATEUR) VALUES (?,?,?,?,?,?,?,?,?,0,FALSE)";
	private final String SELECT_PSEUDO = "SELECT PSEUDO FROM UTILISATEURS WHERE PSEUDO =? AND NO_UTILISATEUR != ?";
	private final String SELECT_MAIL = "SELECT EMAIL FROM UTILISATEURS WHERE EMAIL =? AND NO_UTILISATEUR != ?";
	private final String GET_USER = "SELECT no_utilisateur, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM utilisateurs WHERE pseudo = ?";
	private final String UPDATE_USER = "UPDATE utilisateurs SET pseudo= ?, nom = ?, prenom = ?, email = ?, telephone = ? , rue = ?, code_postal = ?, ville = ?, mot_de_passe = ? WHERE no_utilisateur = ?";
	private final String UPDATE_CREDIT = "UPDATE UTILISATEURS SET credit = ? WHERE no_utilisateur = ?";
	private final String DELETE_USER = "DELETE FROM utilisateurs WHERE no_utilisateur = ?";

	public boolean verifPseudo(String pseudo, int idUser) {
		boolean isOk = true;
		
		try(Connection c = ConnectionProvider.getConnection()){
			PreparedStatement pstt = c.prepareStatement(SELECT_PSEUDO);
			
			pstt.setString(1, pseudo);
			pstt.setInt(2, idUser);
			ResultSet rs = pstt.executeQuery();
			
			if(rs.next()) {
				System.out.println("exist");
				 System.out.println(rs);
			isOk = false;
							
			}}catch(Exception e) {
				e.printStackTrace();
			}
				
		return isOk;
	}
	
	public boolean verifEmail(String mail, int idUser) {
		
		boolean isOk = true;
		
		try(Connection c = ConnectionProvider.getConnection()){
			PreparedStatement pstt = c.prepareStatement(SELECT_MAIL);
			
			pstt.setString(1, mail);
			pstt.setInt(2, idUser);
			ResultSet rs = pstt.executeQuery();
			
			if(rs.next()) {
				isOk = false;	
			}}catch(Exception e) {
				e.printStackTrace();
			}	
		return isOk; 
	}
	@Override
	public void saveUtilisateur(Utilisateur u) throws UtilisateurException {

		if(!verifPseudo(u.getPseudo(), u.getNoUtilisateur()))  {
			throw new UtilisateurException(UtilisateurException.USER_PSEUDO_NOT_UNIQUE);
		}
		
		if(!verifEmail(u.getEmail(), u.getNoUtilisateur())) {
			throw new UtilisateurException(UtilisateurException.USER_MAIL_NOT_UNIQUE);
		}
		try(Connection c = ConnectionProvider.getConnection()){
			String request = INSERT;
			// Update data when user already exist in database.
			if(u.getNoUtilisateur() != 0) {
				request = UPDATE_USER;
			}

			PreparedStatement pstt = c.prepareStatement(request, PreparedStatement.RETURN_GENERATED_KEYS);
			pstt.setString(1, u.getPseudo());
			pstt.setString(2, u.getNom());
			pstt.setString(3, u.getPrenom());
			pstt.setString(4, u.getEmail());
			pstt.setString(5, u.getTelephone());
			pstt.setString(6, u.getRue());
			pstt.setString(7, u.getCodePostal());
			pstt.setString(8, u.getVille());
			pstt.setString(9, u.getMotDePasse());
			if(u.getNoUtilisateur() != 0) {
				pstt.setInt(10, u.getNoUtilisateur());
			}
			pstt.executeUpdate();
			
			if(u.getNoUtilisateur() == 0) {
				ResultSet rs = pstt.getGeneratedKeys();
				if(rs.next()) {
					u.setNoUtilisateur(rs.getInt(1));
				}
			}
		}	catch(SQLException s) {
			s.printStackTrace();
		}
	}

	@Override
	public Utilisateur getUtilisateur(String pseudo) throws UtilisateurNotFoundException {
		Utilisateur user = null;
		
		try(Connection c = ConnectionProvider.getConnection()){
			PreparedStatement pstt = c.prepareStatement(GET_USER);
			pstt.setString(1, pseudo);
			ResultSet rs = pstt.executeQuery();
			if(rs.next()) {
				Integer noUtilisateur = rs.getInt(1);
				String nom = rs.getString(2);
				String prenom = rs.getString(3);
				String email = rs.getString(4);
				String telephone = rs.getString(5);
				String rue = rs.getString(6);
				String codePostal = rs.getString(7);
				String ville = rs.getString(8);
				String motDePasse = rs.getString(9);
				int credit = rs.getInt(10);
				boolean administrateur = rs.getBoolean(11);
				user = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, credit, administrateur);
				user.setNoUtilisateur(noUtilisateur);
			} else {
				throw new UtilisateurNotFoundException();
			}
		} catch (SQLException s) {
			s.printStackTrace();
		}
		return user;
	}
	
	@Override
	public void updateCredit(Connection con, Integer noUtilisateur, Integer credit) throws SQLException {
		try {
			PreparedStatement st = con.prepareStatement(UPDATE_CREDIT);
			st.setInt(1, credit);
			st.setInt(2, noUtilisateur);
			st.executeUpdate();
			st.close();
		} catch(SQLException e) {
			throw e;
		}	
		
	}

	@Override
	public void deleteUtilisateur(Utilisateur u) throws UtilisateurNotFoundException {
		
		try (Connection c = ConnectionProvider.getConnection()) {
			PreparedStatement pstt = c.prepareStatement(DELETE_USER);
			
			pstt.setInt(1, u.getNoUtilisateur());
			

			System.out.println("User: " + u.getNoUtilisateur());
			int response = pstt.executeUpdate();
			if(response == 0) {
				throw new UtilisateurNotFoundException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UtilisateurNotFoundException();
		}
	}


}
