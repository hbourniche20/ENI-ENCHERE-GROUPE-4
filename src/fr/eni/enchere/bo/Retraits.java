package fr.eni.enchere.bo;

public class Retraits {
	
	private int no_retrait;
	private String rue;
	private String code_postal;
	private String ville;
	
	public Retraits() {
		
	}

	public Retraits(String rue, String code_postal, String ville) {
		
		this.rue = rue;
		this.code_postal = code_postal;
		this.ville = ville;
	}

	public Retraits(int no_retrait, String rue, String code_postal, String ville) {
		
		this.no_retrait = no_retrait;
		this.rue = rue;
		this.code_postal = code_postal;
		this.ville = ville;
	}

	public int getNoRetrait() {
		return no_retrait;
	}

	public void setNoRetrait(int no_retrait) {
		this.no_retrait = no_retrait;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return code_postal;
	}

	public void setCodePostal(String code_postal) {
		this.code_postal = code_postal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}
	
	
}
