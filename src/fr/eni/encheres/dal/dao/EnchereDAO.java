package fr.eni.encheres.dal.dao;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DALException;

public interface EnchereDAO extends DAO<Enchere> {

	public Enchere selectBy(Utilisateur encheriePar, ArticleVendu concerne) throws DALException; 

}
