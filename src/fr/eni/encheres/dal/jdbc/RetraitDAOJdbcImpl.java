package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.dao.RetraitDAO;
import fr.eni.encheres.tool.Messages;

public class RetraitDAOJdbcImpl implements RetraitDAO {

	@Override
	public void insert(Retrait o) throws DALException {
		Connection connexion = null;
		PreparedStatement ps = null;
		Statement s = null;
		ResultSet rs = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();
			String sql = "INSERT INTO RETRAITS"+
				" (rue,code_postal,ville,no_article)"+
				" VALUES(?,?,?,?)";
			ps = connexion.prepareStatement(sql);
			
			saveRetrait(o, ps);
			ps.setInt(4, o.getArticleVendu().getNoArticle());

			ps.execute();
			
		} catch (SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_INSERT_BO, Retrait.class.getName(), String.valueOf(o.getArticleVendu().getNoArticle())), e);
			
		} finally {
			DAOFactoryJdbcImpl.closeResultSet(rs);
			DAOFactoryJdbcImpl.closeStatement(s);
			DAOFactoryJdbcImpl.closeStatement(ps);
			DAOFactoryJdbcImpl.closeConnection();
		}
	}

	@Override
	public void update(Retrait o) throws DALException {
		if(o.getArticleVendu()==null) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_UPDATE_BO_NOID, Retrait.class.getName()));
		}
		Connection connexion = null;
		PreparedStatement ps = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();
			String sql = "UPDATE RETRAITS"+
				" SET rue=?, code_postal=?, ville=?"+
				" WHERE no_article=?";
			ps = connexion.prepareStatement(sql);
			
			saveRetrait(o, ps);
			ps.execute();

		} catch (SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_UPDATE_BO, Retrait.class.getName(), String.valueOf(o.getArticleVendu().getNoArticle())), e);
			
		} finally {
			DAOFactoryJdbcImpl.closeStatement(ps);
			DAOFactoryJdbcImpl.closeConnection();
		}
	}

	@Override
	public void delete(Retrait o) throws DALException {
		Retrait aSupprimer = this.selectBy(o.getArticleVendu());
		if(aSupprimer==null) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_DELETE_BO_NOTFOUND, Retrait.class.getName(), String.valueOf(o.getArticleVendu().getNoArticle())));
		}
		
		Connection connexion = null;
		PreparedStatement ps = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();

			String sql = "DELETE FROM RETRAITS WHERE no_article=?";
			ps = connexion.prepareStatement(sql);
			ps.setInt(1, o.getArticleVendu().getNoArticle());
			
			ps.execute();

		} catch (SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_DELETE_BO, Retrait.class.getName(), String.valueOf(o.getArticleVendu().getNoArticle())), e);
			
		} finally {
			DAOFactoryJdbcImpl.closeStatement(ps);
			DAOFactoryJdbcImpl.closeConnection();
		}
	}

	@Override
	public Retrait selectBy(ArticleVendu articleVendu) throws DALException {
		Retrait r = null;
		Connection connexion = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();
			String sql = "SELECT * FROM RETRAITS WHERE no_article=?";
			ps = connexion.prepareStatement(sql);
			ps.setInt(1, articleVendu.getNoArticle());
			
			rs = ps.executeQuery();
			if(rs.next()) {
				r = loadRetrait(rs);
			}

		} catch(SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_LOAD_BO, Retrait.class.getName(), String.valueOf(articleVendu.getNoArticle())), e);

		} finally {
			DAOFactoryJdbcImpl.closeResultSet(rs);
			DAOFactoryJdbcImpl.closeStatement(ps);
			DAOFactoryJdbcImpl.closeConnection();
		}
		return r;
	}

	@Override
	public List<Retrait> selectAll() throws DALException {
		List<Retrait> r = null;
		Connection connexion = null;
		Statement s = null;
		ResultSet rs = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();
			s = connexion.createStatement();
			String sql = "SELECT * FROM RETRAITS";
			
			rs = s.executeQuery(sql);
			
			r = new ArrayList<Retrait>();
			Retrait retrait;
			while(rs.next()) {
				retrait = loadRetrait(rs);
				r.add(retrait);
			}
		} catch(SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_LOAD_BO_LIST, Retrait.class.getName()), e);
			
		} finally {
			DAOFactoryJdbcImpl.closeResultSet(rs);
			DAOFactoryJdbcImpl.closeStatement(s);
			DAOFactoryJdbcImpl.closeConnection();
		}
		return r;
	}

	/**
	 * Crée un lieu de retrait à partir d'un recordset
	 * @param recordset
	 * @return Retrait	
	 * @throws SQLException
	 */
	private Retrait loadRetrait(ResultSet rs) throws SQLException {
		Retrait r = new Retrait();
		
		r.setRue( rs.getString("rue") ); 
		r.setCodePostal( rs.getString("code_postal") );
		r.setVille( rs.getString("ville") ); 
		r.setArticleVendu(new ArticleVendu(rs.getInt("no_article")));
		
		return r;
	}

	/**
	 * Valorise les critères d'un PreparedStatement à partir d'un lieu de retrait
	 * @param o Retrait
	 * @param ps PreparedStatement
	 * @throws SQLException
	 */
	private void saveRetrait(Retrait o, PreparedStatement ps) throws SQLException {
		ps.setString(1, o.getRue());
		ps.setString(2, o.getCodePostal());
		ps.setString(3, o.getVille());
		ps.setInt(4, o.getArticleVendu().getNoArticle());
	}
	

}
