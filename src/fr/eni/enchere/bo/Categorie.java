package fr.eni.enchere.bo;

import java.util.ArrayList;
import java.util.List;

public class Categorie {
	private int noCategorie;
	private String libelle;
	private List<ArticleVendu> articles;
	
	
	
	public Categorie() {
		this.articles = new ArrayList<>();
	}

	public Categorie(String libelle) {
		this.libelle = libelle;
	}

	public Categorie(int noCategorie, String libelle) {
		this.noCategorie = noCategorie;
		this.libelle = libelle;
	}

	
	
	public int getNoCategorie() {
		return noCategorie;
	}
	
	public void setNoCategorie(int noCategorie) {
		this.noCategorie = noCategorie;
	}

	public String getLibelle() {
		return libelle;
	}
	
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public List<ArticleVendu> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticleVendu> articles) {
		this.articles = articles;
	}
	
}
