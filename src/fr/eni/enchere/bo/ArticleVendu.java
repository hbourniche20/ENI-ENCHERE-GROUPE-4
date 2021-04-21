package fr.eni.enchere.bo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArticleVendu {
	private int noArticle;
	private String nomArticle;
	private String description;
	private LocalDate dateDebutEncheres;
	private LocalDate dateFinEncheres;
	private int miseAPrix;
	private int prixVente;
	private boolean etatVente;
	private Categorie categorieArticle;
	private Retrait lieuRetrait;
	private Utilisateur vendeur;
	private List<Enchere> encheres;
	
	
	
	public ArticleVendu() {
		this.etatVente = false;
		this.encheres = new ArrayList<>();
	}

	public ArticleVendu(String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres,int miseAPrix, int prixVente, boolean etatVente, 
			Categorie categorieArticle, Retrait lieuRetrait, Utilisateur vendeur) {
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.etatVente = etatVente;
		this.categorieArticle = categorieArticle;
		this.lieuRetrait = lieuRetrait;
		this.vendeur = vendeur;
	}

	public ArticleVendu(int noArticle, String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int miseAPrix, int prixVente, boolean etatVente, 
			Categorie categorieArticle, Retrait lieuRetrait, Utilisateur vendeur) {
		this.nomArticle = nomArticle;
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.etatVente = etatVente;
		this.categorieArticle = categorieArticle;
		this.lieuRetrait = lieuRetrait;
		this.vendeur = vendeur;
	}

	
	
	public int getNoArticle() {
		return noArticle;
	}
	
	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}

	public String getNomArticle() {
		return nomArticle;
	}
	
	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDateDebutEncheres() {
		return dateDebutEncheres;
	}
	
	public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}

	public LocalDate getDateFinEncheres() {
		return dateFinEncheres;
	}
	
	public void setDateFinEncheres(LocalDate dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}

	public int getMiseAPrix() {
		return miseAPrix;
	}
	
	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}

	public int getPrixVente() {
		return prixVente;
	}
	
	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	public boolean isEtatVente() {
		return etatVente;
	}
	
	public void setEtatVente(boolean etatVente) {
		this.etatVente = etatVente;
	}

	public Categorie getCategorieArticle() {
		return categorieArticle;
	}
	
	public void setCategorieArticle(Categorie categorieArticle) {
		this.categorieArticle = categorieArticle;
	}

	public Retrait getLieuRetrait() {
		return lieuRetrait;
	}
	
	public void setLieuRetrait(Retrait lieuRetrait) {
		this.lieuRetrait = lieuRetrait;
	}

	public Utilisateur getVendeur() {
		return vendeur;
	}
	
	public void setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
	}

	public List<Enchere> getEncheres() {
		return encheres;
	}

	public void setEncheres(List<Enchere> encheres) {
		this.encheres = encheres;
	}
	
}
