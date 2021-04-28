package fr.eni.enchere.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.bo.Categorie;

import fr.eni.enchere.exception.CategorieException;

public class CategorieDaoJdbcImpl implements CategorieDao {
	
	private final String SELECT_ALL_CATEGORIES = "SELECT no_categorie, libelle FROM CATEGORIES";
	private final String SELECT_OTHER_CATEGORIES ="SELECT no_categorie, libelle FROM CATEGORIES WHERE no_categorie != ?";
	
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

	@Override
	public List<Categorie> selectOtherCategories(int noCategorie) throws CategorieException {
		// TODO Auto-generated method stub
		List<Categorie> listeCategories = new ArrayList<>();
		try(Connection con = ConnectionProvider.getConnection()) {
			PreparedStatement pstt  = con.prepareStatement(SELECT_OTHER_CATEGORIES);
			pstt.setInt(1, noCategorie);
			ResultSet rs = pstt.executeQuery();

			while(rs.next()) {
				Categorie c = new Categorie();
				c.setNoCategorie(rs.getInt(1));
				c.setLibelle(rs.getString(2));
				
				listeCategories.add(c);
			}
			rs.close();
			pstt.close();
		} catch(SQLException e) {
			throw new CategorieException();
		}
	
		return listeCategories;
		
	}

}
