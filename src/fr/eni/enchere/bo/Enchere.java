package fr.eni.enchere.bo;

import java.time.LocalDateTime;

public class Enchere {
	
	private LocalDateTime dateEnchere;
	private int montantEnchere;
	private Utilisateur encherisseur;
	private ArticleVendu article;
	
	
	
	public Enchere() {
		
	}

	public Enchere(LocalDateTime dateEnchere, int montantEnchere, Utilisateur encherisseur, ArticleVendu article) {
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.encherisseur = encherisseur;
		this.article = article;
	}

	
	
	public LocalDateTime getDateEnchere() {
		return dateEnchere;
	}
	
	public void setDateEnchere(LocalDateTime dateEnchere) {
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
