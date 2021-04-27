package fr.eni.encheres.bll;

import java.time.LocalDateTime;
import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.dao.ArticleVenduDAO;
import fr.eni.encheres.dal.dao.RetraitDAO;
import fr.eni.encheres.tool.Messages;

public class ArticleManager {

	/**
	 * Singleton
	 */
	
	private static ArticleManager instance = null;
	
	public static synchronized ArticleManager getInstance() {
		if(instance==null) {
			instance = new ArticleManager();
		}
		return instance;
	}

	private ArticleManager() {
		super();
	}
	
	/**
	 * Services
	 */
	
	public ArticleVendu readArticle(Integer noArticle) throws BLLException {
		ArticleVendu r = null;
		try {
			r =  DAOFactory.getInstance().getArticleVenduDAO().selectBy(noArticle);
			
			// chargement des objects rattachés
			if(r!=null) {
				// Utilisateur Acheteur
				Integer id = r.getAchetePar().getNoUtilisateur();
				r.setAchetePar(DAOFactory.getInstance().getUtilisateurDAO().selectBy(id));
				// Utilisateur Vendeur
				id = r.getVenduPar().getNoUtilisateur();
				r.setVenduPar(DAOFactory.getInstance().getUtilisateurDAO().selectBy(id));
				// Retrait
				r.setRetrait(DAOFactory.getInstance().getRetraitDAO().selectBy(r));
				// Catégorie
				id = r.getCategorieArticle().getNoCategorie();
				r.setCategorieArticle(DAOFactory.getInstance().getCategorieDAO().selectBy(id));
				
			} else {
				throw new BLLException(Messages.ERROR_BLL_BO_NOTFOUND, Messages.get(Messages.ERROR_BLL_BO_NOTFOUND,ArticleVendu.class.getName(),String.valueOf(noArticle)));
			}
		} catch (DALException e) {
			e.printStackTrace();
			throw new BLLException(e.getMessage(), Messages.get(Messages.ERROR_BLL_ACCESS_DAO), e);
		}
		return r;
	}
	
	public List<ArticleVendu> selectArticlesByFilter(String filtreNom, Categorie categorie, LocalDateTime now) throws BLLException {
		List<ArticleVendu> r = null;

		try {
			r =  DAOFactory.getInstance().getArticleVenduDAO().selectBy(filtreNom, categorie, now);
			
			for(ArticleVendu a : r) {
				// Utilisateur Acheteur
				Integer id = a.getAchetePar().getNoUtilisateur();
				a.setAchetePar(DAOFactory.getInstance().getUtilisateurDAO().selectBy(id));
				// Utilisateur Vendeur
				id = a.getVenduPar().getNoUtilisateur();
				a.setVenduPar(DAOFactory.getInstance().getUtilisateurDAO().selectBy(id));
				// Retrait
				a.setRetrait(DAOFactory.getInstance().getRetraitDAO().selectBy(a));
				// Catégorie
				id = a.getCategorieArticle().getNoCategorie();
				a.setCategorieArticle(DAOFactory.getInstance().getCategorieDAO().selectBy(id));
			}

		} catch (DALException e) {
			e.printStackTrace();
			throw new BLLException(e.getMessage(), Messages.get(Messages.ERROR_BLL_ACCESS_DAO), e);
		}
		return r;
	}
	
	public void saveArticle(ArticleVendu article, Retrait retrait) throws BLLException {
		try {
			ArticleVenduDAO articleDAO = DAOFactory.getInstance().getArticleVenduDAO();
			RetraitDAO retraitDAO = DAOFactory.getInstance().getRetraitDAO();

			// Pas d'identifiant, donc insertion des objets
			if(article.getNoArticle()==null) {
				articleDAO.insert(article);
				retrait.setArticleVendu(article);
				retraitDAO.insert(retrait);
			} else {
				// Mise à jour des objets
				articleDAO.update(article);
				retraitDAO.update(retrait);
			}
		} catch (DALException e) {
			e.printStackTrace();
			throw new BLLException(e.getMessage(), Messages.get(Messages.ERROR_BLL_ACCESS_DAO), e);
		}
	}
	
	public void deleteArticle(Integer noArticle) throws BLLException {
		try {
			ArticleVendu article = new ArticleVendu(noArticle);
			DAOFactory.getInstance().getArticleVenduDAO().delete(article);
			
		} catch (DALException e) {
			e.printStackTrace();
			throw new BLLException(e.getMessage(), Messages.get(Messages.ERROR_BLL_ACCESS_DAO), e);
		}
	}
}
