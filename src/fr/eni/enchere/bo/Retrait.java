 package fr.eni.enchere.bo;

public class Retrait {
	private String rue;
	private String codePostal;
	private String ville;
	private int noRetrait;
	
	
	
	public Retrait() {
		
	}
	
	public Retrait(String rue, String codePostal, String ville,int noRetrait) {
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.noRetrait = noRetrait;
	}

	public Retrait(String rue, String codePostal, String ville) {
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
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
	
	public int getNoRetrait() {
		return noRetrait;
	}

	public void setNoRetrait(int noRetrait2) {
		// TODO Auto-generated method stub
		this.noRetrait = noRetrait2;
	}
	
}
