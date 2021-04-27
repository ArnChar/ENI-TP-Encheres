package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.tool.Messages;

public class CategorieManager {

	/**
	 * Singleton
	 */
	
	private static CategorieManager instance = null;
	
	public static synchronized CategorieManager getInstance() {
		if(instance==null) {
			instance = new CategorieManager();
		}
		return instance;
	}

	private CategorieManager() {
		super();
	}
	
	/**
	 * Services
	 */
	
	public List<Categorie> selectAllCategories() throws BLLException {
		List<Categorie> r = null;
		try {
			r = DAOFactory.getInstance().getCategorieDAO().selectAll();
		} catch (DALException e) {
			e.printStackTrace();
			throw new BLLException(e.getMessage(), Messages.get(Messages.ERROR_BLL_ACCESS_DAO), e);
		}
		return r;
	}

}
