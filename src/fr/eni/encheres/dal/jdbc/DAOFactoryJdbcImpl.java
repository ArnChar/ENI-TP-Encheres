package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.dao.ArticleVenduDAO;
import fr.eni.encheres.dal.dao.CategorieDAO;
import fr.eni.encheres.dal.dao.EnchereDAO;
import fr.eni.encheres.dal.dao.RetraitDAO;
import fr.eni.encheres.dal.dao.UtilisateurDAO;
import fr.eni.encheres.tool.Messages;

/**
 * Implémention JDBC de DAOFactory
 */
public class DAOFactoryJdbcImpl extends DAOFactory {
	
	private static final String POOL_CNX = "java:comp/env/jdbc/poolConnexion";

	private static Connection connexion = null;
	
	/**
	 * Services
	 */

	// Obtention d'une connexion du pool JDBC
	public static synchronized Connection openConnection() throws DALException {
		if(connexion==null) {
			Context context;
			try {

				context = new InitialContext();
				// recherche datasource
				DataSource dataSource = (DataSource) context.lookup(POOL_CNX);
				// demande de connexion
				connexion = dataSource.getConnection();

			} catch (NamingException | SQLException e) {
				e.printStackTrace();
				throw new DALException(Messages.get(Messages.ERROR_DAO_FACTORY_OPEN_CONNECTION),e);
			}
		}
		return connexion;
	}
	
	// Remise d'une connexion dans le pool JDBC
	public static void closeConnection() throws DALException {
		if(connexion!=null) {
			synchronized(connexion) {
				try {
					connexion.close();
					connexion = null;
				} catch (SQLException e) {
					e.printStackTrace();
					throw new DALException(Messages.get(Messages.ERROR_DAO_FACTORY_CLOSE_CONNECTION),e);
				}
			}
		}
	}
	
	// Fermeture d'un resultset avec conversion de l'exception
	public static void closeResultSet(ResultSet rs) throws DALException { 
		try {
			if(rs!=null) {
				rs.close();
			}
		} catch(SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_FACTORY_CLOSE_RECORSET),e);
		}
	}
	
	// Fermeture d'un statement avec conversion de l'exception
	public static void closeStatement(Statement s) throws DALException { 
		try {
			if(s!=null) {
				s.close();
			}
		} catch(SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_FACTORY_CLOSE_STATEMENT),e);
		}
	}
	
	/**
	 * Implémenation des méthodes abstraites
	 */
	
	@Override
	public UtilisateurDAO getUtilisateurDAO() throws DALException {
		return new UtilisateurDAOJdbcImpl();
	}

	@Override
	public ArticleVenduDAO getArticleVenduDAO() throws DALException {
		return new ArticleVenduDAOJdbcImpl();
	}

	@Override
	public EnchereDAO getEnchereDAO() throws DALException {
		return new EnchereDAOJdbcImpl();
	}

	@Override
	public RetraitDAO getRetraitDAO() throws DALException {
		return new RetraitDAOJdbcImpl();
	}

	@Override
	public CategorieDAO getCategorieDAO() throws DALException {
		return new CategorieDAOJdbcImpl();
	}

}
