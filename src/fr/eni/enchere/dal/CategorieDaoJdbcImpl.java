package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.Categorie;

import fr.eni.enchere.exception.CategorieException;

public class CategorieDaoJdbcImpl implements CategorieDao {
	
	private final String SELECT_ALL_CATEGORIES = "SELECT no_categorie, libelle FROM CATEGORIES";
	
	@Override
	public List<Categorie> selectAllCategories() throws CategorieException {
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
			throw new CategorieException();
		}
	
		return listeCategories;
	}

}
