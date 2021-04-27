package fr.eni.encheres.dal.dao;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.DALException;

public interface RetraitDAO extends DAO<Retrait> {

	public Retrait selectBy(ArticleVendu articleVendu) throws DALException; 
	
}
