package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;
import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.bo.Utilisateur;

public class ArticleVenduDaoJdbcImpl implements ArticleVenduDao {
	
	private final String INSERT = "INSERT INTO ARTICLES_VENDUS(NOM_ARTICLE,DESCRIPTION,DATE_DEBUT_ENCHERES,DATE_FIN_ENCHERES,PRIX_INITIAL,NO_CATEGORIE,NO_UTILISATEUR,NO_RETRAIT) VALUES (?,?,?,?,?,?,?,?)";
	private final String SELECT_ALL_CATEGORIES = "SELECT no_categorie, libelle FROM CATEGORIES";
//	private final String SELECT_ALL_RETRAITS = "SELECT RUE,CODE_POSTAL,VILLE FROM RETRAITS ";
	private final String SELECT_LOCATION = "SELECT RUE,CODE_POSTAL,VILLE FROM UTILISATEURS WHERE PSEUDO =?";
	private final String INSERT_RETRAIT = "INSERT INTO RETRAITS(RUE,CODE_POSTAL,VILLE) VALUES (?,?,?)  ";
	
	
	@Override
	public List<Categorie> selectAllCategories() throws Exception {
		List<Categorie> listeCategories = new ArrayList<>();

		try(Connection con = ConnectionProvider.getConnection()) {
			Statement st  = con.createStatement();
			ResultSet rs = st.executeQuery(SELECT_ALL_CATEGORIES);

			while(rs.next()) {
				Categorie c = new Categorie();
				c.setNoCategorie(rs.getInt(1));
				c.setLibelle(rs.getString(2));
				
				listeCategories.add(c);
			}
			rs.close();
			st.close();
		} catch(SQLException e) {
			throw new Exception("Impossible de récupérer la liste de catégories");
		}
	
		return listeCategories;
	}
	
	
	
	

	
	@Override
	public void addArticleVendu(ArticleVendu a) throws Exception {
		// TODO Auto-generated method stub
		
		try(Connection c = ConnectionProvider.getConnection()){
			try {
			c.setAutoCommit(false);
			
			PreparedStatement pstt = c.prepareStatement(INSERT_RETRAIT,PreparedStatement.RETURN_GENERATED_KEYS);
		
			
			pstt.setString(1, a.getLieuRetrait().getRue());
			pstt.setString(2, a.getLieuRetrait().getCodePostal());
			pstt.setString(3, a.getLieuRetrait().getVille());
			
			pstt.executeUpdate();
			
			ResultSet rs = pstt.getGeneratedKeys();
			
			if(rs.next()){
			
			a.getLieuRetrait().setNoRetrait(rs.getInt(1));
			
			}
			rs.close();
			pstt.close();
			
			pstt = c.prepareStatement(INSERT);
			
			pstt.setString(1, a.getNomArticle());
			pstt.setString(2, a.getDescription());
			pstt.setDate(3, Date.valueOf(a.getDateDebutEncheres()));
			pstt.setDate(4, Date.valueOf(a.getDateFinEncheres()));
			pstt.setInt(5, a.getMiseAPrix());
			pstt.setInt(6, a.getCategorieArticle().getNoCategorie());
			pstt.setInt(7, a.getVendeur().getNoUtilisateur());
			pstt.setInt(8, a.getLieuRetrait().getNoRetrait());
			
			
			pstt.executeUpdate();
			pstt.close();
			
			c.commit();
			}catch (Exception e ){
				c.rollback();
				throw e;
				
			}
		}catch(SQLException s) {
			s.printStackTrace();
		}
		
		
		
	}



	
	

	

}
