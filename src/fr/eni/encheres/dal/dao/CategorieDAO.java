package fr.eni.encheres.dal.dao;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.DALException;

public interface CategorieDAO extends DAO<Categorie> {

	public Categorie selectBy(int id) throws DALException; 
	
}
