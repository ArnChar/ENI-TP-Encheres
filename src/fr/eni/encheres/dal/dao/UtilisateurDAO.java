package fr.eni.encheres.dal.dao;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DALException;

public interface UtilisateurDAO extends DAO<Utilisateur> {

	public Utilisateur selectBy(int id) throws DALException;
	
	public Utilisateur selectByUser(String username, String password) throws DALException;
	
}
