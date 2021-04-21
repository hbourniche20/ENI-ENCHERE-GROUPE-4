package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.Utilisateur;
import fr.eni.enchere.exception.EmailNotUniqueException;
import fr.eni.enchere.exception.PseudoNotUniqueException;
import fr.eni.enchere.exception.UtilisateurNotFoundException;

public class UtilisateurDaoJdbcImpl implements UtilisateurDao {

	private final String INSERT = "INSERT INTO UTILISATEURS(PSEUDO,NOM,PRENOM,EMAIL,TELEPHONE,RUE,CODE_POSTAL,VILLE,MOT_DE_PASSE,CREDIT,ADMINISTRATEUR) VALUES (?,?,?,?,?,?,?,?,?,0,FALSE)";
	private final String SELECT_PSEUDO = "SELECT PSEUDO FROM UTILISATEURS WHERE PSEUDO =?";
	private final String SELECT_MAIL = "SELECT EMAIL FROM UTILISATEURS WHERE EMAIL =?";
	private final String GET_USER = "SELECT nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM utilisateurs WHERE pseudo = ?";
	
	public boolean verifPseudo(String pseudo) {
		 boolean isOk = true;
		
		try(Connection c = ConnectionProvider.getConnection()){
			PreparedStatement pstt = c.prepareStatement(SELECT_PSEUDO);
			
			pstt.setString(1, pseudo);
			ResultSet rs = pstt.executeQuery();
			
			if(rs.next()) {
				 
			isOk = false;
							
			}}catch(Exception e) {
				e.printStackTrace();
			}
				
		return isOk;
	}
	
	public boolean verifEmail(String mail) {
		
		boolean isOk = true;
		
		try(Connection c = ConnectionProvider.getConnection()){
			PreparedStatement pstt = c.prepareStatement(SELECT_MAIL);
			
			pstt.setString(1, mail);
			ResultSet rs = pstt.executeQuery();
			
			if(rs.next()) {
				 
				isOk = false;
								
				}}catch(Exception e) {
					e.printStackTrace();
				}
					
			return isOk; 
		
	}
	@Override
	public void addUtilisateur(Utilisateur u) throws PseudoNotUniqueException, EmailNotUniqueException {
		
		if(!verifPseudo(u.getPseudo()))  {
			throw new PseudoNotUniqueException();
		}
		
		if(!verifPseudo(u.getEmail())) {
			throw new EmailNotUniqueException();
		}
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
				String nom = rs.getString(1);
				String prenom = rs.getString(2);
				String email = rs.getString(3);
				String telephone = rs.getString(4);
				String rue = rs.getString(5);
				String codePostal = rs.getString(6);
				String ville = rs.getString(7);
				String motDePasse = rs.getString(8);
				int credit = rs.getInt(9);
				boolean administrateur = rs.getBoolean(10);
				user = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, credit, administrateur);
			} else {
				throw new UtilisateurNotFoundException();
			}
		} catch (SQLException s) {
			s.printStackTrace();
		}
		return user;
	}
	
	

}
