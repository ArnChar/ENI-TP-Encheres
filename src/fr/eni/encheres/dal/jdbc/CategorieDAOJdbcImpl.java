package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.dao.CategorieDAO;
import fr.eni.encheres.tool.Messages;

public class CategorieDAOJdbcImpl implements CategorieDAO {

	@Override
	public void insert(Categorie o) throws DALException {
		Connection connexion = null;
		PreparedStatement ps = null;
		Statement s = null;
		ResultSet rs = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();
			String sql = "INSERT INTO CATEGORIES"+
				" (libelle)"+
				" VALUES(?)";
			ps = connexion.prepareStatement(sql);
			
			saveCategorie(o, ps);
			ps.execute();
			
			sql = "SELECT IDENT_CURRENT('CATEGORIES') AS no_categorie";
			s = connexion.createStatement();
			rs = s.executeQuery(sql);
			if(rs.next()) {
				o.setNoCategorie( rs.getInt("no_categorie") );
			}
		} catch (SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_INSERT_BO_INCREMENT, Categorie.class.getName()), e);
			
		} finally {
			DAOFactoryJdbcImpl.closeResultSet(rs);
			DAOFactoryJdbcImpl.closeStatement(s);
			DAOFactoryJdbcImpl.closeStatement(ps);
			DAOFactoryJdbcImpl.closeConnection();
		}
	}

	@Override
	public void update(Categorie o) throws DALException {
		if(o.getNoCategorie()==null) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_UPDATE_BO_NOID, Categorie.class.getName()));
		}
		Connection connexion = null;
		PreparedStatement ps = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();
			String sql = "UPDATE CATEGORIES"+
				" SET libelle=?"+
				" WHERE no_categorie=?";
			ps = connexion.prepareStatement(sql);
			
			saveCategorie(o, ps);
			ps.setInt(2, o.getNoCategorie());
			
			ps.execute();

		} catch (SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_UPDATE_BO, Categorie.class.getName(), String.valueOf(o.getNoCategorie())), e);
			
		} finally {
			DAOFactoryJdbcImpl.closeStatement(ps);
			DAOFactoryJdbcImpl.closeConnection();
		}
	}

	@Override
	public void delete(Categorie o) throws DALException {
		Categorie aSupprimer = this.selectBy(o.getNoCategorie());
		if(aSupprimer==null) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_DELETE_BO_NOTFOUND, Categorie.class.getName(), String.valueOf(o.getNoCategorie())));
		}
		
		Connection connexion = null;
		PreparedStatement ps = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();

			String sql = "DELETE FROM CATEGORIES WHERE no_categorie=?";
			ps = connexion.prepareStatement(sql);
			ps.setInt(1, o.getNoCategorie());
			
			ps.execute();

		} catch (SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_DELETE_BO, Categorie.class.getName(), String.valueOf(o.getNoCategorie())), e);
			
		} finally {
			DAOFactoryJdbcImpl.closeStatement(ps);
			DAOFactoryJdbcImpl.closeConnection();
		}
	}

	@Override
	public Categorie selectBy(int id) throws DALException {
		Categorie r = null;
		Connection connexion = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();
			String sql = "SELECT * FROM CATEGORIES WHERE no_categorie=?";
			ps = connexion.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				r = loadCategorie(rs);
			}

		} catch(SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_LOAD_BO, Categorie.class.getName(), String.valueOf(id)), e);

		} finally {
			DAOFactoryJdbcImpl.closeResultSet(rs);
			DAOFactoryJdbcImpl.closeStatement(ps);
			DAOFactoryJdbcImpl.closeConnection();
		}
		return r;
	}

	@Override
	public List<Categorie> selectAll() throws DALException {
		List<Categorie> r = null;
		Connection connexion = null;
		Statement s = null;
		ResultSet rs = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();
			s = connexion.createStatement();
			String sql = "SELECT * FROM CATEGORIES";
			
			rs = s.executeQuery(sql);
			
			r = new ArrayList<Categorie>();
			Categorie categorie;
			while(rs.next()) {
				categorie = loadCategorie(rs);
				r.add(categorie);
			}
		} catch(SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_LOAD_BO_LIST, Categorie.class.getName()), e);
			
		} finally {
			DAOFactoryJdbcImpl.closeResultSet(rs);
			DAOFactoryJdbcImpl.closeStatement(s);
			DAOFactoryJdbcImpl.closeConnection();
		}
		return r;
	}

	/**
	 * Crée une catégorie à partir d'un recordset
	 * @param recordset
	 * @return Categorie	
	 * @throws SQLException
	 */
	private Categorie loadCategorie(ResultSet rs) throws SQLException {
		Categorie r = new Categorie();
		
		r.setNoCategorie( rs.getInt("no_categorie") ); 
		r.setLibelle( rs.getString("libelle") ); 
		
		return r;
	}

	/**
	 * Valorise les critères d'un PreparedStatement à partir d'une catégorie
	 * @param o Categorie
	 * @param ps PreparedStatement
	 * @throws SQLException
	 */
	private void saveCategorie(Categorie o, PreparedStatement ps) throws SQLException {
		ps.setString(1, o.getLibelle());
	}
	

}
