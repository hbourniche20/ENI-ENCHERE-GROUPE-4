package fr.eni.enchere.bo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.exception.ArticleVenduException;

public class ArticleVendu {
	private int noArticle;
	private String nomArticle;
	private String description;
	private LocalDate dateDebutEncheres;
	private LocalDate dateFinEncheres;
	private int miseAPrix;
	private int prixVente;
	private String etatVente;
	private Categorie categorieArticle;
	private Retrait lieuRetrait;
	private Utilisateur vendeur;
	private List<Enchere> encheres;
	
	
	
	public ArticleVendu() {
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
		this.categorieArticle = categorieArticle;
		this.lieuRetrait = lieuRetrait;
		this.vendeur = vendeur;
	}
	
	public ArticleVendu(String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres,int miseAPrix)  {
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		
	}

		
	public ArticleVendu(int noArticle, String nomArticle, String description, LocalDate dateDebutEncheres,
			LocalDate dateFinEncheres, int miseAPrix, Categorie categorieArticle, Retrait lieuRetrait,
			Utilisateur vendeur) {
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.categorieArticle = categorieArticle;
		this.lieuRetrait = lieuRetrait;
		this.vendeur = vendeur;
	}

	public ArticleVendu(String nomArticleVendu, String descriptionArticleVendu, LocalDate dateDebut, LocalDate dateFin,
			int miseAPrix2, Categorie c, Utilisateur u, Retrait r) {
		this.nomArticle = nomArticleVendu;
		this.description = descriptionArticleVendu;
		this.dateDebutEncheres = dateDebut;
		this.dateFinEncheres = dateFin;
		this.miseAPrix = miseAPrix2;
		this.categorieArticle = c;
		this.vendeur = u;
		this.lieuRetrait = r;
		
	}
	
	public ArticleVendu(String nomArticleVendu, String descriptionArticleVendu, LocalDate dateDebut, LocalDate dateFin,
			int miseAPrix2, Categorie c, Utilisateur u) {
		// TODO Auto-generated constructor stub
		this.nomArticle = nomArticleVendu;
		this.description = descriptionArticleVendu;
		this.dateDebutEncheres = dateDebut;
		this.dateFinEncheres = dateFin;
		this.miseAPrix = miseAPrix2;
		this.categorieArticle = c;
		this.vendeur = u;
		
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

	public String getEtatVente() {
		return etatVente;
	}
	
	public void setEtatVente(String etatVente) {
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
