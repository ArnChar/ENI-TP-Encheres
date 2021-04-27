package fr.eni.encheres.bo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ArticleVendu implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public enum EtatVente {
		EN_ATTENTE,
		EN_COURS,
		TERMINEE
	}
	
	private Integer noArticle = null;
	private String nomArticle = null;
	private String description = null;
	private LocalDateTime dateDebutEncheres = null;
	private LocalDateTime dateFinEncheres = null;
	private int miseAPrix = -1;
	private int prixVente = -1;
	private String cheminPhoto = null;
	private Utilisateur venduPar = null;
	private Utilisateur achetePar = null;
	private Categorie categorieArticle = null;
	private Retrait retrait = null;
	
	/**
	 * Constructeurs
	 */

	public ArticleVendu() {
		super();
	}
	
	public ArticleVendu(Integer noArticle) {
		super();
		this.setNoArticle(noArticle);
	}
	
	public ArticleVendu(Integer noArticle, String nomArticle, String description, LocalDateTime dateDebutEncheres,
			LocalDateTime dateFinEncheres, int miseAPrix, int prixVente, String cheminPhoto, Utilisateur venduPar,
			Utilisateur achetePar, Categorie categorieArticle, Retrait retrait) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.cheminPhoto = cheminPhoto;
		this.venduPar = venduPar;
		this.achetePar = achetePar;
		this.categorieArticle = categorieArticle;
		this.retrait = retrait;
	}

	/**
	 * Services
	 */
	
	// Retourne si l'enchère pour le produit a déjà commencée ou est terminée
	public EtatVente getEtatVente() {
		EtatVente r = EtatVente.EN_ATTENTE;
		LocalDateTime now = LocalDateTime.now();
		if(dateDebutEncheres!=null) {
			if(now.isBefore(dateDebutEncheres)) {
				r = EtatVente.EN_ATTENTE;
			} else if(dateFinEncheres!=null) {
				if(now.isBefore(dateFinEncheres)) {
					r = EtatVente.EN_COURS;
				} else {
					r = EtatVente.TERMINEE;
				}
			}
		}
		return r;
	}
	
	/**
	 * Accesseurs
	 */

	public Integer getNoArticle() {
		return noArticle;
	}

	public void setNoArticle(Integer noArticle) {
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

	public LocalDateTime getDateDebutEncheres() {
		return dateDebutEncheres;
	}

	public void setDateDebutEncheres(LocalDateTime dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}

	public LocalDateTime getDateFinEncheres() {
		return dateFinEncheres;
	}

	public void setDateFinEncheres(LocalDateTime dateFinEncheres) {
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

	public Utilisateur getVenduPar() {
		return venduPar;
	}

	public void setVenduPar(Utilisateur venduPar) {
		this.venduPar = venduPar;
	}

	public Utilisateur getAchetePar() {
		return achetePar;
	}

	public void setAchetePar(Utilisateur achetePar) {
		this.achetePar = achetePar;
	}

	public Categorie getCategorieArticle() {
		return categorieArticle;
	}

	public void setCategorieArticle(Categorie categorieArticle) {
		this.categorieArticle = categorieArticle;
	}

	public String getCheminPhoto() {
		return cheminPhoto;
	}

	public void setCheminPhoto(String cheminPhoto) {
		this.cheminPhoto = cheminPhoto;
	}

	public Retrait getRetrait() {
		return retrait;
	}

	public void setRetrait(Retrait retrait) {
		this.retrait = retrait;
	}

}
