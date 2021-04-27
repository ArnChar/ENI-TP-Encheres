package fr.eni.encheres.dal.dao;

import java.time.LocalDateTime;
import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.DALException;

public interface ArticleVenduDAO extends DAO<ArticleVendu> {

	public ArticleVendu selectBy(int id) throws DALException; 
	
	public List<ArticleVendu> selectBy(String filtreNom, Categorie categorie, LocalDateTime now) throws DALException;
	
}
