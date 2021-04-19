package fr.eni.enchere.bo;

import java.time.LocalDate;

public class Enchere {
	
	private int noEnchere;
	private LocalDate dateEnchere;
	private int montantEnchere;
	private Utilisateur encherisseur;
	private ArticleVendu article;
	
	
	public Enchere() {
		
	}

	public Enchere(LocalDate dateEnchere, int montantEnchere, Utilisateur encherisseur, ArticleVendu article) {
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.encherisseur = encherisseur;
		this.article = article;
	}

	public Enchere(int noEnchere, LocalDate dateEnchere, int montantEnchere, Utilisateur encherisseur, ArticleVendu article) {
		this.noEnchere = noEnchere;
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.encherisseur = encherisseur;
		this.article = article;
	}

	
	public int getNoEnchere() {
		return noEnchere;
	}
	public void setNoEnchere(int noEnchere) {
		this.noEnchere = noEnchere;
	}

	public LocalDate getDateEnchere() {
		return dateEnchere;
	}
	public void setDateEnchere(LocalDate dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getMontantEnchere() {
		return montantEnchere;
	}
	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	public Utilisateur getEncherisseur() {
		return encherisseur;
	}
	public void setEncherisseur(Utilisateur encherisseur) {
		this.encherisseur = encherisseur;
	}

	public ArticleVendu getArticle() {
		return article;
	}
	public void setArticle(ArticleVendu article) {
		this.article = article;
	}
	
}
