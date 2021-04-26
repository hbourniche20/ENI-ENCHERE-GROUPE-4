package fr.eni.enchere.bo;

import java.util.ArrayList;
import java.util.List;

import fr.eni.enchere.exception.WrongInputException;

public class Utilisateur {
	
	private int noUtilisateur;
	private String pseudo;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private String rue;
	private String codePostal;
	private String ville;
	private String motDePasse;
	private int credit = 0;
	private boolean administrateur;
	private List<ArticleVendu> articles;
	private List<Enchere> encheres;

	
	public Utilisateur() {
		this.articles = new ArrayList<>();
		this.encheres = new ArrayList<>();
	}

	public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue, String codePostal, String ville, 
			String motDePasse) {
		this();
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.motDePasse = motDePasse;
	}
	
	public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue, String codePostal, String ville, 
			String motDePasse, int credit, boolean administrateur) {
		this(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse);
		this.credit = credit;
		this.administrateur = administrateur;
	}
	
	public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone, String rue, String codePostal, 
			String ville, String motDePasse, int credit, boolean administrateur) {
		this(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, credit, administrateur);
		this.noUtilisateur = noUtilisateur;
	}

	public int getNoUtilisateur() {
		return noUtilisateur;
	}
	
	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public String getPseudo() {
		return pseudo;
	}
	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRue() {
		return rue;
	}
	
	public void setRue(String rue) {
		this.rue = rue;
	}
	
	public String getCodePostal() {
		return codePostal;
	}
	
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}
	
	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getMotDePasse() {
		return motDePasse;
	}
	
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public int getCredit() {
		return credit;
	}
	
	public void setCredit(int credit) {
		this.credit = credit;
	}

	public boolean isAdministrateur() {
		return administrateur;
	}
	
	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}

	public List<ArticleVendu> getArticles() {
		return articles;
	}
	
	public void setArticles(List<ArticleVendu> articles) {
		this.articles = articles;
	}

	public List<Enchere> getEncheres() {
		return encheres;
	}

	public void setEncheres(List<Enchere> encheres) {
		this.encheres = encheres;
	}

	public boolean hasValidInformations() throws WrongInputException {
		
		// Verify phone number format (we are using the French format here)
		if((this.telephone.length() == 12 && this.telephone.toCharArray()[0] == '+')) {
			checkNumbers(this.telephone.substring(1), "telephone");
		} else if (this.telephone.length() == 10) {
			checkNumbers(this.telephone, "telephone");
		} else {
			throw new WrongInputException("Le numéro de téléphone n'est pas au bon format.");
		}

		// Verify postal code format (we are using the French format here)
		if(this.codePostal.length() == 5) {
			checkNumbers(this.codePostal, "code postal");
		} else {
			throw new WrongInputException("Le code postal n'est pas au bon format");
		}
		
		if(this.credit < 0) {
			throw new WrongInputException("Le crédit doit être supérieur à 0");
		}
		
		return true;
	}

	public void checkNumbers(String toCheck, String inputLabel) throws WrongInputException {
		System.out.println("Verify: " + toCheck);
		try {
			for(char character: toCheck.toCharArray()) {
				Integer.parseInt(character + "");
			}
		} catch(NumberFormatException e) {
			throw new WrongInputException("Le " + inputLabel + " contient des caractères interdits.");
		}
	}

	@Override
	public String toString() {
		return "Utilisateur [noUtilisateur=" + noUtilisateur + ", pseudo=" + pseudo + ", nom=" + nom + ", prenom="
				+ prenom + ", email=" + email + ", telephone=" + telephone + ", rue=" + rue + ", codePostal="
				+ codePostal + ", ville=" + ville + ", motDePasse=" + motDePasse + ", credit=" + credit
				+ ", administrateur=" + administrateur + ", articles=" + articles + ", encheres=" + encheres + "]";
	}

}
