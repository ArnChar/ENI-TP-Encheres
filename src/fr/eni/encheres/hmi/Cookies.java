package fr.eni.encheres.hmi;

import java.util.Base64;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.eni.encheres.tool.Messages;

public class Cookies {
	
	/**
	 * Recherche un cookie par son nom
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Cookie r = null;
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals(name)) {
					r = cookie;
					break;
				}
			}
		}
		return r;
	}
	
	/**
	 * Récupère un cookie et retourne une instance de l'objet stocké à l'intérieur
	 * @param <T> Classe de l'objet contenu dans le cookie
	 * @param request Request contenant le cookie
	 * @param name Nom du cookie
	 * @param type Type de l'objet contenu dans le cookie
	 * @return Objet contenu dans le cookie
	 * @throws HMIException Erreur de désérialisation Json
	 */
	public static <T> T getCookieObjectByName(HttpServletRequest request, String name, Class<T> type) throws HMIException {
		T r = null;
		// Récupère le cookie
		Cookie cookie = getCookieByName(request, name);
		if(cookie!=null) {
			try {
				// Décode la valeur du cookie codée en base64
				String json = new String(Base64.getDecoder().decode(cookie.getValue()));

				// Parse le json contenu et instancie l'objet à retourner
				r = new ObjectMapper().readValue(json, type);
				
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				throw new HMIException(Messages.get(Messages.ERROR_IHM_JSON_SERIALIZATION),e);
			}
		}
		return r;
	}
	
	/**
	 * Crée un cookie avec pour valeur un objet sérialisé en json puis encodé en base64.
	 * @param name Nom du cookie
	 * @param object Instance de l'objet à stocker dans le cookie
	 * @return Cookie contenant l'objet
	 * @throws HMIException Erreur de sérialisation Json
	 */
	public static Cookie createFromObject(String name, Object object) throws HMIException {
		Cookie r = null;
		try {
			// Sérialise en json l'objet
			String json = new ObjectMapper().writeValueAsString(object);
			
			// Encode en base64 le json et crée un cookie contenant le résultat
			r = new Cookie(name, Base64.getEncoder().encodeToString(json.getBytes()) );
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new HMIException(Messages.get(Messages.ERROR_IHM_JSON_SERIALIZATION),e);
		}
		return r;
	}
	
}
