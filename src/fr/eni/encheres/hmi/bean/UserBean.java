package fr.eni.encheres.hmi.bean;

import java.io.Serializable;

public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String username = null;
	private String password = null;
	private Integer noUtilisateur = null;
	
	/**
	 * Bean de stockage des données utilisateur de l'application
	 */

	public UserBean() {
		super();
	}

	/**
	 * Accesseurs
	 */
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(Integer noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}
	
}
