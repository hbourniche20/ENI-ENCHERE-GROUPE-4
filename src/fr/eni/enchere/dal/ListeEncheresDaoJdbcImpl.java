package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.ArticleVendu;
import fr.eni.enchere.bo.Categorie;

public class ListeEncheresDaoJdbcImpl implements ListeEncheresDao {

	private final String SELECT_ALL_CATEGORIES = "SELECT no_categorie, libelle FROM CATEGORIES";
	private final String SELECT_ALL_ARTICLES = "SELECT no_article, nom_article, date_fin_encheres, prix_initial, no_utilisateur, pseudo FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES.no_utilisateur = UTILISATEURS.no_utilisateur";

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
			throw new Exception("Impossible de récupérer les listes de catégories");
		}

				
		return listeCategories;
	}

	@Override
	public List<ArticleVendu> selectAllArticles() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
				
				


}
