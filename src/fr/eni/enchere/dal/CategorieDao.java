package fr.eni.enchere.dal;

import java.util.List;

import fr.eni.enchere.bo.Categorie;

import fr.eni.enchere.exception.CategorieException;


public interface CategorieDao {

	List<Categorie> selectAllCategories() throws CategorieException;
	
	List<Categorie> selectOtherCategories(int noCategorie) throws CategorieException;
	
}
