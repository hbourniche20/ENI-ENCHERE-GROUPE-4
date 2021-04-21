package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.eni.enchere.bo.ArticleVendu;

public class ArticleVenduDaoJdbcImpl implements ArticleVenduDao {
	
	private final String INSERT = "INSERT INTO ARTICLES_VENDUS(NOM_ARTICLE,DESCRIPTION,DATE_DEBUT_ENCHERES,DATE_FIN_ENCHERES,PRIX_INITIAL) VALUES (?,?,?,?,?)";

	@Override
	public void addArticleVendu(ArticleVendu a) throws Exception {
		// TODO Auto-generated method stub
		
		try(Connection c = ConnectionProvider.getConnection()){
			
			PreparedStatement pstt = c.prepareStatement(INSERT);
			
			pstt.setString(1, a.getNomArticle());
			pstt.setString(2, a.getDescription());
			pstt.setDate(3, Date.valueOf(a.getDateDebutEncheres()));
			pstt.setDate(4, Date.valueOf(a.getDateFinEncheres()));
			pstt.setInt(5, a.getMiseAPrix());
			
			pstt.executeUpdate();
			pstt.close();
			
		}catch(SQLException s) {
			s.printStackTrace();
		}
		
	}

}
