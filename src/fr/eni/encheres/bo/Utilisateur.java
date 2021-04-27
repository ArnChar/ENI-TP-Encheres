package fr.eni.encheres.bo;

import java.io.Serializable;

public class Utilisateur implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer noUtilisateur = null;
	private String pseudo = null;
	private String nom = null;
	private String prenom = null;
	private String email = null;
	private String telephone = null;
	private String rue = null;
	private String codePostal = null;
	private String ville = null;
	private String motDePasse = null;
	private int credit = -1;
	private boolean administrateur = false;
	

	/**
	 *  Constructeurs
	 */
	
	public Utilisateur() {
		super();
	}

	public Utilisateur(Integer noUtilisateur) {
		super();
		this.setNoUtilisateur(noUtilisateur);
	}

	
	public Utilisateur(Integer noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String codePostal, String ville, String motDePasse, int credit, boolean administrateur) {
		super();
		this.noUtilisateur = noUtilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.administrateur = administrateur;
	}


	/**
	 *  Services
	 */
	
	// Ajoute un nb de crédits au crédit existant
	public int ajouterCredit(int montant) {
		this.credit += montant;
		return this.credit;
	}
	
	// Soustrait un nb de crédits au crédit existant et 
	public int retirerCredit(int montant) {
		this.credit -= montant;
		if(this.credit<0) {
			this.credit=0;
		}
		return this.credit;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Utilisateur [");
		sb.append("noUtilisateur=\""+noUtilisateur+"\"");
		sb.append(",pseudo=\""+pseudo+"\"");
		sb.append(",nom=\""+nom+"\"");
		sb.append(",prenom=\""+prenom+"\"");
		sb.append(",email=\""+email+"\"");
		sb.append(",telephone=\""+telephone+"\"");
		sb.append(",rue=\""+rue+"\"");
		sb.append(",codePostal=\""+codePostal+"\"");
		sb.append(",ville=\""+ville+"\"");
		sb.append(",motDePasse=\""+motDePasse+"\"");
		sb.append(",credit=\""+credit+"\"");
		sb.append(",administrateur=\""+administrateur+"\"");
		sb.append("]");
		return sb.toString();
	}
	
	
	/**
	 *  Accesseurs
	 */
	
	public Integer getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(Integer noUtilisateur) {
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
}
