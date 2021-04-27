package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.dao.UtilisateurDAO;
import fr.eni.encheres.hmi.bean.UserBean;
import fr.eni.encheres.tool.Messages;

public class UtilisateurManager {

	private static final String CONSTRAINT_VIOLATION_NOTNULL = "Cannot insert the value NULL";
	private static final String CONSTRAINT_VIOLATION_UN_UTILISATEURS_PSEUDO = "Violation of UNIQUE KEY constraint 'UN_UTILISATEURS_PSEUDO'";
	private static final String CONSTRAINT_VIOLATION_UN_UTILISATEURS_EMAIL = "Violation of UNIQUE KEY constraint 'UN_UTILISATEURS_EMAIL'";

	
	/**
	 * Singleton
	 */
	
	private static UtilisateurManager instance = null;
	
	public static synchronized UtilisateurManager getInstance() {
		if(instance==null) {
			instance = new UtilisateurManager();
		}
		return instance;
	}

	private UtilisateurManager() {
		super();
	}
	
	/**
	 * Services
	 */
	
	public Utilisateur readUtilisateur(Integer noUtilisateur) throws BLLException {
		Utilisateur r = null;
		try {
			r =  DAOFactory.getInstance().getUtilisateurDAO().selectBy(noUtilisateur);
			if(r==null) {
				throw new BLLException(Messages.ERROR_BLL_BO_NOTFOUND, Messages.get(Messages.ERROR_BLL_BO_NOTFOUND,Utilisateur.class.getName(),String.valueOf(noUtilisateur)));
			}
		} catch (DALException e) {
			e.printStackTrace();
			throw new BLLException(e.getMessage(), Messages.get(Messages.ERROR_BLL_ACCESS_DAO), e);
		}
		return r;
	}
	
	public void saveUtilisateur(Utilisateur utilisateur) throws BLLException {
		try {
			if(utilisateur.getNoUtilisateur()==null) {
				DAOFactory.getInstance().getUtilisateurDAO().insert(utilisateur);
			} else {
				DAOFactory.getInstance().getUtilisateurDAO().update(utilisateur);
			}
			
		} catch (DALException e) {
			e.printStackTrace();
			String causeMessage = e.getCause().getMessage();
			if(causeMessage.contains(CONSTRAINT_VIOLATION_NOTNULL)) {
				throw new BLLException(e.getMessage(), Messages.get(Messages.ERROR_BLL_NOTNULL), e);
			}
			if(causeMessage.contains(CONSTRAINT_VIOLATION_UN_UTILISATEURS_PSEUDO)) {
				throw new BLLException(e.getMessage(), Messages.get(Messages.ERROR_BLL_UTILISATEUR_NOTUNIQUE_PSEUDO), e);
			}
			if(causeMessage.contains(CONSTRAINT_VIOLATION_UN_UTILISATEURS_EMAIL)) {
				throw new BLLException(e.getMessage(), Messages.get(Messages.ERROR_BLL_UTILISATEUR_NOTUNIQUE_EMAIL), e);
			}
		}
	}
	
	public Utilisateur authenticateUser(UserBean user) throws BLLException {
		Utilisateur r = null;
	
		try {
			// recherche de l'utilisateur correspondant au user et mot de passe
			UtilisateurDAO dao = DAOFactory.getInstance().getUtilisateurDAO();
			r = dao.selectByUser(user.getUsername(), user.getPassword());
			
			// si user et mdp ok, on reférence l'utilisateur dans le user
			if(r!=null) {
				user.setNoUtilisateur(r.getNoUtilisateur());
			}

		} catch (DALException e) {
			e.printStackTrace();
			throw new BLLException(e.getMessage(), Messages.get(Messages.ERROR_BLL_ACCESS_DAO), e);
		}
		
		return r;
	}
	
	public void deleteUtilisateur(Integer noUtilisateur) throws BLLException {
		try {
			Utilisateur utilisateur = new Utilisateur(noUtilisateur);
			DAOFactory.getInstance().getUtilisateurDAO().delete(utilisateur);
			
		} catch (DALException e) {
			e.printStackTrace();
			throw new BLLException(e.getMessage(), Messages.get(Messages.ERROR_BLL_ACCESS_DAO), e);
		}
	}
	
}
