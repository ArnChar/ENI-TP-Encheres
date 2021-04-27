package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.dao.ArticleVenduDAO;
import fr.eni.encheres.tool.Messages;

public class ArticleVenduDAOJdbcImpl implements ArticleVenduDAO {

	@Override
	public void insert(ArticleVendu o) throws DALException {
		Connection connexion = null;
		PreparedStatement ps = null;
		Statement s = null;
		ResultSet rs = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();
			String sql = "INSERT INTO ARTICLES_VENDUS"+
				" (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,chemin_photo,no_vendeur,no_acheteur,no_categorie)"+
				" VALUES(?,?,?,?,?,?,?,?,?,?)";
			ps = connexion.prepareStatement(sql);
			
			saveArticleVendu(o, ps);
			ps.execute();
			
			sql = "SELECT IDENT_CURRENT('ARTICLES_VENDUS') AS no_article";
			s = connexion.createStatement();
			rs = s.executeQuery(sql);
			if(rs.next()) {
				o.setNoArticle( rs.getInt("no_article") );
			}
		} catch (SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_INSERT_BO_INCREMENT, ArticleVendu.class.getName()), e);
			
		} finally {
			DAOFactoryJdbcImpl.closeResultSet(rs);
			DAOFactoryJdbcImpl.closeStatement(s);
			DAOFactoryJdbcImpl.closeStatement(ps);
			DAOFactoryJdbcImpl.closeConnection();
		}
	}

	@Override
	public void update(ArticleVendu o) throws DALException {
		if(o.getNoArticle()==null) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_UPDATE_BO_NOID, ArticleVendu.class.getName()));
		}
		Connection connexion = null;
		PreparedStatement ps = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();
			String sql = "UPDATE ARTICLES_VENDUS"+
				" SET nom_article=?, description=?, date_debut_encheres=?, date_fin_encheres=?, prix_initial=?, prix_vente=?, chemin_photo=?, no_vendeur=?, no_acheteur=?, no_categorie=? "+
				" WHERE no_article=?";
			ps = connexion.prepareStatement(sql);
			
			saveArticleVendu(o, ps);
			ps.setInt(11, o.getNoArticle());
			
			ps.execute();

		} catch (SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_UPDATE_BO, ArticleVendu.class.getName(), String.valueOf(o.getNoArticle())), e);
			
		} finally {
			DAOFactoryJdbcImpl.closeStatement(ps);
			DAOFactoryJdbcImpl.closeConnection();
		}
	}

	@Override
	public void delete(ArticleVendu o) throws DALException {
		ArticleVendu aSupprimer = this.selectBy(o.getNoArticle());
		if(aSupprimer==null) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_DELETE_BO_NOTFOUND, ArticleVendu.class.getName(), String.valueOf(o.getNoArticle())));
		}
		
		Connection connexion = null;
		PreparedStatement ps = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();

			String sql = "DELETE FROM ARTICLES_VENDUS WHERE no_article=?";
			ps = connexion.prepareStatement(sql);
			ps.setInt(1, o.getNoArticle());
			
			ps.execute();

		} catch (SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_DELETE_BO, ArticleVendu.class.getName(), String.valueOf(o.getNoArticle())), e);
			
		} finally {
			DAOFactoryJdbcImpl.closeStatement(ps);
			DAOFactoryJdbcImpl.closeConnection();
		}
	}

	@Override
	public ArticleVendu selectBy(int id) throws DALException {
		ArticleVendu r = null;
		Connection connexion = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();
			String sql = "SELECT * FROM ARTICLES_VENDUS WHERE no_article=?";
			ps = connexion.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				r = loadArticleVendu(rs);
			}

		} catch(SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_LOAD_BO, ArticleVendu.class.getName(), String.valueOf(id)), e);

		} finally {
			DAOFactoryJdbcImpl.closeResultSet(rs);
			DAOFactoryJdbcImpl.closeStatement(ps);
			DAOFactoryJdbcImpl.closeConnection();
		}
		return r;
	}

	@Override
	public List<ArticleVendu> selectBy(String filtreNom, Categorie categorie, LocalDateTime now) throws DALException {
		List<ArticleVendu> r = null;
		Connection connexion = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();
			String sql = 
				"SELECT * FROM ARTICLES_VENDUS" +
				" WHERE nom_article LIKE ?" +
				(categorie!=null ? " AND no_categorie LIKE ?" : "") +
				(now!=null ? " AND date_debut_encheres <= ? AND date_fin_encheres >= ?" : "");
			ps = connexion.prepareStatement(sql);
			int index = 1;
			ps.setString(index++, filtreNom==null ? "%" : "%"+filtreNom+"%");
			if(categorie!=null) {
				ps.setString(index++, String.valueOf(categorie.getNoCategorie()));
			}
			if(now!=null) {
				ps.setTimestamp(index++, Timestamp.valueOf(now));
				ps.setTimestamp(index++, Timestamp.valueOf(now));
			}
			rs = ps.executeQuery();
			
			r = new ArrayList<ArticleVendu>();
			ArticleVendu article;
			while(rs.next()) {
				article = loadArticleVendu(rs);
				r.add(article);
			}
		} catch(SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_LOAD_BO_LIST, ArticleVendu.class.getName()), e);
			
		} finally {
			DAOFactoryJdbcImpl.closeResultSet(rs);
			DAOFactoryJdbcImpl.closeStatement(ps);
			DAOFactoryJdbcImpl.closeConnection();
		}
		return r;
	}
	
	@Override
	public List<ArticleVendu> selectAll() throws DALException {
		List<ArticleVendu> r = null;
		Connection connexion = null;
		Statement s = null;
		ResultSet rs = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();
			s = connexion.createStatement();
			String sql = "SELECT * FROM ARTICLES_VENDUS";
			
			rs = s.executeQuery(sql);
			
			r = new ArrayList<ArticleVendu>();
			ArticleVendu article;
			while(rs.next()) {
				article = loadArticleVendu(rs);
				r.add(article);
			}
		} catch(SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_LOAD_BO_LIST, ArticleVendu.class.getName()), e);
			
		} finally {
			DAOFactoryJdbcImpl.closeResultSet(rs);
			DAOFactoryJdbcImpl.closeStatement(s);
			DAOFactoryJdbcImpl.closeConnection();
		}
		return r;
	}

	/**
	 * Crée un article à partir d'un recordset
	 * @param recordset
	 * @return ArticleVendu	
	 * @throws SQLException
	 */
	private ArticleVendu loadArticleVendu(ResultSet rs) throws SQLException {
		ArticleVendu r = new ArticleVendu();
		
		r.setNoArticle( rs.getInt("no_article") ); 
		r.setNomArticle( rs.getString("nom_article") ); 
		r.setDescription( rs.getString("description") );
		r.setDateDebutEncheres(	rs.getTimestamp("date_debut_encheres").toLocalDateTime() ); 
		r.setDateFinEncheres(	rs.getTimestamp("date_fin_encheres").toLocalDateTime() ); 
		r.setMiseAPrix( rs.getInt("prix_initial") ); 
		r.setPrixVente( rs.getInt("prix_vente") ); 
		r.setCheminPhoto( rs.getString("chemin_photo") ); 
		r.setVenduPar( new Utilisateur(rs.getInt("no_vendeur")) ); 
		r.setAchetePar( new Utilisateur(rs.getInt("no_acheteur")) ); 
		r.setCategorieArticle( new Categorie(rs.getInt("no_categorie")) ); 
		
		return r;
	}

	/**
	 * Valorise les critères d'un PreparedStatement à partir d'un utilisateur
	 * @param o Utilisateur
	 * @param ps PreparedStatement
	 * @throws SQLException
	 */
	private void saveArticleVendu(ArticleVendu o, PreparedStatement ps) throws SQLException {
		ps.setString(1, o.getNomArticle());
		ps.setString(2, o.getDescription());
		ps.setTimestamp(3, Timestamp.valueOf(o.getDateDebutEncheres()) );
		ps.setTimestamp(4, Timestamp.valueOf(o.getDateFinEncheres()) );
		ps.setInt(5, o.getMiseAPrix());
		ps.setInt(6, o.getPrixVente());
		ps.setString(7, o.getCheminPhoto());
		ps.setInt(8, o.getVenduPar().getNoUtilisateur());
		if(o.getAchetePar()==null) {
			ps.setNull(9,Types.INTEGER);
		} else {
			ps.setInt(9, o.getAchetePar().getNoUtilisateur());
		}
		ps.setInt(10, o.getCategorieArticle().getNoCategorie());
	}

}
