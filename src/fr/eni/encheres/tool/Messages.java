package fr.eni.encheres.tool;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

public class Messages {

	/**
	 * Singleton
	 */
	
	private static Messages instance = new Messages("messages.properties");
	
	/**
	 * Instance
	 */
	
	private Properties properties;
	
	private Messages(String ressource) {
		properties = new Properties();
		try {
			properties.load(Messages.class.getResourceAsStream(ressource));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Erreur de chargement du fichier "+ressource);
		}
	}
	
	/**
	 * Services
	 */
	
	public static String get(String key) {
		return instance.properties.getProperty(key);
	}

	public static String get(String key, String param) {
		return MessageFormat.format(instance.properties.getProperty(key), param);
	}
	
	public static String get(String key, String param1, String param2) {
		return MessageFormat.format(instance.properties.getProperty(key), param1, param2);
	}
	

	/**
	 * Cles du fichier des messages
	 */
	
	public static final	String ERROR_DAO_FACTORY_LOAD = "error.dao.factory.load";
	public static final	String ERROR_DAO_FACTORY_OPEN_CONNECTION = "error.dao.factory.open.connection";
	public static final	String ERROR_DAO_FACTORY_CLOSE_CONNECTION = "error.dao.factory.close.connection";
	public static final	String ERROR_DAO_FACTORY_CLOSE_RECORSET = "error.dao.factory.close.recordset";
	public static final	String ERROR_DAO_FACTORY_CLOSE_STATEMENT = "error.dao.factory.close.statement";
	public static final	String ERROR_DAO_LOAD_BO = "error.dao.load.bo";
	public static final	String ERROR_DAO_LOAD_BO_LIST = "error.dao.load.bo.list";
	public static final	String ERROR_DAO_INSERT_BO = "error.dao.insert.bo";
	public static final	String ERROR_DAO_INSERT_BO_INCREMENT = "error.dao.insert.bo.increment";
	public static final	String ERROR_DAO_UPDATE_BO = "error.dao.update.bo";
	public static final	String ERROR_DAO_UPDATE_BO_NOID = "error.dao.update.bo.noid";
	public static final	String ERROR_DAO_DELETE_BO = "error.dao.delete.bo";
	public static final	String ERROR_DAO_DELETE_BO_NOTFOUND = "error.dao.delete.bo.notfound";
	public static final	String ERROR_DAO_SELECT = "error.dao.select";
	
	public static final	String ERROR_BLL_ACCESS_DAO = "error.bll.access.dao";
	public static final	String ERROR_BLL_NOTNULL = "error.bll.notnull";
	public static final	String ERROR_BLL_BO_NOTFOUND = "error.bll.bo.nofound";
	
	public static final	String ERROR_BLL_UTILISATEUR_NOTUNIQUE_PSEUDO = "error.bll.utilisateur.notunique.pseudo";
	public static final	String ERROR_BLL_UTILISATEUR_NOTUNIQUE_EMAIL = "error.bll.utilisateur.notunique.email";

	public static final	String ERROR_IHM_JSON_SERIALIZATION = "error.ihm.json.serialization";
	
	public static final	String ALERT_CONNEXION_DENIED = "alert.connexion.denied";	
	public static final	String ALERT_PROFIL_PASSWORD_EMPTY = "alert.profil.password.empty";	
	public static final	String ALERT_PROFIL_PASSWORD_DIFFERENT = "alert.profil.password.different";	
	
	public static final	String LABEL_DATETIME_VALUE_PATTERN = "label.datetime.value.pattern";
	
	public static final	String LABEL_LISTEENCHERES_CARD_DATETIME_FORMAT = "label.listeEncheres.card.datetime.format";

	public static final	String ALERT_VENTE_CATEGORIE = "alert.vente.categorie";
	public static final	String ALERT_VENTE_DATES = "alert.vente.dates";
	
}
