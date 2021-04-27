package fr.eni.encheres.bo;

import java.io.Serializable;

public class Categorie implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer noCategorie = null;
	private String libelle = null;
	
	/**
	 * Constructeurs
	 */

	public Categorie() {
		super();
	}
	
	public Categorie(Integer noCategorie) {
		super();
		this.setNoCategorie(noCategorie);
	}
	
	public Categorie(Integer noCategorie, String libelle) {
		super();
		this.noCategorie = noCategorie;
		this.libelle = libelle;
	}

	/**
	 * Accesseurs
	 */

	public Integer getNoCategorie() {
		return noCategorie;
	}

	public void setNoCategorie(Integer noCategorie) {
		this.noCategorie = noCategorie;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}
