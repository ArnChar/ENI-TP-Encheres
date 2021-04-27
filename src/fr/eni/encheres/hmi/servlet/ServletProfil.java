package fr.eni.encheres.hmi.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.BLLException;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.hmi.ViewModel;
import fr.eni.encheres.hmi.bean.UserBean;
import fr.eni.encheres.tool.Messages;

/**
 * Servlet implementation class ServletProfil
 */

@WebServlet({"/Profil","/ProfilDelete"})

public class ServletProfil extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Requete /ProfilDelete : suppression du user et déconnexion
		if(request.getServletPath().equals("/ProfilDelete")) {
			processProfilDelete(request, response);
		} else {
			processProfil(request, response);
		}
	}
	
	// Requete /Profil : traitement du user
	private void processProfil(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Prise en compte du paramètre "action" déterminant le comportement de la page profil.jsp
		//create : création d'un utilisateur
		//modify : affichage et modification de l'utilisateur défini par le paramètre "no_utilisateur"
		//view   : affichage d'un utilisateur défini par le paramètre "no_utilisateur"
		if(request.getAttribute("action")==null) {
			request.setAttribute("action", request.getParameter("action"));
			request.setAttribute("no_utilisateur", request.getParameter("no_utilisateur"));
			request.setAttribute("title", request.getParameter("title"));
		}
		
		//En mode modify et view, Alimentation des champs de la page
		if( !request.getAttribute("action").equals("create") ) {
			List<String> alerts = new ArrayList<String>();
			ViewModel viewModel = loadModelFromUtilisateur(Integer.parseInt((String) request.getAttribute("no_utilisateur")), alerts);
			if(alerts.size()>0) {
				request.setAttribute("alerts", alerts);
			}
			viewModel.putIntoRequest(request);
		}
		
		// Redirection vers la page profil.jsp
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/jsp/profil.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// recupération des champs du formulaire dans le modèle de vue
		ViewModel viewModel = new ViewModel();
		viewModel.getFromRequest(request, 
			new String[] {"no_utilisateur","in_username","in_lastname","in_firstname","in_email","in_phone","in_street","in_zip","in_city",
				"in_password","in_confirm","in_credit","ck_admin"});

		// Vérif erreurs de saisie
		List<String> alerts = new ArrayList<String>();
		checkModel(viewModel, alerts);

		// Si pas d'erreurs de saisie 
		if(alerts.size()==0) {
			// Instanciation d'un utilisateur à partir du modèle de vue
			Utilisateur utilisateur = getUtilisateurFromModel(viewModel);
			try {
				// Insertion ou maj utilisateur
				UtilisateurManager.getInstance().saveUtilisateur(utilisateur);
				// Sauvegarde NoUtilisateur dans viewModel 
				viewModel.putInteger("no_utilisateur", utilisateur.getNoUtilisateur());
			} catch(BLLException e) {
				// si violations de contraintes, on les ajoute aux alertes utilisateur
				alerts.add(e.getUserMessage());
			}
		}
		
		// Si présence d'erreurs, on réalimente la page avec le modèle de vue et les alertes
		if(alerts.size()>0) {
			viewModel.putIntoRequest(request);
			request.setAttribute("alerts", alerts);
			doGet(request, response);
		
		} else {
			// Création du user en session
			UserBean user = getUserBeanFromModel(viewModel);
			request.getSession().setAttribute("user", user);

			// redirection vers page Accueil en étant authentifié
			response.sendRedirect(request.getContextPath()+"/ListeEncheres");
		}
	}

	// Vérif erreurs de saisie et alimente une liste d'alertes
	private void checkModel(ViewModel viewModel, List<String> alerts) {
		// Mot de passe vide
		if( viewModel.getString("in_password")==null ) {
			alerts.add(Messages.get(Messages.ALERT_PROFIL_PASSWORD_EMPTY));
		}
		// Confirmation mot de passe mal KO
		if( !viewModel.get("in_password").equals(viewModel.get("in_confirm")) ) {
			alerts.add(Messages.get(Messages.ALERT_PROFIL_PASSWORD_DIFFERENT));
		}
	}

	// Mapping viewModel vers Utilisateur
	private Utilisateur getUtilisateurFromModel(ViewModel viewModel) {
		Utilisateur r = new Utilisateur();
		r.setNoUtilisateur(viewModel.getInteger("no_utilisateur"));
		r.setPseudo(viewModel.getString("in_username"));
		r.setNom(viewModel.getString("in_lastname"));
		r.setPrenom(viewModel.getString("in_firstname"));
		r.setEmail(viewModel.getString("in_email"));
		r.setTelephone(viewModel.getString("in_phone"));
		r.setRue(viewModel.getString("in_street"));
		r.setCodePostal(viewModel.getString("in_zip"));
		r.setVille(viewModel.getString("in_city"));
		r.setMotDePasse(viewModel.getString("in_password"));
		r.setCredit(viewModel.getInteger("in_credit")==null ? 0 : viewModel.getInteger("in_credit"));
		r.setAdministrateur(viewModel.getString("ck_admin")!=null);
		return r;
	}
	
	// Maoping viewModel vers UserBean
	private UserBean getUserBeanFromModel(ViewModel viewModel) {
		UserBean r = new UserBean();
		r.setUsername(viewModel.get("in_username"));
		r.setPassword(viewModel.get("in_password"));
		r.setNoUtilisateur(viewModel.getInteger("no_utilisateur"));
		return r;
	}
	
	// Charge un modèle de vue avec un utilisateur existant
	// En cas d'erreur, enrichit la liste d'alertes à afficher
	private ViewModel loadModelFromUtilisateur(Integer noUtilisateur, List<String> alerts) {
		ViewModel r = new ViewModel();
		try {
			Utilisateur u = UtilisateurManager.getInstance().readUtilisateur(noUtilisateur);
			r.put("in_username",u.getPseudo());
			r.put("in_lastname",u.getNom());
			r.put("in_firstname",u.getPrenom());
			r.put("in_email",u.getEmail());
			r.put("in_phone",u.getTelephone());
			r.put("in_street",u.getRue());
			r.put("in_zip",u.getCodePostal());
			r.put("in_city",u.getVille());
			r.put("in_password",null);
			r.put("in_credit",String.valueOf(u.getCredit()));
			r.put("ck_admin",u.isAdministrateur()?"on":null);
			
		} catch (BLLException e) {
			alerts.add(e.getUserMessage());
		}
		return r;
	}

	// Suppression du user et déconnexion
	private void processProfilDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Récupère le user en session
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		if(user!=null) {
			try {
				// Supprime l'tilisateur de la base
				UtilisateurManager.getInstance().deleteUtilisateur(user.getNoUtilisateur());
				
			} catch (BLLException e) {
				e.printStackTrace();
			}
		}		
		// Déconnexion
		response.sendRedirect(request.getContextPath()+"/Deconnexion");
	}

}
