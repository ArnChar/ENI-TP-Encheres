package fr.eni.encheres.dal.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.dao.UtilisateurDAO;
import fr.eni.encheres.tool.Messages;


public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {

	@Override
	public void insert(Utilisateur o) throws DALException {
		Connection connexion = null;
		PreparedStatement ps = null;
		Statement s = null;
		ResultSet rs = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();
			String sql = "INSERT INTO UTILISATEURS"+
				" (pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur)"+
				" VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			ps = connexion.prepareStatement(sql);
			
			saveUtilisateur(o, ps);
			ps.execute();
			
			sql = "SELECT IDENT_CURRENT('UTILISATEURS') AS no_utilisateur";
			s = connexion.createStatement();
			rs = s.executeQuery(sql);
			if(rs.next()) {
				o.setNoUtilisateur( rs.getInt("no_utilisateur") );
			}
		} catch (SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_INSERT_BO_INCREMENT, Utilisateur.class.getName()), e);
			
		} finally {
			DAOFactoryJdbcImpl.closeResultSet(rs);
			DAOFactoryJdbcImpl.closeStatement(s);
			DAOFactoryJdbcImpl.closeStatement(ps);
			DAOFactoryJdbcImpl.closeConnection();
		}
	}

	@Override
	public void update(Utilisateur o) throws DALException {
		if(o.getNoUtilisateur()==null) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_UPDATE_BO_NOID, Utilisateur.class.getName()));
		}
		Connection connexion = null;
		PreparedStatement ps = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();
			String sql = "UPDATE UTILISATEURS"+
				" SET pseudo=?, nom=?, prenom=?, email=?, telephone=?, rue=?, code_postal=?, ville=?, mot_de_passe=?, credit=?, administrateur=? "+
				" WHERE no_utilisateur=?";
			ps = connexion.prepareStatement(sql);
			
			saveUtilisateur(o, ps);
			ps.setInt(12, o.getNoUtilisateur());
			
			ps.execute();

		} catch (SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_UPDATE_BO, Utilisateur.class.getName(), String.valueOf(o.getNoUtilisateur())), e);
			
		} finally {
			DAOFactoryJdbcImpl.closeStatement(ps);
			DAOFactoryJdbcImpl.closeConnection();
		}
	}

	@Override
	public void delete(Utilisateur o) throws DALException {
		Utilisateur aSupprimer = this.selectBy(o.getNoUtilisateur());
		if(aSupprimer==null) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_DELETE_BO_NOTFOUND, Utilisateur.class.getName(), String.valueOf(o.getNoUtilisateur())));
		}
		
		Connection connexion = null;
		CallableStatement cs = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();
			
			String sql = "EXEC dbo.sp_supprimerUtilisateur ?";
			cs = connexion.prepareCall(sql);
			cs.setInt(1, o.getNoUtilisateur());

			cs.execute();

		} catch (SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_DELETE_BO, Utilisateur.class.getName(), String.valueOf(o.getNoUtilisateur())), e);
			
		} finally {
			DAOFactoryJdbcImpl.closeStatement(cs);
			DAOFactoryJdbcImpl.closeConnection();
		}
	}

	@Override
	public Utilisateur selectBy(int id) throws DALException {
		Utilisateur r = null;
		Connection connexion = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();
			String sql = "SELECT * FROM UTILISATEURS WHERE no_utilisateur=?";
			ps = connexion.prepareStatement(sql);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				r = loadUtilisateur(rs);
			}

		} catch(SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_LOAD_BO, Utilisateur.class.getName(), String.valueOf(id)), e);

		} finally {
			DAOFactoryJdbcImpl.closeResultSet(rs);
			DAOFactoryJdbcImpl.closeStatement(ps);
			DAOFactoryJdbcImpl.closeConnection();
		}
		return r;
	}

	@Override
	public List<Utilisateur> selectAll() throws DALException {
		List<Utilisateur> r = null;
		Connection connexion = null;
		Statement s = null;
		ResultSet rs = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();
			s = connexion.createStatement();
			String sql = "SELECT * FROM UTILISATEURS";
			
			rs = s.executeQuery(sql);
			
			r = new ArrayList<Utilisateur>();
			Utilisateur utilisateur;
			while(rs.next()) {
				utilisateur = loadUtilisateur(rs);
				r.add(utilisateur);
			}
		} catch(SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_LOAD_BO_LIST, Utilisateur.class.getName()), e);
			
		} finally {
			DAOFactoryJdbcImpl.closeResultSet(rs);
			DAOFactoryJdbcImpl.closeStatement(s);
			DAOFactoryJdbcImpl.closeConnection();
		}
		return r;
	}


	@Override
	public Utilisateur selectByUser(String username, String password) throws DALException {
		Utilisateur r = null;
		Connection connexion = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			connexion = DAOFactoryJdbcImpl.openConnection();
			String sql = "SELECT * FROM UTILISATEURS WHERE (pseudo=? OR email=?) AND mot_de_passe=?";
			ps = connexion.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, username);
			ps.setString(3, password);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				r = loadUtilisateur(rs);
			}

		} catch(SQLException e) {
			throw new DALException(Messages.get(Messages.ERROR_DAO_SELECT, Utilisateur.class.getName()), e);

		} finally {
			DAOFactoryJdbcImpl.closeResultSet(rs);
			DAOFactoryJdbcImpl.closeStatement(ps);
			DAOFactoryJdbcImpl.closeConnection();
		}
		return r;
	}
	

	/**
	 * Crée un utilisateur à partir d'un recordset
	 * @param recordset
	 * @return Utilisateur	
	 * @throws SQLException
	 */
	private Utilisateur loadUtilisateur(ResultSet rs) throws SQLException {
		Utilisateur r = new Utilisateur();
		
		r.setNoUtilisateur(rs.getInt("no_utilisateur"));
		r.setPseudo(rs.getString("pseudo"));
		r.setNom(rs.getString("nom"));
		r.setPrenom(rs.getString("prenom"));
		r.setEmail(rs.getString("email"));
		r.setTelephone(rs.getString("telephone"));
		r.setRue(rs.getString("rue"));
		r.setCodePostal(rs.getString("code_postal"));
		r.setVille(rs.getString("ville"));
		r.setMotDePasse(rs.getString("mot_de_passe"));
		r.setCredit(rs.getInt("credit"));
		r.setAdministrateur(rs.getBoolean("administrateur"));
		
		return r;
	}

	/**
	 * Valorise les critères d'un PreparedStatement à partir d'un utilisateur
	 * @param o Utilisateur
	 * @param ps PreparedStatement
	 * @throws SQLException
	 */
	private void saveUtilisateur(Utilisateur o, PreparedStatement ps) throws SQLException {
		ps.setString(1, o.getPseudo());
		ps.setString(2, o.getNom());
		ps.setString(3, o.getPrenom());
		ps.setString(4, o.getEmail());
		ps.setString(5, o.getTelephone());
		ps.setString(6, o.getRue());
		ps.setString(7, o.getCodePostal());
		ps.setString(8, o.getVille());
		ps.setString(9, o.getMotDePasse());
		ps.setInt(10, o.getCredit());
		ps.setBoolean(11, o.isAdministrateur());
	}

}
