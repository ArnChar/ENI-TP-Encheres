package fr.eni.encheres.dal;

import java.lang.reflect.InvocationTargetException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import fr.eni.encheres.dal.dao.ArticleVenduDAO;
import fr.eni.encheres.dal.dao.CategorieDAO;
import fr.eni.encheres.dal.dao.EnchereDAO;
import fr.eni.encheres.dal.dao.RetraitDAO;
import fr.eni.encheres.dal.dao.UtilisateurDAO;
import fr.eni.encheres.tool.Messages;

public abstract class DAOFactory {
	
	/**
	 * Singleton
	 */

	private static final String DAO_FACTORY_CLASS = "java:comp/env/dao/DAOFactoryImplementationClass";
	private static DAOFactory instance = null;
	
	/**
	 * Instancie l'implémentation de la DAOFactory indiquée dans le fichier settings.properties
	 * @return Implementation of DAOFactory
	 * @throws DALException
	 */
	public static DAOFactory getInstance() throws DALException {
		if(instance==null) {
			try {

				String factoryClassName = (String) new InitialContext().lookup(DAO_FACTORY_CLASS);
				instance = (DAOFactory) Class.forName(factoryClassName).getConstructors()[0].newInstance();

			} catch (NamingException | InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | SecurityException | ClassNotFoundException e) {
				e.printStackTrace();
				throw new DALException(Messages.get(Messages.ERROR_DAO_FACTORY_LOAD),e);
			}
		}
		return instance;
	}
	
	
	/**
	 * Services abstraits
	 */
	
	public abstract UtilisateurDAO getUtilisateurDAO() throws DALException;
	public abstract ArticleVenduDAO getArticleVenduDAO() throws DALException;
	public abstract EnchereDAO getEnchereDAO() throws DALException;
	public abstract RetraitDAO getRetraitDAO() throws DALException;
	public abstract CategorieDAO getCategorieDAO() throws DALException;

}
