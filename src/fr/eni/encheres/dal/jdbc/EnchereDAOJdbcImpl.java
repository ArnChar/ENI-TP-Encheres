package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.dao.EnchereDAO;
import fr.eni.encheres.tool.Messages;

public class EnchereDAOJdbcImpl implements EnchereDAO {

	@Override
	public void insert(Enchere o) throws DALException {
		Connection connexion = null;
		PreparedStatement ps = null;
		Statement s = null;
		ResultSet rs = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();
			String sql = "INSERT INTO ENCHERES"+
				" (date_enchere,montant_enchere,no_utilisateur,no_article)"+
				" VALUES(?,?,?,?)";
			ps = connexion.prepareStatement(sql);
			
			saveEnchere(o, ps);
			ps.execute();
			
		} catch (SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_INSERT_BO, Enchere.class.getName(),  getIds(o)), e);
			
		} finally {
			DAOFactoryJdbcImpl.closeResultSet(rs);
			DAOFactoryJdbcImpl.closeStatement(s);
			DAOFactoryJdbcImpl.closeStatement(ps);
			DAOFactoryJdbcImpl.closeConnection();
		}
	}

	@Override
	public void update(Enchere o) throws DALException {
		if(o.getEncheriePar()==null || o.getConcerne()==null) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_UPDATE_BO_NOID, Enchere.class.getName()));
		}
		Connection connexion = null;
		PreparedStatement ps = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();
			String sql = "UPDATE ENCHERES"+
				" SET date_enchere=?, montant_enchere=?"+
				" WHERE no_utilisateur=? AND no_article=?";
			ps = connexion.prepareStatement(sql);
			
			saveEnchere(o, ps);
			ps.execute();

		} catch (SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_UPDATE_BO, Enchere.class.getName(), getIds(o)), e);
			
		} finally {
			DAOFactoryJdbcImpl.closeStatement(ps);
			DAOFactoryJdbcImpl.closeConnection();
		}
	}

	@Override
	public void delete(Enchere o) throws DALException {
		Enchere aSupprimer = this.selectBy(o.getEncheriePar(),o.getConcerne());
		if(aSupprimer==null) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_DELETE_BO_NOTFOUND, ArticleVendu.class.getName(), getIds(o)));
		}
		
		Connection connexion = null;
		PreparedStatement ps = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();

			String sql = "DELETE FROM ARTICLES_VENDUS WHERE no_utilisateur=? AND no_article=?";
			ps = connexion.prepareStatement(sql);
			ps.setInt(1, o.getEncheriePar().getNoUtilisateur());
			ps.setInt(2, o.getConcerne().getNoArticle());
			
			ps.execute();

		} catch (SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_DELETE_BO, Enchere.class.getName(), getIds(o)), e);
			
		} finally {
			DAOFactoryJdbcImpl.closeStatement(ps);
			DAOFactoryJdbcImpl.closeConnection();
		}
	}

	@Override
	public Enchere selectBy(Utilisateur encheriePar, ArticleVendu concerne) throws DALException { 
		Enchere r = null;
		Connection connexion = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();
			String sql = "SELECT * FROM ENCHERES WHERE no_utilisateur=? AND no_article=?";
			ps = connexion.prepareStatement(sql);
			ps.setInt(1, encheriePar.getNoUtilisateur());
			ps.setInt(2, concerne.getNoArticle());
			
			rs = ps.executeQuery();
			if(rs.next()) {
				r = loadEnchere(rs);
			}

		} catch(SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_LOAD_BO, Enchere.class.getName(), getIds(encheriePar.getNoUtilisateur(),concerne.getNoArticle())), e);

		} finally {
			DAOFactoryJdbcImpl.closeResultSet(rs);
			DAOFactoryJdbcImpl.closeStatement(ps);
			DAOFactoryJdbcImpl.closeConnection();
		}
		return r;
	}

	@Override
	public List<Enchere> selectAll() throws DALException {
		List<Enchere> r = null;
		Connection connexion = null;
		Statement s = null;
		ResultSet rs = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();
			s = connexion.createStatement();
			String sql = "SELECT * FROM ENCHERES";
			
			rs = s.executeQuery(sql);
			
			r = new ArrayList<Enchere>();
			Enchere enchere;
			while(rs.next()) {
				enchere = loadEnchere(rs);
				r.add(enchere);
			}
		} catch(SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_LOAD_BO_LIST, Enchere.class.getName()), e);
			
		} finally {
			DAOFactoryJdbcImpl.closeResultSet(rs);
			DAOFactoryJdbcImpl.closeStatement(s);
			DAOFactoryJdbcImpl.closeConnection();
		}
		return r;
	}

	/**
	 * Crée une enchère à partir d'un recordset
	 * @param recordset
	 * @return Enchere	
	 * @throws SQLException
	 */
	private Enchere loadEnchere(ResultSet rs) throws SQLException {
		Enchere r = new Enchere();
		
		r.setDateEnchere( new java.util.Date( rs.getDate("date_enchere").getTime()) ); 
		r.setMontantEnchere( rs.getInt("montant_enchere") ); 
		
		return r;
	}

	/**
	 * Valorise les critères d'un PreparedStatement à partir d'une enchère
	 * @param o Enchere
	 * @param ps PreparedStatement
	 * @throws SQLException
	 */
	private void saveEnchere(Enchere o, PreparedStatement ps) throws SQLException {
		ps.setDate(1, new java.sql.Date(o.getDateEnchere().getTime()) );
		ps.setInt(2, o.getMontantEnchere());
		ps.setInt(3, o.getEncheriePar().getNoUtilisateur());
		ps.setInt(4, o.getConcerne().getNoArticle());
	}

	private String getIds(Enchere o) {
		return getIds(o.getEncheriePar().getNoUtilisateur(),o.getConcerne().getNoArticle());
	}
	
	private String getIds(int idEncheriePar, int idConcerne) {
		return "(no_utilisateur=" + String.valueOf(idEncheriePar) + ",no_article=" + String.valueOf(idConcerne) + ")";
	}
	

}
