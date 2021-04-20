package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.Utilisateur;

public class UtilisateurDaoJdbcImpl implements UtilisateurDao {

	private final String INSERT = "INSERT INTO UTILISATEURS(PSEUDO,NOM,PRENOM,EMAIL,TELEPHONE,RUE,CODE_POSTAL,VILLE,MOT_DE_PASSE,CREDIT,ADMINISTRATEUR) VALUES (?,?,?,?,?,?,?,?,?,0,FALSE)";
	private final String SELECT_PSEUDO = "SELECT PSEUDO FROM UTILISATEURS WHERE PSEUDO =?";
	private final String SELECT_MAIL = "SELECT EMAIL FROM UTILISATEURS WHERE EMAIL =?";
	
	public boolean verifPseudo(String pseudo) {
		 boolean x =true;
		
		try(Connection c = ConnectionProvider.getConnection()){
			PreparedStatement pstt = c.prepareStatement(SELECT_PSEUDO);
			
			pstt.setString(1, pseudo);
			ResultSet rs = pstt.executeQuery();
			
			if(rs.next()) {
				 
			x = false;
							
			}}catch(Exception e) {
				e.printStackTrace();
			}
				
		return x;
	}
	
	public boolean verifEmail(String mail) {
		
		boolean x = true;
		
		try(Connection c = ConnectionProvider.getConnection()){
			PreparedStatement pstt = c.prepareStatement(SELECT_MAIL);
			
			pstt.setString(1, mail);
			ResultSet rs = pstt.executeQuery();
			
			if(rs.next()) {
				 
				x = false;
								
				}}catch(Exception e) {
					e.printStackTrace();
				}
					
			return x; 
		
	}
	@Override
	public void addUtilisateur(Utilisateur u) throws Exception {
		// TODO Auto-generated method stub
		
		
		if(!verifPseudo(u.getPseudo()))  {
			throw new Exception("Le pseudo existe déjà!");
		}
		
		if(!verifPseudo(u.getEmail())) {
			throw new Exception("L'adresse mail existe déjà");
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
	
	

}
