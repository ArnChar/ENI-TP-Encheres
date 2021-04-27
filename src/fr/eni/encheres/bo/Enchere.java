package fr.eni.encheres.bo;

import java.io.Serializable;
import java.util.Date;


public class Enchere implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dateEnchere = null;
	private int montantEnchere = -1;
	private Utilisateur encheriePar = null;
	private ArticleVendu concerne = null;
	
	/**
	 * Constructeurs
	 */

	public Enchere() {
		super();
	}

	public Enchere(Utilisateur utilisateur, ArticleVendu articleVendu) {
		super();
		this.setEncheriePar(utilisateur);
		this.setConcerne(articleVendu);
	}

	/**
	 * Accesseurs
	 */

	public Date getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(Date dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getMontantEnchere() {
		return montantEnchere;
	}

	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	public Utilisateur getEncheriePar() {
		return encheriePar;
	}

	public void setEncheriePar(Utilisateur encheriePar) {
		this.encheriePar = encheriePar;
	}

	public ArticleVendu getConcerne() {
		return concerne;
	}

	public void setConcerne(ArticleVendu concerne) {
		this.concerne = concerne;
	}

}
