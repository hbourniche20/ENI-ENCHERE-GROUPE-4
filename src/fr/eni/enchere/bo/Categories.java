package fr.eni.enchere.bo;

public class Categories {
	
	private int no_categorie;
	private String libelle;
	
	public Categories() {
		
	}

	public Categories(String libelle) {
		
		this.libelle = libelle;
	}

	public Categories(int no_categorie, String libelle) {
		
		this.no_categorie = no_categorie;
		this.libelle = libelle;
	}

	public int getNo_categorie() {
		return no_categorie;
	}

	public void setNo_categorie(int no_categorie) {
		this.no_categorie = no_categorie;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	
	
	
}
