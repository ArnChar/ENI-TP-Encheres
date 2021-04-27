package fr.eni.encheres.bo;

import java.io.Serializable;

public class Retrait implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArticleVendu articleVendu = null;
	private String rue = null;
	private String codePostal = null;
	private String ville = null;
	
	
	/**
	 * Constructeurs
	 */

	public Retrait() {
		super();
	}

	public Retrait(ArticleVendu articleVendu) {
		super();
		this.setArticleVendu(articleVendu);
	}

	public Retrait(ArticleVendu articleVendu, String rue, String codePostal, String ville) {
		super();
		this.articleVendu = articleVendu;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}
	
	/**
	 * Accesseurs
	 */
	
	public ArticleVendu getArticleVendu() {
		return articleVendu;
	}

	public void setArticleVendu(ArticleVendu articleVendu) {
		this.articleVendu = articleVendu;
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
}
