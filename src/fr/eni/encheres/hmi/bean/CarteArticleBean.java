package fr.eni.encheres.hmi.bean;

public class CarteArticleBean {
	
	private String cheminPhoto = null;
	private String nomArticle = null;
	private String categorie = null;
	private String prix = null;
	private String dateFin = null;
	private String nomVendeur = null;
	private Integer idVendeur = null;
	private Integer idArticle = null;
	
	public CarteArticleBean() {
		super();
	}

	public CarteArticleBean(String nomArticle, String prix, String dateFin, Integer idVendeur, String nomVendeur) {
		super();
		this.nomArticle = nomArticle;
		this.prix = prix;
		this.dateFin = dateFin;
		this.idVendeur = idVendeur;
		this.nomVendeur = nomVendeur;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public String getPrix() {
		return prix;
	}

	public void setPrix(String prix) {
		this.prix = prix;
	}

	public String getDateFin() {
		return dateFin;
	}

	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}

	public String getNomVendeur() {
		return nomVendeur;
	}

	public void setNomVendeur(String nomVendeur) {
		this.nomVendeur = nomVendeur;
	}

	public Integer getIdVendeur() {
		return idVendeur;
	}

	public void setIdVendeur(Integer idVendeur) {
		this.idVendeur = idVendeur;
	}

	public Integer getIdArticle() {
		return idArticle;
	}

	public void setIdArticle(Integer idArticle) {
		this.idArticle = idArticle;
	}

	public String getCheminPhoto() {
		return cheminPhoto;
	}

	public void setCheminPhoto(String cheminPhoto) {
		this.cheminPhoto = cheminPhoto;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

}
