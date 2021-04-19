package fr.eni.enchere.bo;

import java.util.Date;

public class Articles_Vendus {
	
	private int no_article;
	private String nom_article;
	private String description;
	private Date date_debut_encheres;
	private Date date_fin_encheres;
	private int prix_initial;
	private int prix_vente;
	
	public Articles_Vendus() {
	}

	public Articles_Vendus(String nom_article, String description, Date date_debut_encheres, Date date_fin_encheres,
			int prix_initial, int prix_vente) {
		super();
		this.nom_article = nom_article;
		this.description = description;
		this.date_debut_encheres = date_debut_encheres;
		this.date_fin_encheres = date_fin_encheres;
		this.prix_initial = prix_initial;
		this.prix_vente = prix_vente;
	}

	public Articles_Vendus(int no_article, String nom_article, String description, Date date_debut_encheres,
			Date date_fin_encheres, int prix_initial, int prix_vente) {
		super();
		this.no_article = no_article;
		this.nom_article = nom_article;
		this.description = description;
		this.date_debut_encheres = date_debut_encheres;
		this.date_fin_encheres = date_fin_encheres;
		this.prix_initial = prix_initial;
		this.prix_vente = prix_vente;
	}

	public int getNoArticle() {
		return no_article;
	}

	public void setNoArticle(int no_article) {
		this.no_article = no_article;
	}

	public String getNomArticle() {
		return nom_article;
	}

	public void setNomArticle(String nom_article) {
		this.nom_article = nom_article;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateDebutEncheres() {
		return date_debut_encheres;
	}

	public void setDateDebutEncheres(Date date_debut_encheres) {
		this.date_debut_encheres = date_debut_encheres;
	}

	public Date getDateFinEncheres() {
		return date_fin_encheres;
	}

	public void setDateFinEncheres(Date date_fin_encheres) {
		this.date_fin_encheres = date_fin_encheres;
	}

	public int getPrixInitial() {
		return prix_initial;
	}

	public void setPrixInitial(int prix_initial) {
		this.prix_initial = prix_initial;
	}

	public int getPrixVente() {
		return prix_vente;
	}

	public void setPrixVente(int prix_vente) {
		this.prix_vente = prix_vente;
	}
	
	
	
	

}
